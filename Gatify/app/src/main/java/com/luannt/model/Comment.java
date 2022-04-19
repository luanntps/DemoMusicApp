package com.luannt.model;

public class Comment {
    private int id;
    private String content;
    private String email;
    private int id_song;
    private String url_user_pic;

    public Comment(int id, String content, String email, int id_song) {
        this.id = id;
        this.content = content;
        this.email = email;
        this.id_song = id_song;
    }

    public Comment(String content, String email, int id_song, String url_user_pic) {
        this.content = content;
        this.email = email;
        this.id_song = id_song;
        this.url_user_pic = url_user_pic;
    }

    public Comment(String content, String email, int id_song) {
        this.content = content;
        this.email = email;
        this.id_song = id_song;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public String getUrl_user_pic() {
        return url_user_pic;
    }

    public void setUrl_user_pic(String url_user_pic) {
        this.url_user_pic = url_user_pic;
    }
}
