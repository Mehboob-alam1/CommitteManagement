package com.mehboob.committemanagement.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.adapters.EventAdapter;
import com.mehboob.committemanagement.common.models.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ProgressEventsFragment extends Fragment {

    RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_events, container, false);


        recyclerView= view.findViewById(R.id.eventsRecyclerViewProg);

//        currentTime();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("Events");


        eventsRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Event> eventsList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Convert each document to your EventModel class (customize as per your needs)
                        Event event = documentSnapshot.toObject(Event.class);


                        eventsList.add(event);
                    }

                    // Update LiveData with the list of events
                    eventAdapter = new EventAdapter(getContext(), eventsList, "1");
                    recyclerView.setAdapter(eventAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Log.e("YourRepository", "Error getting events from Firestore", e);
                });




        return view;
    }

    private void currentTime() {
        Date currentDate = new Date();
        Calendar compareTime = Calendar.getInstance();

        String timeDifference = "";

        if (currentDate.after(compareTime.getTime())) {
            timeDifference = "Current time is after comparison time";
        } else if (currentDate.before(compareTime.getTime())) {
            timeDifference = "Current time is before comparison time";
        } else {
            timeDifference = "Current time is the same as comparison time";
        }

        System.out.println("Current Time: " + currentDate.toString());
        System.out.println("Comparison Time: " + compareTime.getTime().toString());
        System.out.println("Time Difference: " + timeDifference);

        Toast.makeText(getContext(), ""+currentDate, Toast.LENGTH_SHORT).show();
    }


    private boolean isEventInProgress(Date eventTime) {
        Date currentDate = new Date();
        return currentDate.after(eventTime) && currentDate.before(getEndTime(eventTime));
    }

    private Date getEndTime(Date eventTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(eventTime);
        calendar.add(Calendar.HOUR, 1); // Assuming events last for 1 hour
        return calendar.getTime();
    }
}