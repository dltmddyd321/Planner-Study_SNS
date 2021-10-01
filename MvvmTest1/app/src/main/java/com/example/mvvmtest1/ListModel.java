package com.example.mvvmtest1;

public class ListModel {

    private String name, mail;

    public ListModel(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public ListModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
