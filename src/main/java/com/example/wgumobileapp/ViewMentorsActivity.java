package com.example.wgumobileapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wgumobileapp.Adapters.MentorRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Mentor;

import java.util.ArrayList;
import java.util.List;

public class ViewMentorsActivity extends AppCompatActivity implements MentorRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    MentorRecyclerViewAdapter adapter;
    private FloatingActionButton addMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mentors);
        ArrayList<Mentor> mentors = new ArrayList<>();
        mentors.addAll(MainActivity.getDatabase().getMentorDAO().getMentors());

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.mentorsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MentorRecyclerViewAdapter(this, mentors);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        addMentor = (FloatingActionButton) findViewById(R.id.addMentor);

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addMentor:
                intent = new Intent(this, AddMentorActivity.class);
                startActivity(intent);
                break;
        }
    }
}

