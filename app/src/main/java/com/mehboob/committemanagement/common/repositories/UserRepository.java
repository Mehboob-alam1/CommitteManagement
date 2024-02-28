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

public LiveData<User> getCurrentUser(String userId){

    MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the "agenda" field from the document
                        User agenda = documentSnapshot.toObject(User.class);

                        // Update the LiveData with the retrieved agenda
                        userMutableLiveData.postValue(agenda);
                    } else {
                        // Document doesn't exist
                        userMutableLiveData.postValue(null);
                        Log.d("YourRepository", "Document does not exist");
                    }
                }).addOnFailureListener(e -> {
                    userMutableLiveData.postValue(null);
            });

    return userMutableLiveData;
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

    public MutableLiveData<Agenda> getAgenda(String committeeName){
        MutableLiveData<Agenda> agendaLiveData = new MutableLiveData<>();

        firestore.collection("committees")
                .document(committeeName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the "agenda" field from the document
                        Agenda agenda = documentSnapshot.get("agenda", Agenda.class);

                        // Update the LiveData with the retrieved agenda
                        agendaLiveData.postValue(agenda);
                    } else {
                        // Document doesn't exist
                        agendaLiveData.postValue(null);
                        Log.d("YourRepository", "Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    agendaLiveData.postValue(null);
                    Log.e("YourRepository", "Error getting agenda from Firestore", e);
                });

        return agendaLiveData;

    }
}
