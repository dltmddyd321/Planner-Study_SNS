package com.example.login_ex.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.login_ex.R;
import com.example.login_ex.adapter.NotesAdapter;
import com.example.login_ex.model.Notes;
import com.example.login_ex.viewmodel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoActivity extends AppCompatActivity {

    FloatingActionButton addNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;
    List<Notes> filterNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        Objects.requireNonNull(getSupportActionBar()).setTitle("나의 메모장");

        addNotesBtn = findViewById(R.id.addNoteBtn);
        notesRecycler = findViewById(R.id.notesRecycler);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        addNotesBtn.setOnClickListener(view -> {
            startActivity(new Intent(MemoActivity.this, InsertMemo.class));
        });

        //LiveData 객체에 대한 변화(추가 및 삭제)를 감지하고 변경 사항에 대한 반응
        notesViewModel.getAllNotes.observe(this, notes -> {
            notesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new NotesAdapter(MemoActivity.this, notes);
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

        //검색 결과에 대한 데이터를 호출하기 위한 함수
        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNameList = notes;
            }
        });
    }


    public void setAdapter(List<Notes> notes) {
        //Grid Layout 형태로 레이아웃 설정
        notesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new NotesAdapter(MemoActivity.this, notes);
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
                //검색 창에 특정 Text(s)가 입력된다면 검색 필터 함수 작동
                NotesFilter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String s) {
        ArrayList<Notes> filterNames = new ArrayList<>();

        //메모 데이터가 담긴 리스트를 순회
        for(Notes notes:this.filterNameList) {
            //만일 제목 또는 부제목에 입력한 s 값을 포함된다면 해당하는 데이터를 리스트에 추가
            if(notes.notesTitle.contains(s) || notes.notesSubtitle.contains(s)) {
                filterNames.add(notes);
            }
        }
        this.adapter.searchNotes(filterNames);
    }
}