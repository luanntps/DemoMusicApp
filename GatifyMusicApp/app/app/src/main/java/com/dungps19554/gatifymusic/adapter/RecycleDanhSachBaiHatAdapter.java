package com.dungps19554.gatifymusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dungps19554.gatifymusic.R;
import com.dungps19554.gatifymusic.model.DanhSachBaiHat;

import java.util.ArrayList;

public class RecycleDanhSachBaiHatAdapter extends BaseAdapter {
    ArrayList<DanhSachBaiHat> baiHats;
    Context context;

    public RecycleDanhSachBaiHatAdapter(ArrayList<DanhSachBaiHat> baiHats, Context context) {
        this.baiHats = baiHats;
        this.context = context;
    }


    @Override
    public int getCount() {
        return baiHats.size();
    }

    @Override
    public Object getItem(int i) {
        return baiHats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return baiHats.get(i).getMa();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();


        view = inflater.inflate(R.layout.item_recycle_danhsach_baihat, null);
        TextView tenLoai = view.findViewById(R.id.tvBaiHatDS);


        tenLoai.setText(baiHats.get(i).getTenBH() + "");
        return view;
    }


}
