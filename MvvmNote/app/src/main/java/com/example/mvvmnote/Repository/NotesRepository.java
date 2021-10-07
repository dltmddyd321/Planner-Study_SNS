package com.example.mvvmnote.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mvvmnote.Dao.NotesDao;
import com.example.mvvmnote.Database.NotesDatabase;
import com.example.mvvmnote.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();

    }

    public void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }
}
