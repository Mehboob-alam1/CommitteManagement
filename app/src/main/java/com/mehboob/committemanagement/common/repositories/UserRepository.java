package com.mehboob.committemanagement.common.repositories;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mehboob.committemanagement.common.models.Agenda;
import com.mehboob.committemanagement.common.models.User;

import java.util.UUID;

public class UserRepository {


    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private MutableLiveData<Boolean> ifUpload = new MutableLiveData<>();

    public UserRepository() {
        storageReference= FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveUserData(User user) {
        firestore.collection("users")
                .document(user.getUserId())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("UserRepository", "User data saved successfully"))
                .addOnFailureListener(e -> Log.e("UserRepository", "Error saving user data", e));
    }

    public MutableLiveData<Boolean> getIfUpload() {
        return ifUpload;
    }

    public void uploadCommitteeAgendaImage(String agenda, Uri image, String committeeName){

        StorageReference imageRef = storageReference.child("AgendaImages/" + UUID.randomUUID().toString());

        // Upload the image to Firebase Storage
        imageRef.putFile(image)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully, now get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Update LiveData with the download URL


                        // Call a method to store the download URL in Firestore
                        storeInFirestore(agenda,uri.toString(),committeeName);
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    ifUpload.setValue(false);
                    Log.e("YourViewModel", "Error uploading image to Firebase Storage", e);
                });


    }

    private void storeInFirestore(String agenda, String image,String committeeName) {

        Agenda data= new Agenda(agenda,image);

        firestore.collection("committees")
                .document(committeeName)
                .update("agenda",data)


                .addOnSuccessListener(documentReference -> {
                    // Document added successfully
                    ifUpload.setValue(true);
                    Log.d("UserRepository", "Image URL added to Firestore");
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    ifUpload.setValue(false);
                    Log.e("UserRepository", "Error adding image URL to Firestore", e);
                });
    }
}
