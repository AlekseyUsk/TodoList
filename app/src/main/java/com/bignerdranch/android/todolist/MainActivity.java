package com.bignerdranch.android.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayoutNotes;
    FloatingActionButton floatingActionButton;

    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        simulationOfRandomNotes();
        showNotes();
    }

    private void initView() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void simulationOfRandomNotes() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "заметка" + i, random.nextInt(3));//генерирует от 0-3 случ число включ
            notes.add(note);
        }
    }

    private void showNotes() {
        for (Note note : notes) {
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearLayoutNotes,
                    false);

            TextView textViewNote = view.findViewById(R.id.itemTextView);
            textViewNote.setText(note.getText());

            int colorResId;
            switch (note.getId()) {
                case 0:
                    colorResId = android.R.color.holo_green_light;
                    break;
                case 1:
                    colorResId = android.R.color.holo_orange_light;
                    break;
                default:
                    colorResId = android.R.color.holo_red_light;
                    break;
            }
            int color = ContextCompat.getColor(this, colorResId);
            textViewNote.setBackgroundColor(color);
            linearLayoutNotes.addView(view);
        }
    }
}