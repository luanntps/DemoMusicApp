package com.luannt.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luannt.R;
import com.luannt.adapter.ViewPagerAdapter;
import com.luannt.model.Song;
import com.luannt.service.ServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNV;
    ViewPager viewPager;

    TextView tvCurrentTime, tvTotalTime, tvCurrentSongName, tvSheetCurrentTime, tvSheetTotalTime, tvSheetCurrentSongName;
    SeekBar seekBar, sheetSeekBar;
    ImageView btnSkipPrevious,btnPrevious,btnPlay,btnNext,btnSkipNext, imgCurrentSongPic;

    MediaPlayer mediaPlayer=new MediaPlayer();
    Song currentSong;
    ArrayList<Song> listSong;

    LinearLayout playBar;
    LinearLayout bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;


    SharedPreferences sharedPref ;
    int x=0;
    int checkSongClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        bottomNV = findViewById(R.id.bottomnavigationView);

        bottomSheet=findViewById(R.id.bottom_sheet_player);
        playBar=findViewById(R.id.playBar);

        imgCurrentSongPic=findViewById(R.id.imgCurrentSongPic);
        tvCurrentTime=findViewById(R.id.tvCurrentTime);
        tvTotalTime=findViewById(R.id.tvTotalTime);
        tvSheetTotalTime=findViewById(R.id.tvSheetTotalTime);
        tvSheetCurrentTime=findViewById(R.id.tvSheetCurrentTime);
        tvCurrentSongName=findViewById(R.id.tvCurrentSongName);
        tvSheetCurrentSongName=findViewById(R.id.tvSheetCurrentSongName);

        seekBar=findViewById(R.id.sbSeekBar);
        sheetSeekBar=findViewById(R.id.sbSheetSeekBar);

        btnSkipPrevious=findViewById(R.id.btnSkipPrevious);
        btnPrevious=findViewById(R.id.btnPrevious);
        btnPlay=findViewById(R.id.btnPlay);
        btnNext=findViewById(R.id.btnNext);
        btnSkipNext=findViewById(R.id.btnSkipNext);

        btnSkipPrevious.setOnClickListener(v->SkipPrevious());
        btnPrevious.setOnClickListener(v->Previous());
        btnPlay.setOnClickListener(v->PlayPause());
        btnNext.setOnClickListener(v->Next());
        btnSkipNext.setOnClickListener(v->SkipNext());

        getListSong();

        sharedPref= this.getSharedPreferences("CurrentSongPreferences", Context.MODE_PRIVATE);
        //tạo luồng cho media
        this.runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                if(sharedPref.getInt("checkClick",0)==1) {
                    receiveData();
                    Play();
                }
                if(currentSong!=null) {
                    tvCurrentSongName.setText(currentSong.getSong_name() + "");
                    tvSheetCurrentSongName.setText(currentSong.getSong_name() + "");
                    String url = currentSong.getUrl_song_pic();
                    RequestBuilder<Drawable> glide = Glide.with(MainActivity.this).load(url).circleCrop();
                    glide.into(imgCurrentSongPic);
                }
                new Handler().postDelayed(this,100);
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    sheetSeekBar.setProgress(mediaPlayer.getCurrentPosition());

                    tvCurrentTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));
                    tvSheetCurrentTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));
                    x+=3;
                    imgCurrentSongPic.setRotation(x);
                }
                if(mediaPlayer.isPlaying()){
                    btnPlay.setImageResource(R.drawable.ic_pause);
                }else {
                    imgCurrentSongPic.setRotation(0);
                    btnPlay.setImageResource(R.drawable.ic_play);
                }
            }
        }));
        //sự kiện cho seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                if(mediaPlayer!=null && fromUser){
                    tvCurrentTime.setText(convertToMMSS(progress+""));
                    tvSheetCurrentTime.setText(convertToMMSS(progress+""));
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sheetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                if(mediaPlayer!=null && fromUser){
                    tvCurrentTime.setText(convertToMMSS(progress+""));
                    tvSheetCurrentTime.setText(convertToMMSS(progress+""));
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //sự kiện cho bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        playBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        //sự kiện ViewPager
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNV.getMenu().findItem(R.id.it_home).setChecked(true);

                        break;
                    case 1:
                        bottomNV.getMenu().findItem(R.id.it_bxh).setChecked(true);

                        break;
                    case 2:
                        bottomNV.getMenu().findItem(R.id.it_live).setChecked(true);

                        break;
                    case 3:
                        bottomNV.getMenu().findItem(R.id.it_search).setChecked(true);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.it_home) {
                    item.setChecked(true);
                    viewPager.setCurrentItem(0);
                }
                if (item.getItemId() == R.id.it_bxh) {
                    item.setChecked(true);
                    viewPager.setCurrentItem(1);
                }
                if (item.getItemId() == R.id.it_live) {
                    item.setChecked(true);
                    viewPager.setCurrentItem(2);
                }
                if (item.getItemId() == R.id.it_search) {
                    item.setChecked(true);
                    viewPager.setCurrentItem(3);
                }
                return false;
            }
        });
    }

    //gọi api lấy danh sách bài hát + gắn vào arraylist
    public void getListSong(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable()
                .add(requestInterface.GetAllSong()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseSong, this::handleErrorSong) );

    }
    private void handleResponseSong(ArrayList<Song> alSong) {
        listSong=alSong;
    }
    private void handleErrorSong(Throwable error) {
        Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
    }
    //đổi ms sang mm:ss
    @SuppressLint("DefaultLocale")
    public String convertToMMSS(String duration){
        Long millis =Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis)%TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis)%TimeUnit.MINUTES.toSeconds(1));
    }
    //chức năng của mediaPLayer
    private void SkipPrevious(){
        int position=sharedPref.getInt("songPosition",0);
        if(position>0) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("songPosition",position-1);
            editor.commit();
            currentSong = listSong.get(sharedPref.getInt("songPosition",0));
            setCurrentSong();
            editor.putInt("checkClick",1);
            sharedPref.getInt("songPosition",0);
            editor.commit();
        }

    }
    private void Previous(){
        int time=mediaPlayer.getCurrentPosition()-15000;
        mediaPlayer.seekTo(time);
    }
    private void Play(){
        mediaPlayer.reset();
        String audioUrl = currentSong.getUrl_media();
        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);

            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();
            sheetSeekBar.setProgress(0);
            sheetSeekBar.setMax(mediaPlayer.getDuration());
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
            tvTotalTime.setText(convertToMMSS(mediaPlayer.getDuration()+""));
            tvSheetTotalTime.setText(convertToMMSS(mediaPlayer.getDuration()+""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void PlayPause(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else mediaPlayer.start();
    }

    private void Next(){
        int time=mediaPlayer.getCurrentPosition()+15000;
        mediaPlayer.seekTo(time);
    }
    private void SkipNext(){
        int position=sharedPref.getInt("songPosition",0);
        if(position<listSong.size()-1) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("songPosition",position+1);
            editor.commit();
            currentSong = listSong.get(sharedPref.getInt("songPosition",0));
            setCurrentSong();
            editor.putInt("checkClick",1);
            editor.commit();
        }
    }
    //Nhận data của song được chọn
    void receiveData(){
        String urlCurrentMedia=sharedPref.getString("urlCurrentMedia",null);
        String urlCurrentPic=sharedPref.getString("urlCurrentPic",null);
        String currentID=sharedPref.getString("currentID",null);
        String currentViewCount=sharedPref.getString("currentViewCount",null);
        String currentSongName=sharedPref.getString("currentSongName",null);
        String currentSongGenreID=sharedPref.getString("currentSongGenreID",null);
        String currentSongLyrics=sharedPref.getString("currentSongLyrics",null);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("checkClick",0);
        editor.commit();
        if(urlCurrentMedia!=null
                && urlCurrentPic!=null
                && currentID!=null
                && currentViewCount!=null
                && currentSongName!=null
                && currentSongGenreID!=null
                && currentSongLyrics!=null)
        currentSong = new Song(Integer.parseInt(currentID),currentSongName,Integer.parseInt(currentViewCount),currentSongLyrics,urlCurrentPic,urlCurrentMedia,Integer.parseInt(currentSongGenreID));
    }
    //gắn info của song được chọn vào shared referene
    void setCurrentSong(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("urlCurrentMedia", currentSong.getUrl_media()+"");
        editor.putString("urlCurrentPic",currentSong.getUrl_song_pic()+"");
        editor.putString("currentID",currentSong.getId()+"");
        editor.putString("currentViewCount",currentSong.getView_count()+"");
        editor.putString("currentSongName",currentSong.getSong_name()+"");
        editor.putString("currentSongGenreID",currentSong.getGenre_id()+"");
        editor.putString("currentSongLyrics",currentSong.getLyrics()+"");
        editor.commit();
    }
}