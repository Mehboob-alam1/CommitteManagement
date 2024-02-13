package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardCommitte.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, ComitteesActivity.class));

        });






    }
}