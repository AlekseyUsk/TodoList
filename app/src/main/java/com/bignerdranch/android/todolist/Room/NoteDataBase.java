package com.bignerdranch.android.todolist.Room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static final String ROOM_DATE_BASE = "notes_room_data_base";

    private static NoteDataBase instance = null;

    public static NoteDataBase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    NoteDataBase.class,
                    ROOM_DATE_BASE).allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract NoteDAO noteDAO();
}
