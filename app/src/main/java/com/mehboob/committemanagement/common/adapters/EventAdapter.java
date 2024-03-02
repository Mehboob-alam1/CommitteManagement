package com.mehboob.committemanagement.common.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.Committee;
import com.mehboob.committemanagement.common.models.Event;
import com.mehboob.committemanagement.common.viewmodels.CommitteeViewModel;
import com.mehboob.committemanagement.members.MyCommitteeActivity;
import com.mehboob.committemanagement.supervisor.AddEventsActivity;
import com.mehboob.committemanagement.supervisor.EventsActivity;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    private Context context;
    private List<Event> list;

    private String item;
    private  Committee committee;
    private boolean isSupervisor;


    public EventAdapter(Context context, List<Event> list, String items) {
        this.context = context;
        this.list = list;
        this.item = items;
        this.isSupervisor = false;


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


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Holder holder,  int position) {

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

        String eventId = event.getEventId();



        holder.btnDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseFirestore.getInstance().collection("committees").document(eventId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                list.remove(position);
                                // Notify the adapter of the change
                                notifyItemRemoved(position);

                                Toast.makeText(context, "events is delete"  , Toast.LENGTH_SHORT).show();
                            }
                        })
                        . addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(context, " not delete"  +e.getLocalizedMessage(),  Toast.LENGTH_SHORT).show();

                            }
                        });

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
                Intent intent = new Intent(context, AddEventsActivity.class);
                intent.putExtra("eventId", event.getEventId());
                context.startActivity(intent);


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
