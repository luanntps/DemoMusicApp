package com.luannt.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luannt.R;
import com.luannt.adapter.RecycleCommentAdapter;
import com.luannt.adapter.ViewPagerAdapter;
import com.luannt.helpers.MediaNotification;
import com.luannt.model.Comment;
import com.luannt.model.Song;
import com.luannt.model.User;
import com.luannt.service.OnClearFromRecentService;
import com.luannt.service.ServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String email;
    User currentUser;

    BottomNavigationView bottomNV;
    ViewPager viewPager;

    TextView tvCurrentTime, tvTotalTime, tvCurrentSongName, tvSheetCurrentTime, tvSheetTotalTime, tvSheetCurrentSongName;
    SeekBar seekBar, sheetSeekBar;
    ImageView btnSkipPrevious,btnPrevious,btnPlay,btnNext,btnSkipNext, imgCurrentSongPic;

    MediaPlayer mediaPlayer=new MediaPlayer();
    Song currentSong;
    ArrayList<Song> listSong;

    ArrayList<Comment> listComment;
    RecyclerView lvComment;

    LinearLayout playBar;
    CoordinatorLayout bottomSheet;
    LinearLayout commentSheet;
    LinearLayout typeCommentSheet;

    BottomSheetBehavior bottomSheetBehavior;
    BottomSheetBehavior commentSheetBehavior;
    TextView openComment;

    EditText edtComment;
    ImageView avtUser, imgUser;
    ImageView btnSendComment;

    SharedPreferences sharedPref ;
    int x=0;
    int position=0;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("useremail");

        viewPager=findViewById(R.id.viewPager);
        bottomNV = findViewById(R.id.bottomnavigationView);

        lvComment = findViewById(R.id.lvCommment);

        imgUser=findViewById(R.id.imgUser);
        avtUser=findViewById(R.id.avtUser);
        btnSendComment=findViewById(R.id.btnSendComment);
        edtComment=findViewById(R.id.edtComment);

        bottomSheet=findViewById(R.id.bottom_sheet_player);
        playBar=findViewById(R.id.playBar);
        openComment=findViewById(R.id.openComment);
        commentSheet=findViewById(R.id.comment_sheet);


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
        getUserDetail(email);
        getAllComment();

        //notification cho media
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("SONGS_SONGS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
        //
        sharedPref= this.getSharedPreferences("CurrentSongPreferences", Context.MODE_PRIVATE);
        //tạo luồng cho media
        this.runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                if(sharedPref.getInt("checkClick",0)==1) {
                    receiveData();
                    Play();
                    getAllComment();
                    try {
                        MediaNotification.createNotification(MainActivity.this,currentSong,R.drawable.ic_pause,1,listSong.size()-1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

        commentSheetBehavior = BottomSheetBehavior.from(commentSheet);
        openComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    commentSheet.setVisibility(View.VISIBLE);
                    edtComment.setEnabled(true);
                    edtComment.requestFocus();
                    commentSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //tự động mở Keyboard
                    InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                }else {
                    commentSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
                if (item.getItemId() == R.id.it_search) {
                    item.setChecked(true);
                    viewPager.setCurrentItem(2);
                }
                return false;
            }
        });
        //sự kiện cho chức năng comment
        btnSendComment.setOnClickListener(c->sendComment());
    }
    //sự kiện cho nút gửi comment
    public void sendComment(){
        String myComment=edtComment.getText()+"";
        if(myComment.isEmpty()){}
        else{
            Comment comment=new Comment(myComment,email,currentSong.getId(),currentUser.getUrl_user_pic());
            createComment(comment);
            Toast.makeText(MainActivity.this,"Đã đăng bình luận.",Toast.LENGTH_SHORT).show();
            getAllComment();
            commentSheet.setVisibility(View.GONE);
            edtComment.setText("");
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
            edtComment.clearFocus();
        }
    }
    // tạo notification
    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(MediaNotification.CHANNEL_ID,"Gatify",NotificationManager.IMPORTANCE_LOW);
            notificationManager=getSystemService(NotificationManager.class);

            notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getExtras().getString("actionname");

            switch (action){
                case MediaNotification.ACTION_PREVIOUS: {
                    SkipPrevious();
                    break;
                }
                case MediaNotification.ACTION_PLAY:{
                    PlayPause();
                    break;
                }
                case MediaNotification.ACTION_NEXT:{
                    SkipNext();
                    break;
                }
            }
        }
    };
    //gọi api lấy danh sách comment
    public void getAllComment(){
        if(currentSong==null){}
        else {
            ServiceAPI requestInterface = new Retrofit.Builder()
                    .baseUrl(ServiceAPI.BASE_SERVICE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServiceAPI.class);
            new CompositeDisposable()
                    .add(requestInterface.GetAllComment(currentSong.getId())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::handleResponseComment, this::handleErrorComment));
        }
    }
    private void handleResponseComment(ArrayList<Comment> alComment) {
        lvComment.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        lvComment.setLayoutManager(manager);
        RecycleCommentAdapter adapter = new RecycleCommentAdapter(alComment, MainActivity.this);
        lvComment.setAdapter(adapter);
        listComment=alComment;
    }
    private void handleErrorComment(Throwable error) {
        Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
    }
    //gọi api tạo comment
    public void createComment(Comment comment) throws OnErrorNotImplementedException {

            ServiceAPI requestInterface = new Retrofit.Builder()
                    .baseUrl(ServiceAPI.BASE_SERVICE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServiceAPI.class);
        requestInterface.CreateComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }

                    @Override
                    public void onNext(Message message) {

                    }

                    @Override
                    public void onError( Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    };
    //Lấy user hiện tại
    public void getUserDetail(String email){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable()
                .add(requestInterface.GetUserDetail(email)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseUser, this::handleErrorUser) );

    }
    private void handleResponseUser(User user) {
        currentUser=user;
        Glide.with(MainActivity.this).load(currentUser.getUrl_user_pic()).into(avtUser);
        Glide.with(MainActivity.this).load(currentUser.getUrl_user_pic()).into(imgUser);
    }
    private void handleErrorUser(Throwable error) {
        Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
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
            try {
                MediaNotification.createNotification(MainActivity.this,currentSong,R.drawable.ic_play,1,listSong.size()-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mediaPlayer.start();
            try {
                MediaNotification.createNotification(MainActivity.this,currentSong,R.drawable.ic_pause,1,listSong.size()-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            position++;
            try {
                MediaNotification.createNotification(MainActivity.this,currentSong,R.drawable.ic_pause,1,listSong.size()-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //Nhận data của song được chọn
    void receiveData(){
        String urlCurrentMedia=sharedPref.getString("urlCurrentMedia",null);
        String urlCurrentPic=sharedPref.getString("urlCurrentPic",null);
        String currentID=sharedPref.getString("currentID",null);
        String currentViewCount=sharedPref.getString("currentViewCount",null);
        String currentSongName=sharedPref.getString("currentSongName",null);
        String currentSongGenreName=sharedPref.getString("currentSongGenreName",null);
        String currentSongLyrics=sharedPref.getString("currentSongLyrics",null);
        String currentSongArtist=sharedPref.getString("currentSongArtist",null);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("checkClick",0);
        editor.commit();
        if(urlCurrentMedia!=null
                && urlCurrentPic!=null
                && currentID!=null
                && currentViewCount!=null
                && currentSongName!=null
                && currentSongGenreName!=null
                && currentSongLyrics!=null
                && currentSongArtist!=null)
        currentSong = new Song(Integer.parseInt(currentID),currentSongName,Integer.parseInt(currentViewCount),currentSongLyrics,urlCurrentPic,urlCurrentMedia,currentSongGenreName,Integer.parseInt(currentSongArtist));
    }
    //gắn info của song được chọn vào shared referene
    void setCurrentSong(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("urlCurrentMedia", currentSong.getUrl_media()+"");
        editor.putString("urlCurrentPic",currentSong.getUrl_song_pic()+"");
        editor.putString("currentID",currentSong.getId()+"");
        editor.putString("currentViewCount",currentSong.getView_count()+"");
        editor.putString("currentSongName",currentSong.getSong_name()+"");
        editor.putString("currentSongGenreName",currentSong.getGenre_name()+"");
        editor.putString("currentSongLyrics",currentSong.getLyrics()+"");
        editor.putString("currentSongArtist",currentSong.getId_artist()+"");
        editor.commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }
        unregisterReceiver(broadcastReceiver);
    }
    //Custom cho nút Back
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }
        if(commentSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED
                &&bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {
            commentSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        this.doubleBackToExitPressedOnce = true;
        if(commentSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED
                &&bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED) {
            Toast.makeText(this, "Ấn 2 lần để thoát", Toast.LENGTH_SHORT).show();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {


            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }
}