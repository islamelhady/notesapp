package com.example.el_hady.viewmodel.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.el_hady.viewmodel.models.Note;
import com.example.el_hady.viewmodel.repository.NoteRepository;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;

    private LiveData<List<Note>> allNotes;

    public NoteViewModel(Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert (Note note){
        repository.insert(note);
    }

    public void update (Note note){
        repository.update(note);
    }

    public void delete (Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
