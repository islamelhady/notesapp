package com.example.elhady.notes.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.elhady.notes.models.Note;

import java.util.List;

public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote (Note note);

    @Delete()
    void deleteNote(Note note);

    //The (DESC) keyword sorts results in descending order.
    //Similarly, (ASC) sorts the results in ascending order.
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();
}
