package com.example.login_ex.model;

public class Post {
    int profileImg;
    String userName;
    int img;
    String message;


    public Post(int profileImg, String userName, int img, String message) {
        this.profileImg = profileImg;
        this.userName = userName;
        this.img = img;
        this.message = message;
    }

    public int getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(int profileImg) {
        this.profileImg = profileImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

