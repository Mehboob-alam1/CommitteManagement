package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.User;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityCreateAcountBinding;

public class CreateAcountActivity extends AppCompatActivity {
    private ActivityCreateAcountBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAcountBinding.inflate(getLayoutInflater());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(binding.getRoot());

        binding.imgArrowBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnCreateAccount.setOnClickListener(v -> {
          binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
            if (binding.etFirstName.getText().toString().isEmpty()) {
                binding.etFirstName.setError("First name");
                binding.etFirstName.requestFocus();
            } else if (binding.etLastName.getText().toString().isEmpty()) {
                binding.etLastName.setError("Last name");
                binding.etLastName.requestFocus();
            } else if (binding.etUserName.getText().toString().isEmpty()) {
                binding.etUserName.setError("User name");
                binding.etUserName.requestFocus();
            } else if (binding.etPhoneNumber.getText().toString().isEmpty()) {
                binding.etPhoneNumber.setError("Phone number");
                binding.etPhoneNumber.requestFocus();
            } else if (TextUtils.isEmpty(binding.etPassword.getText().toString().trim())) {
                binding.etPassword.setError("Password is required");
            } else if (!isPasswordValid(binding.etPassword.getText().toString().trim())) {
                binding.etPassword.setError("Password should be at least 6 characters");
            } else if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
                binding.etEmail.setError("Email is required");
            } else if (!isEmailValid(binding.etEmail.getText().toString().trim())) {
                binding.etEmail.setError("Enter a valid email address");
            } else if (binding.etPhoneNumber.getText().length() > 11) {
                binding.etPhoneNumber.setError("Invalid");
                binding.etPhoneNumber.requestFocus();
            } else {

                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                String firstname = binding.etFirstName.getText().toString().trim();
                String lastname = binding.etLastName.getText().toString().trim();
                String username = binding.etUserName.getText().toString().trim();
                String phone = binding.etPhoneNumber.getText().toString().trim();
                authViewModel.signUp(email, password).observe(this, authResult -> {

                    if (authResult != null) {
                        if (authResult.getUser() != null) {
                            // Signup success, user is signed up
                            // You can access the authenticated user using authResult.getUser()
                            String userId = authResult.getUser().getUid();
                            User user = new User(userId,username,firstname,lastname,email,phone,password);
                            authViewModel.saveUserData(user);
                            binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
                            startActivity(new Intent(CreateAcountActivity.this,SuccessActivity.class));
                            finish();
                        } else {
                            // Signup failed, handle the error
                            // Display an error message or take appropriate action
                            binding.loadingLayout.getRoot().setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Signup failed: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
}