package com.bignerdranch.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayoutNotes;
    FloatingActionButton floatingActionButton;

    private DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        onClickAddNotes();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showNotes();
    }

    private void initView() {
        linearLayoutNotes = findViewById(R.id.linearLayoutNotes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void showNotes() {
        linearLayoutNotes.removeAllViews();
        for (Note note : dataBase.getNotes()) {
            View view = getLayoutInflater().inflate(
                    R.layout.note_item,
                    linearLayoutNotes,
                    false);

            TextView textViewNote = view.findViewById(R.id.itemTextView);
            textViewNote.setText(note.getText());

            int colorResId;
            switch (note.getPriority()) {
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

    private void onClickAddNotes() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}