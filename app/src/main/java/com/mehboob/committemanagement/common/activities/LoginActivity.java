package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setContentView(binding.getRoot());


        authViewModel.getCurrentUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                // User is authenticated, save data to Firestore
                updateUI();

            }
        });
        binding.btnSignIn.setOnClickListener(v ->{
            String password = binding.etPassword.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();

            if (TextUtils.isEmpty(password)) {
                binding.etPassword.setError("Password is required");
            } else if (!isPasswordValid(password)) {
                binding.etPassword.setError("Password should be at least 6 characters");
            } else if (TextUtils.isEmpty(email)) {
                binding.etEmail.setError("Email is required");
            } else if (!isEmailValid(email)) {
                binding.etEmail.setError("Enter a valid email address");
            }else{
                
                signInWithEmailPassword(email,password);
            }


           
        });


        binding.btnCreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CreateAcountActivity.class));
        });




    }

    private void signInWithEmailPassword(String email, String password) {

binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
       authViewModel.signIn(email,password).observe(this,authResult -> {


           if (authResult != null) {
               if (authResult.getUser() != null) {

                   // Proceed with any additional actions
                   updateUI();
               } else {
                   // Authentication failed, handle the error
                   // Display an error message or take appropriate action
                   binding.loadingLayout.getRoot().setVisibility(View.GONE);
                   Toast.makeText(this, "Authentication failed: ", Toast.LENGTH_SHORT).show();
               }
           }else {
               Log.d("AuthExcpetion","Not authenticated yet");
               binding.loadingLayout.getRoot().setVisibility(View.GONE);
           }

       });


    }

    private boolean isPasswordValid(String password) {
        // Add your password validation logic here
        // For example, check if it has a minimum length
        return password.length() >= 8;
    }

    // Validate email function
    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void updateUI(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}