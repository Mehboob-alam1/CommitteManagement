package com.mehboob.committemanagement.members;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.EventAdapter;
import com.mehboob.committemanagement.common.adapters.MemberAdapter;
import com.mehboob.committemanagement.common.models.Committee;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityMyCommitteeBinding;
import com.mehboob.committemanagement.supervisor.AddAgendasActivity;
import com.mehboob.committemanagement.supervisor.AddEventsActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyCommitteeActivity extends AppCompatActivity {
    private ActivityMyCommitteeBinding binding;
    private String getIntent;
    private Gson gson;
    private Committee committee;
    private CommitteeViewModel committeeViewModel;
    private AuthViewModel authViewModel;
    private MemberAdapter adapter;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCommitteeBinding.inflate(getLayoutInflater());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        setContentView(binding.getRoot());
        gson = new Gson();

        getIntent = getIntent().getStringExtra("data");

        Type type = new TypeToken<Committee>() {
        }.getType();

        committee = gson.fromJson(getIntent, type);

        binding.txtName.setText(committee.getCommitteeSupervisor());
        binding.txtDesignation.setText("SuperVisor");


        bindData(committee);

        setRecyclerView();
        setEventRecyclerView();

        binding.btnAddAgenda.setOnClickListener(v -> {
            Intent i = new Intent(MyCommitteeActivity.this, AddAgendasActivity.class);
            i.putExtra("comname", binding.txtCommitteeName.getText().toString());
            startActivity(i);
        });

        binding.btnAddEvent.setOnClickListener(v -> {
            Intent i = new Intent(MyCommitteeActivity.this, AddEventsActivity.class);
            i.putExtra("comname", binding.txtCommitteeName.getText().toString());
            i.putExtra("superv", committee.getCommitteeSupervisor());
            startActivity(i);
        });



        authViewModel.getAgenda(binding.txtCommitteeName.getText().toString())
                .observe(this, agenda -> {

                    try {
                        binding.txtCommitteAgenda.setText(agenda.getAgendaDesc());
                        Glide.with(this)
                                .load(agenda.getImage())
                                .into(binding.imgAgenda);
                    } catch (Exception e) {

                    }

                });


    }

    private void setEventRecyclerView() {
        eventAdapter= new EventAdapter(this, new ArrayList<>(),"1");
        binding.eventsRecyclerView.setAdapter(eventAdapter);
        binding.eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setRecyclerView() {
        adapter = new MemberAdapter(this, new ArrayList<>());
        binding.membersRecyclerView.setAdapter(adapter);
        binding.membersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void bindData(Committee committee) {

        binding.txtCommitteeName.setText(committee.getCommitteeName());

    }


    @Override
    protected void onResume() {
        super.onResume();



        committeeViewModel.getAnyCommitteeMembers(committee.getCommitteeName())
                .observe(this, strings -> {

                    adapter.setMembers(strings);
                    adapter.notifyDataSetChanged();
                });

        committeeViewModel.getEventsForCommittee(committee.getCommitteeName())
                .observe(this,events -> {
                    eventAdapter.setList(events);
                    eventAdapter.notifyDataSetChanged();

                });
    }
}