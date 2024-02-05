package com.mehboob.committemanagement.common.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepository {
    private FirebaseAuth firebaseAuth;

    public AuthenticationRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();
        currentUser.setValue(firebaseAuth.getCurrentUser());
        return currentUser;
    }

    public LiveData<AuthResult> signIn(String email, String password) {
        MutableLiveData<AuthResult> signInResult = new MutableLiveData<>();



        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()){
                        signInResult.setValue(task.getResult());
                    }else{
                        signInResult.setValue(null);
                    }
                }).addOnFailureListener(e -> {
                    signInResult.setValue(null);
                });
        return signInResult;
    }


    // Add other authentication methods as needed


    public LiveData<AuthResult> signUp(String email, String password) {
        MutableLiveData<AuthResult> signUpResult = new MutableLiveData<>();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> signUpResult.setValue(task.getResult()));

        return signUpResult;
    }
}
