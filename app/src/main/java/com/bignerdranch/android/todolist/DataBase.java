package com.bignerdranch.android.todolist;

import java.util.ArrayList;
import java.util.Random;

public class DataBase {

    private static DataBase instance = null; // Создал экземпляр класса

    public static DataBase getInstance() {   // статичный метод - патерн getInstance()
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private ArrayList<Note> notes = new ArrayList<>();

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    private DataBase() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Note note = new Note(i, "заметка" + i, random.nextInt(3));//генерирует от 0-3 случ число включ
            notes.add(note);
        }
    }

    public void add(Note note) {
        notes.add(note);
    }

    public void remove(int id) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id) {
                notes.remove(note);
            }
        }
    }
}