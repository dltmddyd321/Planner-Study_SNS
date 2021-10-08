package com.example.login_ex.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.login_ex.R;
import com.example.login_ex.databinding.ActivityInsertMemoBinding;
import com.example.login_ex.model.Notes;
import com.example.login_ex.viewmodel.NotesViewModel;

import java.util.Date;
import java.util.Objects;

public class InsertMemo extends AppCompatActivity {

    ActivityInsertMemoBinding binding;
    String title, subTitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertMemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("나의 메모장");

        //데이터를 제공할 ViewModel을 ViewModelProviders 통해 호출
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //테마 색상 버튼 클릭에 따른 이미지 변화
        binding.redPriority.setOnClickListener(view -> {

            binding.redPriority.setImageResource(R.drawable.ic_baseline_check_24);
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);

            priority = "1";
        });
        binding.greenPriority.setOnClickListener(view -> {

            binding.redPriority.setImageResource(0);
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_check_24);
            binding.yellowPriority.setImageResource(0);

            priority = "2";
        });
        binding.yellowPriority.setOnClickListener(view -> {

            binding.redPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_check_24);

            priority = "3";
        });

        //새로운 메모를 입력하고 데이터 생성
        binding.doneNoteBtn.setOnClickListener(v -> {

            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes(title, subTitle, notes);
        });
    }

    private void CreateNotes(String title, String subTitle, String notes) {

        //날짜 삽입
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        //새로운 메모 데이터를 삽입하고 ViewModel을 통해 DB 추가
        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subTitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();
        notesViewModel.insertNote(notes1);

        Toast.makeText(this,"등록 완료!",Toast.LENGTH_SHORT).show();

        finish();
    }
}