package com.bignerdranch.android.todolist.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        noteDataBase.noteDAO().addNote(note)
                .subscribeOn(Schedulers.io())              // перекл на фоновый поток
                .observeOn(AndroidSchedulers.mainThread()) //перекл на главный выпоняется все что внизжу в главном потоке
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);
                    }
                });
    }
}
