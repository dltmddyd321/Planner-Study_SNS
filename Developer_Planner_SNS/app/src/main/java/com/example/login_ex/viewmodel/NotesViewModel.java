package com.example.login_ex.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.login_ex.model.Notes;
import com.example.login_ex.repository.NotesRepository;

import java.util.List;

/*
View와 Model(Notes)를 연결하는 역할
 */
public class NotesViewModel extends AndroidViewModel {

    //연결 수단인 Repository 선언
    public NotesRepository repository;

    //ViewModel의 데이터를 관리할 LiveData 배열 선언
    public LiveData<List<Notes>> getAllNotes;

    public NotesViewModel(Application application) {
        super(application);

        //ViewModel의 모든 데이터를 연결하고 관리할 Repository 연결
        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
    }

    public void insertNote(Notes notes) {
        repository.insertNotes(notes);
    }

    public void deleteNote(int id) {
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes) {
        repository.updateNotes(notes);
    }
}