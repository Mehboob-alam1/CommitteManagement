package com.mehboob.committemanagement.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.EventAdapter;
import com.mehboob.committemanagement.common.models.Event;

import java.util.ArrayList;
import java.util.List;


public class CompletedEventsFragment extends Fragment {


    RecyclerView recyclerView;
    private  EventAdapter eventAdapter;
    private FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_completed_events, container, false);

        recyclerView = view.findViewById(R.id.eventsRecyclerViewComp);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("Events");

        eventsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Event> events = new ArrayList<>();


                    for (DocumentSnapshot document : task.getResult()) {

                        Event event = document.toObject(Event.class);
                        Toast.makeText(getContext(), ""+event.getEventName(), Toast.LENGTH_SHORT).show();
                        events.add(event);
                    }

                    eventAdapter = new EventAdapter(getContext(), events, "1");
                    recyclerView.setAdapter(eventAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                } else {
                    Toast.makeText(getContext(), "document not  exist", Toast.LENGTH_SHORT).show();                        // Handle each document here
                }
            }
        });
        return  view;
    }


}