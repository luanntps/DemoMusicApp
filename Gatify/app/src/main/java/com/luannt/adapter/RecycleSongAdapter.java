package com.luannt.adapter;

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

import com.bumptech.glide.Glide;
import com.luannt.R;
import com.luannt.model.Song;

import java.util.ArrayList;

public class RecycleSongAdapter extends RecyclerView.Adapter<RecycleSongAdapter.ViewHolder> {
    ArrayList<Song> alSong;
    Context context;

    public RecycleSongAdapter(ArrayList<Song> alSong, Context context) {
        this.alSong = alSong;
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
        holder.tvTenBH.setText(alSong.get(position).getSong_name());
        Glide.with(context).load(alSong.get(position).getUrl_song_pic()).override(300,300)
                .centerCrop().into(holder.imgSong);
    }

    @Override
    public int getItemCount() {
        return alSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenBH;
        ImageView imgSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBH = itemView.findViewById(R.id.tvTenBHHome);
            imgSong = itemView.findViewById(R.id.ivPosterBHHome);
        }
    }
}
