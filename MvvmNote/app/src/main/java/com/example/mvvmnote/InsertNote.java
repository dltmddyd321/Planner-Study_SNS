package com.example.mvvmnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.mvvmnote.Model.Notes;
import com.example.mvvmnote.ViewModel.NotesViewModel;
import com.example.mvvmnote.databinding.ActivityInsertNoteBinding;

import java.util.Date;
import java.util.Objects;

public class InsertNote extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    String title, subTitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("나의 메모장");

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

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

        binding.doneNoteBtn.setOnClickListener(v -> {

            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes(title, subTitle, notes);
        });
    }

    private void CreateNotes(String title, String subTitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

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