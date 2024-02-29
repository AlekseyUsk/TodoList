package com.bignerdranch.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.Room.NoteDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Adapter adapterNotes;
    private ItemTouchHelper itemTouchHelper;

    private NoteDataBase noteDataBase;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDataBase = NoteDataBase.getInstance(getApplication());
        handler = new Handler(Looper.getMainLooper());

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

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                noteDataBase.noteDAO().removeNote(note.getId());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showNotes();
                                    }
                                });
                            }
                        });
                        thread.start();
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showNotes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> receivedNotesFromTheDatabase = noteDataBase.noteDAO().getNote();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapterNotes.setNotes(receivedNotesFromTheDatabase);
                    }
                });
            }
        });
        thread.start();
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