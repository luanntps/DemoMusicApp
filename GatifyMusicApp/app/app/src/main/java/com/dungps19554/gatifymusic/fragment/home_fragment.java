package com.dungps19554.gatifymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dungps19554.gatifymusic.R;
import com.dungps19554.gatifymusic.User_Activity;
import com.dungps19554.gatifymusic.adapter.RecycleBaiHatAdapter;
import com.dungps19554.gatifymusic.adapter.RecycleNgheSiAdapter;
import com.dungps19554.gatifymusic.model.BaiHat;

import java.util.ArrayList;

public class home_fragment extends Fragment {
    RecyclerView recyclerViewBH, recyclerViewNS;
    ImageView ivHome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerViewBH = view.findViewById(R.id.recycleViewBH);
        recyclerViewNS = view.findViewById(R.id.recycleViewNS);
        ivHome = view.findViewById(R.id.ivHomeDraw);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), User_Activity.class);
                startActivity(intent);
            }
        });
        recycleBH();
        recycleNS();
//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_bh);
//        view.startAnimation(animation);
        return view;
    }


    public void recycleBH(){
        recyclerViewBH.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBH.setLayoutManager(manager);
        ArrayList<BaiHat> alBaiHat = new ArrayList<>();
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        alBaiHat.add(new BaiHat(R.mipmap.chayvenoiphiaanh_poster, "Chạy về khóc với anh"));
        RecycleBaiHatAdapter adapter = new RecycleBaiHatAdapter(alBaiHat, getContext());
        recyclerViewBH.setAdapter(adapter);
    }

    public void recycleNS(){
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
    }


}
