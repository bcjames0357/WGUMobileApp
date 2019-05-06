package com.example.wgumobileapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wgumobileapp.AssessmentDetailsActivity;
import com.example.wgumobileapp.MainActivity;
import com.example.wgumobileapp.Models.Assessment;
import com.example.wgumobileapp.R;
import com.example.wgumobileapp.ViewAssessmentsActivity;

import java.util.List;

public class AssessmentRecyclerViewAdapter extends RecyclerView.Adapter<AssessmentRecyclerViewAdapter.ViewHolder> {

    private List<Assessment> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public int selectedPos = RecyclerView.NO_POSITION;
    private static final String TAG = AssessmentRecyclerViewAdapter.class.getSimpleName();

    // data is passed into the constructor
    public AssessmentRecyclerViewAdapter(Context context, List<Assessment> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.assessment_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String assessment = mData.get(position).getTitle();
        holder.myTextView.setText(assessment);
        holder.itemView.setSelected(selectedPos == position);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.assessmentRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "AssRVAdapter onClick");
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getTitle();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

