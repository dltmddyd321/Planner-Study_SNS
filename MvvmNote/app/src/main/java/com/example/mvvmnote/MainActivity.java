package com.example.mvvmnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mvvmnote.Adapter.NotesAdapter;
import com.example.mvvmnote.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotesBtn = findViewById(R.id.addNoteBtn);
        notesRecycler = findViewById(R.id.notesRecycler);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        addNotesBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, InsertNote.class));
        });

        notesViewModel.getAllNotes.observe(this, notes -> {
            notesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new NotesAdapter(MainActivity.this, notes);
            notesRecycler.setAdapter(adapter);
        });
    }
}