package com.example.el_hady.viewmodel.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.el_hady.viewmodel.NoteDatabase;
import com.example.el_hady.viewmodel.interfaces.NoteDao;
import com.example.el_hady.viewmodel.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert (Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update (Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete (Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes (Note note){
        new DeleteAllNotesNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes (Note note){
        return allNotes;

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public DeleteAllNotesNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}