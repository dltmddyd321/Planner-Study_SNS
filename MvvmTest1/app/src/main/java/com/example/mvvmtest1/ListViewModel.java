package com.example.mvvmtest1;

import androidx.lifecycle.ViewModel;

public class ListViewModel extends ViewModel {

    public String name, mail;

    public ListViewModel () {
    }

    public ListViewModel(ListModel listModel) {
        this.name = name;
        this.mail = mail;
    }

}
