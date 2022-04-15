package com.luannt.service;

import android.os.Message;

import com.luannt.model.Genre;
import com.luannt.model.Playlist;
import com.luannt.model.Song;
import com.luannt.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://sosteam.congtydacap.club/";
    // call api
    //thao tac get
    @GET("api/get-all-user")
    Observable<ArrayList<User>> GetAllUser();
    @GET("api/get-user-detail")
    Observable<ArrayList<User>> GetUserDetail(@Query("email") String email);
    @GET("api/get-all-playlist")
    Observable<ArrayList<Playlist>> GetAllPlaylist(@Query("email") String email);
    @GET("api/get-all-song")
    Observable<ArrayList<Song>> GetAllSong();
    @GET("api/get-all-genre")
    Observable<ArrayList<Genre>> GetAllGenre();
    //thao tac post
    @POST("api/create-user")
    Observable<Message> CreateUser(@Body User user);
    @POST("api/update-profile-pic")
    Observable<Message> UpdateProfilePic(@Query("url") String url, @Query("email") String email);
    @POST("api/update-vip")
    Observable<Message> UpdateVip(@Query("url") String url, @Query("email") String email);
    @POST("api/create-playlist")
    Observable<Message> CreatePlaylist(@Body Playlist playlist);
    @POST("api/delete-playlist")
    Observable<Message> DeletePlaylist(@Body Playlist playlist);
    @POST("api/add-song-to-playlist")
    Observable<Message> AddSongToPlaylist(@Query("playlistId") int playlistId, @Query("email") String email);
    @POST("api/delete-song-from-playlist")
    Observable<Message> DeleteSongFromPlaylist(@Query("playlistId") int playlistId, @Query("email") String email);


}
