package com.example.firestoretest;

public class Input {
    String img;
    String message;

    public Input(){}

    public Input(String img, String message) {
        this.img = img;
        this.message = message;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}