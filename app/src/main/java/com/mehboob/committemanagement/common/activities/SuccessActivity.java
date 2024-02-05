package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.databinding.ActivitySuccessBinding;

public class SuccessActivity extends AppCompatActivity {
private ActivitySuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(SuccessActivity.this, LoginActivity.class));
            finishAffinity();
        });
    }
}