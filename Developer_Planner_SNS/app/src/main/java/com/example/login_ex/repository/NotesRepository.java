package com.example.login_ex.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.login_ex.dao.NotesDao;
import com.example.login_ex.database.NotesDatabase;
import com.example.login_ex.model.Notes;

import java.util.List;

/* View Model에서 Model에 접근하기 위한 수단
쿼리를 관리하고 여러 BackEnd를 사용할 수 있도록 지원 */
public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesRepository(Application application) {
        //쿼리 내용이 있는 Dao와 접근 가능하도록 연결
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
