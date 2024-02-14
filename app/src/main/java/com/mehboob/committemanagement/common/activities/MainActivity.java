package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setContentView(binding.getRoot());

        binding.cardCommitte.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, ComitteesActivity.class));

        });







    }

    @Override
    protected void onResume() {
        super.onResume();

        authViewModel.getCurrentUser().observe(this,firebaseUser -> {


            binding.txtEmail.setText(firebaseUser.getEmail());

        });
    }
}