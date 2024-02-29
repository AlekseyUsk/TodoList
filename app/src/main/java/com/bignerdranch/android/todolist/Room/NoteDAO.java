package com.bignerdranch.android.todolist.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM notes")
    List<Note> getNote();  //Важно указываем List рум может под капотом использовать разные сам по себе

    @Insert
    void addNote(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void removeNote(int id);
}