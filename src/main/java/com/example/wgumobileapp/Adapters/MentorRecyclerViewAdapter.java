package com.example.wgumobileapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wgumobileapp.Models.Mentor;
import com.example.wgumobileapp.R;

import java.util.List;

public class MentorRecyclerViewAdapter extends RecyclerView.Adapter<MentorRecyclerViewAdapter.ViewHolder> {

    private List<Mentor> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MentorRecyclerViewAdapter(Context context, List<Mentor> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mentor_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name = mData.get(position).getName();
        String phone = mData.get(position).getPhone();
        String email = mData.get(position).getEmail();

        holder.mentorRowName.setText(name);
        holder.mentorRowPhone.setText(phone);
        holder.mentorRowEmail.setText(email);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mentorRowName;
        TextView mentorRowPhone;
        TextView mentorRowEmail;

        ViewHolder(View itemView) {
            super(itemView);
            mentorRowName = itemView.findViewById(R.id.mentorRowName);
            mentorRowPhone = itemView.findViewById(R.id.mentorRowPhone);
            mentorRowEmail = itemView.findViewById(R.id.mentorRowEmail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getName();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}