package com.luannt.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luannt.R;
import com.luannt.activities.User_Activity;
import com.luannt.adapter.RecycleListSongAdapter;
import com.luannt.adapter.RecycleSongAdapter;
import com.luannt.helpers.RecyclerItemClickListener;
import com.luannt.model.Song;
import com.luannt.service.ServiceAPI;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class home_fragment extends Fragment {
    RecyclerView rcvListSong, rcvPlayList;
    ImageView ivHome, imgCurrentSongPic;


    Song currentSong;

    ArrayList<Song> listSong;


    Animation animation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lấy ra trạng trái fragment lần trước

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        rcvListSong = view.findViewById(R.id.recycleViewBH);
        rcvPlayList = view.findViewById(R.id.rcvPlayList);

        ivHome = view.findViewById(R.id.ivHomeDraw);

        //khởi tạo giao diện media player


        //
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), User_Activity.class);
                startActivity(intent);
            }
        });
        getListSong();
        recyclePlayList();

        ListSongClickEvent();
        //phân luồng cho mediaplayer

        //recycleNS();
//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_bh);
//        view.startAnimation(animation);
        return view;
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
        rcvListSong.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvListSong.setLayoutManager(manager);
        RecycleSongAdapter adapter = new RecycleSongAdapter(alSong, getContext());
        rcvListSong.setAdapter(adapter);
        listSong=alSong;
        }
    private void handleErrorSong(Throwable error) {
        Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
    }
    //
    public void ListSongClickEvent(){
        rcvListSong.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rcvListSong, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentSong=listSong.get(position);

                SharedPreferences sharedPref = getContext().getSharedPreferences("CurrentSongPreferences", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("songPosition",position);
                editor.commit();

                setCurrentSong();

                //tvTotalTime.setText(convertToMMSS());


            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }



    public void recyclePlayList(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable()
                .add(requestInterface.GetAllSong()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponsePlayList, this::handleErrorPlayList) );

    }
    private void handleResponsePlayList(ArrayList<Song> alSong) {
        rcvPlayList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvPlayList.setLayoutManager(manager);
        RecycleListSongAdapter adapter = new RecycleListSongAdapter(alSong, getContext());
        rcvPlayList.setAdapter(adapter);
    }
    private void handleErrorPlayList(Throwable error) {
        Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
    }

    /*public void recycleNS(){
        recyclerViewNS.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNS.setLayoutManager(manager);
        ArrayList<NgheSi> alNgheSi = new ArrayList<>();
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        alNgheSi.add(new NgheSi(R.mipmap.mtp, "Sơn Tùng M-TP"));
        RecycleNgheSiAdapter adapter = new RecycleNgheSiAdapter(alNgheSi, getContext());
        recyclerViewNS.setAdapter(adapter);
    }*/


    void setCurrentSong(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("CurrentSongPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("urlCurrentMedia", currentSong.getUrl_media()+"");
        editor.putString("urlCurrentPic",currentSong.getUrl_song_pic()+"");
        editor.putString("currentID",currentSong.getId()+"");
        editor.putString("currentViewCount",currentSong.getView_count()+"");
        editor.putString("currentSongName",currentSong.getSong_name()+"");
        editor.putString("currentSongGenreID",currentSong.getGenre_id()+"");
        editor.putString("currentSongLyrics",currentSong.getLyrics()+"");
        editor.putInt("checkClick",1);
        editor.commit();

    }
}
