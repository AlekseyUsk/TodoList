package com.bignerdranch.android.todolist.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.todolist.AddNoteActivity;
import com.bignerdranch.android.todolist.R;
import com.bignerdranch.android.todolist.Room.Note;
import com.bignerdranch.android.todolist.presentation.recyclerview.Adapter;
import com.bignerdranch.android.todolist.presentation.viewmodel.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Adapter adapterNotes;
    private ItemTouchHelper itemTouchHelper;

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        initView();
        initRecyclerView();
        subscribingToChangesLiveDataTransferOfTheAdapter();
        onClickAddNotes();
        setupSwipeListener();
    }

    private void subscribingToChangesLiveDataTransferOfTheAdapter() {
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapterNotes.setNotes(notes);
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void initRecyclerView() {
        adapterNotes = new Adapter();
        recyclerView.setAdapter(adapterNotes);
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
                        viewModel.remove(note);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);
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