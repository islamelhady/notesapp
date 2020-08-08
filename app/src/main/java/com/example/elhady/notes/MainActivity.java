package com.example.elhady.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.elhady.notes.adapter.NoteAdapter;
import com.example.elhady.notes.database.NoteDatabase;
import com.example.elhady.notes.models.Note;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST_CODE = 1;
    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageViewAddNote = findViewById(R.id.image_add_main_note);
        imageViewAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewNoteActivity.class), ADD_NOTE_REQUEST_CODE);
            }
        });

        notesRecyclerView = findViewById(R.id.notes_recycler_view);
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList);
        notesRecyclerView.setAdapter(adapter);

        getNotes();
    }

    private void getNotes() {

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
//                we checked if the note list is empty it means it
//                the app is just started since we have declared it as a global variable,
//                in this case , we are adding all notes from the database to this note list
//                and notify the adapter about the new dataset. in another case,
//                if the note list is not empty then it means notes are already loaded from
//                the database so we are just adding only the latest note to the note list and
//                notify adapter about new note inserted. and last we scrolled our adding only the
//                latest note to the note list and notify adapter about nwe note inserted.
//                and last we scrolled our recycler view to the top
                if (noteList.size() == 0) {
                    noteList.addAll(notes);
                    adapter.notifyDataSetChanged();
                } else {
                    noteList.add(0, notes.get(0));
                    adapter.notifyItemInserted(0);
                }
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST_CODE && requestCode == RESULT_OK) ;
        getNotes();
    }
}
