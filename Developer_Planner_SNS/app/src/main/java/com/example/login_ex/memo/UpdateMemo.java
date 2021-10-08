package com.example.login_ex.memo;

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

import com.example.login_ex.R;
import com.example.login_ex.databinding.ActivityUpdateMemoBinding;
import com.example.login_ex.model.Notes;
import com.example.login_ex.viewmodel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;
import java.util.Objects;

public class UpdateMemo extends AppCompatActivity {

    ActivityUpdateMemoBinding binding;
    String priority = "1";
    String sTitle, sSubtitle, sNotes, sPriority;
    NotesViewModel notesViewModel;
    int iid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateMemoBinding.inflate(getLayoutInflater());
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

        //날짜 삽입
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        //새로운 메모 데이터를 삽입하고 ViewModel을 통해 DB 추가
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

    //메뉴 옵션을 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete) {
            //하단 다이얼로그 생성
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateMemo.this);

            //View를 통해 팝업할 다이얼로그 레이아웃 지정
            View view = LayoutInflater.from(UpdateMemo.this).inflate(R.layout.delete_bottom,(LinearLayout)findViewById(R.id.bottomSheet));

            //하단 다이얼로그 팝업
            sheetDialog.setContentView(view);

            TextView yes, no;

            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v -> {
                //해당 iid에 해당하는 데이터를 ViewModel 통해 삭제
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