package com.example.mvvmnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmnote.Model.Notes;
import com.example.mvvmnote.ViewModel.NotesViewModel;
import com.example.mvvmnote.databinding.ActivityUpdateNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;
import java.util.Objects;

public class UpdateNote extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    String priority = "1";
    String sTitle, sSubtitle, sNotes, sPriority;
    NotesViewModel notesViewModel;
    int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("나의 메모장");

        iid = getIntent().getIntExtra("id",0);
        sTitle = getIntent().getStringExtra("title");
        sSubtitle = getIntent().getStringExtra("subtitle");
        sPriority = getIntent().getStringExtra("priority");
        sNotes = getIntent().getStringExtra("note");

        binding.upTitle.setText(sTitle);
        binding.upSubTitle.setText(sSubtitle);
        binding.upNotes.setText(sNotes);

        if(sPriority.equals("1")) {
            binding.redPriority.setImageResource(R.drawable.ic_baseline_check_24);
        } else if(sPriority.equals("2")) {
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_check_24);
        } else if(sPriority.equals("3")) {
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_check_24);
        }

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

        binding.updateNoteBtn.setOnClickListener(v -> {

            String title = binding.upTitle.getText().toString();
            String subTitle = binding.upSubTitle.getText().toString();
            String notes = binding.upNotes.getText().toString();

            UpdateNotes(title, subTitle, notes);

        });
    }

    private void UpdateNotes(String title, String subTitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes updateNotes = new Notes();

        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subTitle;
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(this,"수정 완료!",Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNote.this);

            View view = LayoutInflater.from(UpdateNote.this).inflate(R.layout.delete_bottom,(LinearLayout)findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes, no;

            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                finish();
            });

            no.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });

            sheetDialog.show();
        }

        return true;
    }
}