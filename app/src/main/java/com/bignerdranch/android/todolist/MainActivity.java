package com.bignerdranch.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Adapter adapterNotes;

    private DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initRecyclerView();
        onClickAddNotes();
        onClickItemViewToAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void initRecyclerView() {
        adapterNotes = new Adapter();
        recyclerView.setAdapter(adapterNotes);
    }

    private void onClickItemViewToAdapter() {
        adapterNotes.setOnNoteClickListener(new Adapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                dataBase.remove(note.getId());
                showNotes();
            }
        });
    }

    private void showNotes() {
        adapterNotes.setNotes(dataBase.getNotes());
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