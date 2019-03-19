package com.example.el_hady.viewmodel.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.el_hady.viewmodel.models.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert (Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    //The (DESC) keyword sorts results in descending order.
    //Similarly, (ASC) sorts the results in ascending order.
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
