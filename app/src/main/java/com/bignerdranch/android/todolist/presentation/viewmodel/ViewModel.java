package com.bignerdranch.android.todolist.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase;

    public ViewModel(@NonNull Application application) {
        super(application);
        noteDataBase = NoteDataBase.getInstance(application);
    }

    public LiveData<List<Note>> getNotes() {
        noteDataBase.noteDAO().getNote();
        return noteDataBase.noteDAO().getNote();
    }

    public void remove(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.noteDAO().removeNote(note.getId());
            }
        });
        thread.start();
    }
}
