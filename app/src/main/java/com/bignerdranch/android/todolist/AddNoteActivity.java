package com.bignerdranch.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;
import com.bignerdranch.android.todolist.presentation.viewmodel.AddNoteActivityViewModel;
import com.bignerdranch.android.todolist.presentation.viewmodel.ViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private TextView editTextNote;
    private RadioGroup radioGroupNote;
    private RadioButton radioBtnLow;
    private RadioButton radioBtnMedium;
    private RadioButton radioBtnHigh;
    private Button btnSve;

    private AddNoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        viewModel = new ViewModelProvider(this).get(AddNoteActivityViewModel.class);

        closeScreen();
        initView();
        onClickSaveNote();
    }

    private void initView() {
        editTextNote = findViewById(R.id.editTextNote);
        radioGroupNote = findViewById(R.id.radioGroupNote);
        radioBtnLow = findViewById(R.id.radioBtnLow);
        radioBtnMedium = findViewById(R.id.radioBtnMedium);
        radioBtnHigh = findViewById(R.id.radioBtnHigh);
        btnSve = findViewById(R.id.btnSve);
    }

    private void onClickSaveNote() {
        btnSve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editTextNote.getText().toString().trim();
                int priority = getPriority();
                Note note = new Note(0, text, priority);
                viewModel.saveNote(note);
            }
        });
    }

    private void closeScreen(){
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
    }

    private int getPriority() {
        int priority;
        if (radioBtnLow.isChecked()) {
            priority = 0;
        } else if (radioBtnMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }
}