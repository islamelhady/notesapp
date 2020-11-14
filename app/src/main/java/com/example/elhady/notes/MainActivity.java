package com.example.elhady.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.elhady.notes.adapter.NoteAdapter;
import com.example.elhady.notes.database.NoteDatabase;
import com.example.elhady.notes.listeners.NotesListeners;
import com.example.elhady.notes.models.Note;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NotesListeners {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTE = 3;
    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NoteAdapter adapter;

    private int noteClickedPosition = -1;

    private ToggleButton switchCompat;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        switchCompat = findViewById(R.id.switchCompat);

        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
//            imageView.setImageResource(R.drawable.night);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switchCompat.setChecked(false);
        }


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
//                    imageView.setImageResource(R.drawable.night);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);
//                    imageView.setImageResource(R.drawable.night);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();

                }
            }
        });


        ImageView imageViewAddNote = findViewById(R.id.image_add_main_note);
        imageViewAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewNoteActivity.class), REQUEST_CODE_ADD_NOTE);
            }
        });

        notesRecyclerView = findViewById(R.id.notes_recycler_view);
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList, this);
        notesRecyclerView.setAdapter(adapter);

        // This getNotes() method is called from onCreate() method of an activity. it means
        // the application is just started and we need to display all notes from the database
        // and that's why we are passing REQUEST_CODE_SHOW_NOTES to that method.

        // Here, request code is REQUEST_CODE_SHOW_NOTE, it means we are displaying all notes from the database
        // and therefore as a parameter isNoteDeleted we are passing 'false'
        getNotes(REQUEST_CODE_SHOW_NOTE, false);
    }


    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                // Here, request code is REQUEST_CODE_SHOW_NOTE, so we are adding all notes from database to
                // noteList and notify adapter about the new data set.
                if (requestCode == REQUEST_CODE_SHOW_NOTE){
                    noteList.addAll(notes);
                    adapter.notifyDataSetChanged();
                }else if (requestCode == REQUEST_CODE_ADD_NOTE){
                    noteList.add(0, notes.get(0));
                    adapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                    // Here, request code is REQUEST_CODE_UPDATE_NOTE, so we are removing note from the clicked position
                    // and adding the latest updated note from same position from the database and notify the adapter for item changed at the position


                    // if request code is REQUEST_CODE_UPDATE_NOTE, First, we remove note from list.
                    // Then we checked whether the note is deleted or not.
                    // if the note is deleted then notifying adapter about item removed.
                    // if the note is not deleted then it must be updated that's why we are adding a newly updated
                    // note to that same position where we removed and notifying adapter about item changed.
                }else if (requestCode == REQUEST_CODE_UPDATE_NOTE){
                    noteList.remove(noteClickedPosition);
                    if (isNoteDeleted){
                        adapter.notifyItemRemoved(noteClickedPosition);
                    }else {
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        adapter.notifyItemChanged(noteClickedPosition);
                    }
                }
            }
        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && requestCode == RESULT_OK) {
            // This getNotes() method is called from the onActivityResult() method of activity and we checked the
            // current request code is for add note and the result is RESULT_OK. it means a new note is added from CreateNote
            // activity and its result is sent back to this activity that's why we are passing REQUEST_CODE_ADD_NOTE to that method.

            // Here, request code is REQUEST_CODE_ADD_NOTE, it means we have added a new note to the database
            // and therefore as a parameter isNoteDeleted we are passing 'false'
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        }else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK){
            if (data != null){
                // This getNotes() method is called from the onActivityResult() method of activity and we checked
                // the current request code is for update note and the result is RESULT_OK. it means already
                // available note is updated from CreateNote activity and its result is sent back to this activity
                // that's why we are passing REQUEST_CODE_UPDATE_NOTE to that method.

                // Here, request code is REQUEST_CODE_UPDATE_NOTE, it means we are updating already available note from the database,
                // and it may be possible that note gets deleted therefore as a parameter isNoteDeleted, we are passing value from
                // CreateNoteActivity, whether the note is deleted or not using intent data with key "isNoteDeleted"
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        }

    }
}
