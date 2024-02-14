package com.mehboob.committemanagement.common.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mehboob.committemanagement.common.models.Committee;

import java.util.ArrayList;
import java.util.List;

public class CommitteeRepo {

    private FirebaseFirestore firestore;
    private MutableLiveData<Boolean> isAdded;
    private MutableLiveData<List<Committee>> committeesLiveData;
    private MutableLiveData<List<String>> committeeMutableLiveData;

    public CommitteeRepo() {
        firestore = FirebaseFirestore.getInstance();
        isAdded = new MutableLiveData<>();
        committeesLiveData= new MutableLiveData<>();
        committeeMutableLiveData= new MutableLiveData<>();
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


    public MutableLiveData<List<String>> getAnyCommitteeMembers(String committeeName){
        firestore.collection("committees")
                .document(committeeName)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isComplete()){
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {
                            // Retrieve the 'members' field which is assumed to be an array
                            List<String> members = (List<String>) document.get("committeeMembers");


                            committeeMutableLiveData.setValue(members);
                            // 'members' now contains the list of committee members
                            Log.d("CommitteeMembers", "Members: " + members.toString());
                        } else {
                            Log.d("CommitteeMembers", "Committee document not found!");
                        }

                    }else{

                    }
                }).addOnFailureListener(e -> {
                    Log.d("Error",e.getLocalizedMessage());
                });

        return  committeeMutableLiveData;
    }

}
