package com.mehboob.committemanagement.common.repositories;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mehboob.committemanagement.common.models.User;

public class UserRepository {


    private FirebaseFirestore firestore;

    public UserRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveUserData(User user) {
        firestore.collection("users")
                .document(user.getUserId())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("UserRepository", "User data saved successfully"))
                .addOnFailureListener(e -> Log.e("UserRepository", "Error saving user data", e));
    }
}
