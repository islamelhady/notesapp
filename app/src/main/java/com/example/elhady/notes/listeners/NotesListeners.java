package com.example.elhady.notes.listeners;

import com.example.elhady.notes.models.Note;

public interface NotesListeners {
    void onNoteClicked(Note note, int position);
}
