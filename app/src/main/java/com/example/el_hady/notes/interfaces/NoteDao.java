package com.example.el_hady.notes.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.el_hady.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert (Note note);

    @Update
    void update (Note note);

    @Delete
    void delete (Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    //The (DESC) keyword sorts results in descending order.
    //Similarly, (ASC) sorts the results in ascending order.
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();
}
