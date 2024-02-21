package com.mehboob.committemanagement.common.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.mehboob.committemanagement.common.models.User;
import com.mehboob.committemanagement.common.repositories.AuthenticationRepository;
import com.mehboob.committemanagement.common.repositories.UserRepository;

public class AuthViewModel extends ViewModel {
    private AuthenticationRepository authRepository;
    private UserRepository userRepository;

    public AuthViewModel() {
        authRepository = new AuthenticationRepository();
        userRepository = new UserRepository();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return authRepository.getCurrentUser();
    }

    public LiveData<AuthResult> signIn(String email, String password) {
        return authRepository.signIn(email, password);
    }

    public void saveUserData(User user) {
        userRepository.saveUserData(user);
    }
    public LiveData<AuthResult> signUp(String email,String password){
        return authRepository.signUp(email,password);
    }

    public void uploadCommitteeAgendaImage(String agenda, Uri image,String committeeName){
        userRepository.uploadCommitteeAgendaImage(agenda, image, committeeName);
    }

    public MutableLiveData<Boolean> getIfUpload() {
        return userRepository.getIfUpload();
    }
}
