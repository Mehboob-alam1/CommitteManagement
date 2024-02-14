package com.mehboob.committemanagement.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.committemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.Holder> {


    private Context context;
    private List<String> members;

    public MemberAdapter(Context context, List<String> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_members, parent, false);
        return new Holder(view);
    }

    public void setMembers(List<String> members) {
        this.members.clear();
        this.members.addAll(members);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String data = members.get(position);

        holder.txtName.setText(data);


    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView txtName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
