package com.example.mvvmnote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvvmnote.databinding.ActivityUpdateNoteBinding;

public class UpdateNote extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            
        });
    }
}