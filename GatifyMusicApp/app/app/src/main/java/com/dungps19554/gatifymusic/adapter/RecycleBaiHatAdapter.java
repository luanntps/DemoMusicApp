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
import com.dungps19554.gatifymusic.model.BaiHat;

import java.util.ArrayList;

public class RecycleBaiHatAdapter extends RecyclerView.Adapter<RecycleBaiHatAdapter.ViewHolder> {
    ArrayList<BaiHat> baiHats;
    Context context;

    public RecycleBaiHatAdapter(ArrayList<BaiHat> baiHats, Context context) {
        this.baiHats = baiHats;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycle_baihat, parent, false);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale);
        view.startAnimation(animation);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenBH.setText(baiHats.get(position).getTenBH());
        holder.ivPoster.setImageResource(baiHats.get(position).getHinh());
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenBH;
        ImageView ivPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBH = itemView.findViewById(R.id.tvTenBHHome);
            ivPoster = itemView.findViewById(R.id.ivPosterBHHome);
        }
    }
}
