package com.luannt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.luannt.R;
import com.luannt.model.Comment;
import com.luannt.model.Song;
import com.luannt.model.User;
import com.luannt.service.ServiceAPI;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class RecycleCommentAdapter extends RecyclerView.Adapter<RecycleCommentAdapter.ViewHolder> {
    ArrayList<Comment> alComment;
    Context context;
    String urlImage;
    public RecycleCommentAdapter(ArrayList<Comment> alComment, Context context) {
        this.alComment = alComment;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecycleCommentAdapter.ViewHolder holder, int position) {
        holder.tvUserEmail.setText(alComment.get(position).getEmail());
        holder.tvUserComment.setText(alComment.get(position).getContent());
        Glide.with(context).load(alComment.get(position).getUrl_user_pic()).override(300,300).centerCrop().into(holder.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return alComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserEmail;
        CircleImageView ivUserImage;
        TextView tvUserComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserEmail = itemView.findViewById(R.id.tvUsername);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvUserComment= itemView.findViewById(R.id.tvUserComment);
        }
    }
}

