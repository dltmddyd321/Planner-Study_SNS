package com.example.mvvmnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.mvvmnote.Adapter.NotesAdapter;
import com.example.mvvmnote.Model.Notes;
import com.example.mvvmnote.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;
    List<Notes> filterNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("나의 메모장");

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

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNameList = notes;
            }
        });
        loadData();
    }

    private void loadData() {

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNameList = notes;
            }
        });
    }


    public void setAdapter(List<Notes> notes) {
        notesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new NotesAdapter(MainActivity.this, notes);
        notesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes,menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                NotesFilter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String s) {
        ArrayList<Notes> filterNames = new ArrayList<>();

        for(Notes notes:this.filterNameList) {
            if(notes.notesTitle.contains(s) || notes.notesSubtitle.contains(s)) {
                filterNames.add(notes);
            }
        }
        this.adapter.searchNotes(filterNames);
    }
}