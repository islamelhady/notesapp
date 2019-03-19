package com.example.el_hady.viewmodel.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.el_hady.viewmodel.NoteDatabase;
import com.example.el_hady.viewmodel.interfaces.NoteDao;
import com.example.el_hady.viewmodel.models.Note;

import java.util.List;

public class WordRepository {

    private NoteDao wordDao;
    private LiveData<List<Note>> allWords;

    public WordRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAlphabetizedWords();
    }

    public LiveData<List<Note>> getAllWords() {
        return allWords;
    }


    public void insert (Note word) {
        new insertAsyncTask(wordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}