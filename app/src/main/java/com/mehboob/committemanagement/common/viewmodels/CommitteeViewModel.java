package com.mehboob.committemanagement.common.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mehboob.committemanagement.common.models.Committee;
import com.mehboob.committemanagement.common.repositories.CommitteeRepo;

import java.util.List;

public class CommitteeViewModel extends ViewModel {

private CommitteeRepo committeeRepo;
private MutableLiveData<Boolean> isAdded;
    public CommitteeViewModel() {
        committeeRepo = new CommitteeRepo();
        isAdded=committeeRepo.getIsAdded();

    }

    public void saveCommittee(Committee committee){

        committeeRepo.saveCommittees(committee);
    }

    public MutableLiveData<Boolean> getIsAdded() {
        return isAdded;
    }


    public MutableLiveData<List<Committee>> getCommittees(){
       return committeeRepo.getCommittees();
    }

    public MutableLiveData<List<String>> getAnyCommitteeMembers(String committeName){
        return committeeRepo.getAnyCommitteeMembers(committeName);
    }
}
