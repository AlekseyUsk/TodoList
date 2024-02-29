package com.bignerdranch.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Adapter adapterNotes;
    private ItemTouchHelper itemTouchHelper;

    private NoteDataBase noteDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDataBase = NoteDataBase.getInstance(getApplication());

        initView();
        initRecyclerView();
        onClickAddNotes();
        setupSwipeListener();


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

    private void DeletingByClickingOnAnElementInTheAdapter() {
        adapterNotes.setOnNoteClickListener(new Adapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                noteDataBase.noteDAO().removeNote(note.getId());
                showNotes();
            }
        });
    }

    private void setupSwipeListener() {
        itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback
                        (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = adapterNotes.getNotes().get(position);
                        noteDataBase.noteDAO().removeNote(note.getId());
                        showNotes();
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showNotes() {
        adapterNotes.setNotes(noteDataBase.noteDAO().getNote());
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