package com.luannt.model;

public class Song {
    private int id;
    private String song_name;
    private int view_count;
    private String lyrics;
    private String url_song_pic;
    private String url_media;
    private String genre;
    private int id_artist;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public Song(int id, String song_name, int view_count, String lyrics, String url_song_pic, String url_media, String genre, int id_artist) {
        this.id = id;
        this.song_name = song_name;
        this.view_count = view_count;
        this.lyrics = lyrics;
        this.url_song_pic = url_song_pic;
        this.url_media = url_media;
        this.genre = genre;
        this.id_artist = id_artist;
    }
    public Song(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getUrl_song_pic() {
        return url_song_pic;
    }

    public void setUrl_song_pic(String url_song_pic) {
        this.url_song_pic = url_song_pic;
    }

    public String getUrl_media() {
        return url_media;
    }

    public void setUrl_media(String url_media) {
        this.url_media = url_media;
    }

    public String getGenre_name() {
        return genre;
    }

    public void setGenre_name(String genre) {
        this.genre = genre;
    }
}
