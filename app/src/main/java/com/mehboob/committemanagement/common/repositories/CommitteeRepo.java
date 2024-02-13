package com.mehboob.committemanagement.common.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mehboob.committemanagement.common.models.Committee;

import java.util.ArrayList;
import java.util.List;

public class CommitteeRepo {

    private FirebaseFirestore firestore;
    private MutableLiveData<Boolean> isAdded;
    private MutableLiveData<List<Committee>> committeesLiveData;

    public CommitteeRepo() {
        firestore = FirebaseFirestore.getInstance();
        isAdded = new MutableLiveData<>();
        committeesLiveData= new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(MutableLiveData<Boolean> isAdded) {
        this.isAdded = isAdded;
    }

    public void saveCommittees(Committee committee) {

        firestore.collection("committees")
                .document(committee.getCommitteeName())
                .set(committee)
                        .
                addOnSuccessListener(aVoid -> {
                    Log.d("CommitteeRepo", "Committee data saved successfully");
                    isAdded.setValue(true);
                })
                .addOnFailureListener(e ->{

                    Log.e("CommitteeRepo", "Error saving Committee data", e);
                    isAdded.setValue(false);
                });
    }

    public MutableLiveData<List<Committee>> getCommittees(){
        firestore.collection("committees")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Committee> committees = new ArrayList<>();
                    for (QueryDocumentSnapshot document:queryDocumentSnapshots){

                    Committee committee=    document.toObject(Committee.class);
                    committees.add(committee);


                    }
                    committeesLiveData.setValue(committees);
                }).addOnFailureListener(e -> {
                    Log.d("Error",e.getLocalizedMessage());
                });

        return committeesLiveData;
    }


}
