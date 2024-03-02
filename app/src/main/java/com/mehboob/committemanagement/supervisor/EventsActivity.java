package com.mehboob.committemanagement.supervisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.EventAdapter;
import com.mehboob.committemanagement.common.models.Committee;
import com.mehboob.committemanagement.common.models.Event;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.databinding.ActivityEventsBinding;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    private ActivityEventsBinding binding;

    private EventAdapter adapter;
    private CommitteeViewModel committeeViewModel;
    private String comname;
    private Committee committee;

    private AuthViewModel authViewModel;
    private String supervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        committeeViewModel = new ViewModelProvider(this).get(CommitteeViewModel.class);
        comname = getIntent().getStringExtra("comname");

//        String id = FirebaseAuth.getInstance().getUid();
//       authViewModel.getCurrentUserData(id).observe(this, user -> {
//            String name = user.getFirstName() + " " + user.getLastName();
//            if (name.equals(committee.getCommitteeSupervisor())) {
//                adapter.setSupervisor(true);
//                Toast.makeText(this, ""+committee.getCommitteeSupervisor(), Toast.LENGTH_SHORT).show();
//
//            } else {
//                adapter.setSupervisor(false);
//            }
//        });


        setRecyclerView();


    }

    private void setRecyclerView() {

        adapter = new EventAdapter(this, new ArrayList<>(), "1");
        binding.recyclerViewEvents.setAdapter(adapter);
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();

//        authViewModel.getCurrentUserData(FirebaseAuth.getInstance().getUid()).observe(this, user -> {
//            String name = user.getFirstName() + " " + user.getLastName();
//            if (name.equals(committee.getCommitteeSupervisor())) {
//                adapter.setSupervisor(true);
//            } else {
//                adapter.setSupervisor(false);
//            }
//        });
        committeeViewModel.getEventsForCommittee(comname)
                .observe(this, events -> {
                    adapter.setList(events);
                    adapter.notifyDataSetChanged();

                });
    }


}
