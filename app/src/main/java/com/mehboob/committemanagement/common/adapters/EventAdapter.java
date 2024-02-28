package com.mehboob.committemanagement.common.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.Event;
import com.mehboob.committemanagement.supervisor.EventsActivity;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    private Context context;
    private List<Event> list;

    private String item;

    public EventAdapter(Context context, List<Event> list,String items) {
        this.context = context;
        this.list = list;
        this.item=items;
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



//        holder.itemView.setOnClickListener(v -> {
//            Intent i = new Intent(context, EventsActivity.class);
//            context.startActivity(i);
//
//        });

    }
    @Override
    public int getItemCount() {
        if (item.equals("1")) {
            return list.size();
        } else {
            return 0; // or return a different value based on your requirement
        }
    }


//    @Override
//    public int getItemCount() {
//        if (item.equals("1"))
//
//            return list.size();
//        else
//            return list.size();
//
//
//    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView txtEventName, txtTimeDuration, txtVenue, txtEventType;
        private LinearLayout layout;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtTimeDuration = itemView.findViewById(R.id.txtTimeDuration);
            txtVenue = itemView.findViewById(R.id.txtVenue);
            txtEventType = itemView.findViewById(R.id.txtEventType);
        }
    }
}
