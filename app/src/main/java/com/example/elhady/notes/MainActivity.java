package com.example.elhady.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.el_hady.notes.R;


public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageViewAddNote = findViewById(R.id.image_add_main_note);
        imageViewAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewNoteActivity.class),ADD_NOTE_REQUEST_CODE);
            }
        });

    }
}
