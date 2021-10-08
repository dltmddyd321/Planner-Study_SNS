package com.example.login_ex.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.login_ex.model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    //모든 노트 데이터를 호출하는 쿼리
    @Query("SELECT * FROM Notes_Database")
    LiveData<List<Notes>> getAllNotes();

    //노트 생성에 대한 쿼리
    @Insert
    void insertNotes(Notes... notes);

    //id에 한해서 노트 삭제에 대한 쿼리
    @Query("DELETE FROM Notes_Database WHERE id=:id")
    void deleteNotes(int id);

    //노트 수정에 대한 쿼리
    @Update
    void updateNotes(Notes notes);
}