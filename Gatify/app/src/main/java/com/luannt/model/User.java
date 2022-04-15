package com.luannt.model;

public class User {
    private String username,email,isVip,expried_date,url_user_pic;

    public User(String username, String email, String isVip, String expried_date, String url_user_pic) {
        this.username = username;
        this.email = email;
        this.isVip = isVip;
        this.expried_date = expried_date;
        this.url_user_pic = url_user_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getExpried_date() {
        return expried_date;
    }

    public void setExpried_date(String expried_date) {
        this.expried_date = expried_date;
    }

    public String getUrl_user_pic() {
        return url_user_pic;
    }

    public void setUrl_user_pic(String url_user_pic) {
        this.url_user_pic = url_user_pic;
    }
}
