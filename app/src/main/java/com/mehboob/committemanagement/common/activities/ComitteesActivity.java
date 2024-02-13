package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.CommitteesAdapter;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityComitteesBinding;

import java.util.ArrayList;

public class ComitteesActivity extends AppCompatActivity {
    private ActivityComitteesBinding binding;
    private CommitteeViewModel committeeViewModel;
    private CommitteesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComitteesBinding.inflate(getLayoutInflater());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);

        setContentView(binding.getRoot());


        setRecyclerView();

    }

    private void setRecyclerView() {
        adapter = new CommitteesAdapter(this, new ArrayList<>());
        binding.recyclerCommittees.setAdapter(adapter);
        binding.recyclerCommittees.setLayoutManager(new LinearLayoutManager(this));

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();


        committeeViewModel.getCommittees().observe(this, committees -> {

            adapter.setCommitteeList(committees);
            adapter.notifyDataSetChanged();
        });
    }
}