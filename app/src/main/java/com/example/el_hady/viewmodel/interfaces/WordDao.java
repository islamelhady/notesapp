package com.example.el_hady.viewmodel.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.el_hady.viewmodel.models.Note;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert (Note word);

    @Query("DELETE FROM Note")
    void deleteAll();

    //The (DESC) keyword sorts results in descending order.
    //Similarly, (ASC) sorts the results in ascending order.
    @Query("SELECT * FROM Note ORDER BY word ASC")
    LiveData<List<Note>> getAlphabetizedWords();
}
