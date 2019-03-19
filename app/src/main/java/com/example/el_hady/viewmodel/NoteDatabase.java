package com.example.el_hady.viewmodel;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.el_hady.viewmodel.interfaces.NoteDao;
import com.example.el_hady.viewmodel.models.Note;

@Database(entities = {Note.class} , version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDao noteDao();

    //Make the NoteDatabase a singleton
    // to prevent having multiple instances of the database opened at the same time.


    public static synchronized NoteDatabase getInstance (Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomDatabaseCallback)
                    .build();
        }
        return instance;

    }
}