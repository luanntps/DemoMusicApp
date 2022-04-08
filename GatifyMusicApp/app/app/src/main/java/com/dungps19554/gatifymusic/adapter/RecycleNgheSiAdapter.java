package com.dungps19554.gatifymusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dungps19554.gatifymusic.R;

import java.util.ArrayList;

public class RecycleNgheSiAdapter extends RecyclerView.Adapter<RecycleNgheSiAdapter.ViewHolder> {
    ArrayList<NgheSi> ngheSis;
    Context context;

    public RecycleNgheSiAdapter(ArrayList<NgheSi> ngheSi, Context context) {
        this.ngheSis = ngheSi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycle_nghesi, parent, false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale);
        view.startAnimation(animation);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenNS.setText(ngheSis.get(position).getTenNS());
        holder.ivPoster.setImageResource(ngheSis.get(position).getHinh());
    }

    @Override
    public int getItemCount() {
        return ngheSis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenNS;
        ImageView ivPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNS = itemView.findViewById(R.id.tvTenNsHome);
            ivPoster = itemView.findViewById(R.id.ivNghesiHome);
        }
    }
}
