package com.mehboob.committemanagement.supervisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.EventAdapter;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityEventsBinding;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private ActivityEventsBinding binding;

    private EventAdapter adapter;
    private CommitteeViewModel committeeViewModel;
    private  String comname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);


        comname= getIntent().getStringExtra("comname");

        setRecyclerView();

    }

    private void setRecyclerView() {

        adapter = new EventAdapter(this,new ArrayList<>(),"");
        binding.recyclerViewEvents.setAdapter(adapter);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();


        committeeViewModel.getEventsForCommittee(comname)
                .observe(this,events -> {
                    adapter.setList(events);
                    adapter.notifyDataSetChanged();

                });
    }
}
