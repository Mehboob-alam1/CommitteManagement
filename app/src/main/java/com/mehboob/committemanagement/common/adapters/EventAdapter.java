package com.mehboob.committemanagement.common.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.Event;
import com.mehboob.committemanagement.supervisor.EventsActivity;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    private Context context;
    private List<Event> list;

    private String item;
    private boolean isSupervisor;


    public EventAdapter(Context context, List<Event> list, String items) {
        this.context = context;
        this.list = list;
        this.item = items;
        this.isSupervisor = false; // Default value

    }

    public void setSupervisor(boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.events, parent, false);
        return new Holder(view);
    }

    public void setList(List<Event> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Event event = list.get(position);

        holder.txtEventName.setText(event.getEventName());
        holder.txtTimeDuration.setText(event.getEventDuration());
        holder.txtVenue.setText(event.getEventVenue());
        holder.txtEventType.setText(event.getStatus());


        if (isSupervisor) {
            holder.layout.setVisibility(View.VISIBLE);
        } else {
            holder.layout.setVisibility(View.GONE);

        }





        holder.btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        holder.btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition(); // Get the position of the item in the RecyclerView
                if (position != RecyclerView.NO_POSITION) {
                    Event event = list.get(position); // Get the event at the specified position
                    String eventId = event.getEventId(); // Assuming getId() returns the document ID

                    // Delete the document from Firestore
                    FirebaseFirestore.getInstance()
                            .collection("events")
                            .document(eventId)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully deleted
                                    list.remove(position); // Remove the event from the list
                                    notifyItemRemoved(position); // Notify the adapter that an item has been removed at the specified position
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle any errors
                                    Log.e("EventAdapter", "Error deleting document", e);
                                }
                            });
                }
            }
        });


        holder.btnEventComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (item.equals("1")) {
            return list.size();
        } else {
            return 0; // or return a different value based on your requirement
        }
    }


    public class Holder extends RecyclerView.ViewHolder {

        private TextView txtEventName, txtTimeDuration, txtVenue, txtEventType;
        private LinearLayout layout;
        private CheckBox btnEventComplete;
        private ImageView btnEditEvent, btnDeleteEvent;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtTimeDuration = itemView.findViewById(R.id.txtTimeDuration);
            txtVenue = itemView.findViewById(R.id.txtVenue);
            txtEventType = itemView.findViewById(R.id.txtEventType);
            layout = itemView.findViewById(R.id.layout);

            btnEventComplete = itemView.findViewById(R.id.btnEventComplete);
            btnEditEvent = itemView.findViewById(R.id.btnEventEdit);
            btnDeleteEvent = itemView.findViewById(R.id.btnEventDelete);


        }
    }
}
