package com.mehboob.committemanagement.members;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.MemberAdapter;
import com.mehboob.committemanagement.common.models.Committee;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityMyCommitteeBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyCommitteeActivity extends AppCompatActivity {
private ActivityMyCommitteeBinding binding;
private String getIntent;
private Gson gson;
private Committee committee;
private CommitteeViewModel committeeViewModel;
private MemberAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyCommitteeBinding.inflate(getLayoutInflater());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);


        setContentView(binding.getRoot());
        gson= new Gson();

        getIntent=getIntent().getStringExtra("data");

        Type type = new TypeToken<Committee>() {}.getType();

       committee=  gson.fromJson(getIntent, type);

       binding.txtName.setText(committee.getCommitteeSupervisor());
       binding.txtDesignation.setText("SuperVisor");


       bindData(committee);

       setRecyclerView();



    }

    private void setRecyclerView() {
        adapter= new MemberAdapter(this,new ArrayList<>());
        binding.membersRecyclerView.setAdapter(adapter);
        binding.membersRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void bindData(Committee committee) {

        binding.txtCommitteeName.setText(committee.getCommitteeName());

    }


    @Override
    protected void onResume() {
        super.onResume();

//        committeeViewModel.getAnyCommittee(committee.getCommitteeName())
//                .observe(this,committee1 -> {
//
//                    Toast.makeText(this, ""+committee1.toString(), Toast.LENGTH_SHORT).show();
//                });

        committeeViewModel.getAnyCommitteeMembers(committee.getCommitteeName())
                .observe(this,strings -> {

                    adapter.setMembers(strings);
                    adapter.notifyDataSetChanged();
                });
    }
}