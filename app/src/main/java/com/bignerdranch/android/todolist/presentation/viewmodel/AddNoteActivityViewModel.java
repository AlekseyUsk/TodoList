package com.bignerdranch.android.todolist.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;

public class AddNoteActivityViewModel extends AndroidViewModel {

    private NoteDataBase noteDataBase;

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    public AddNoteActivityViewModel(@NonNull Application application) {
        super(application);
        noteDataBase = NoteDataBase.getInstance(application);
    }

    public void saveNote(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDataBase.noteDAO().addNote(note);
                shouldCloseScreen.postValue(true);
            }
        });
        thread.start();
    }
}
