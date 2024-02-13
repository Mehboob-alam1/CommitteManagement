package com.mehboob.committemanagement.common.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.models.Committee;

import java.util.List;

public class CommitteesAdapter extends RecyclerView.Adapter<CommitteesAdapter.Holder> {

    private Context context;
    private List<Committee> committeeList;

    public CommitteesAdapter(Context context, List<Committee> committeeList) {
        this.context = context;
        this.committeeList = committeeList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.committe_layout, parent, false);
        return new Holder(view);
    }

    public void setCommitteeList(List<Committee> committeeList) {
        this.committeeList.clear();
        this.committeeList.addAll(committeeList);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Committee committee = committeeList.get(position);
        holder.txtCommitteeName.setText(committee.getCommitteeName());
        holder.txtCommitteInfo.setText("Superv: "+committee.getCommitteeSupervisor() +" , " +"Members: " +committee.getCommitteeMembersCount() );

    }

    @Override
    public int getItemCount() {
        return committeeList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtCommitteInfo, txtCommitteeName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txtCommitteeName = itemView.findViewById(R.id.txtCommitteeName);
            txtCommitteInfo = itemView.findViewById(R.id.txtCommitteInfo);
        }
    }
}
