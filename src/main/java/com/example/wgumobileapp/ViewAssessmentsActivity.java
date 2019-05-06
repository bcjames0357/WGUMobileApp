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

import com.example.wgumobileapp.Adapters.AssessmentRecyclerViewAdapter;
import com.example.wgumobileapp.MainActivity;
import com.example.wgumobileapp.Models.Assessment;

import java.nio.channels.Selector;
import java.util.ArrayList;

public class ViewAssessmentsActivity extends AppCompatActivity implements AssessmentRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    private AssessmentRecyclerViewAdapter adapter;
    private FloatingActionButton addAssessment;
    private static RecyclerView recyclerView;
    private static ArrayList<Assessment> assessments = new ArrayList<>();
    private Selector selector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessments);

        assessments.clear();
        assessments.addAll(MainActivity.getDatabase().getAssessmentDAO().getAssessments());

        // set up the RecyclerView
        recyclerView = findViewById(R.id.assessmentsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssessmentRecyclerViewAdapter(this, assessments);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        addAssessment = (FloatingActionButton) findViewById(R.id.addAssessment);

    }

    @Override
    public void onItemClick(View view, int position) {

        Assessment details = assessments.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("assessmentDetails", details);
        Intent cartIntent = new Intent(ViewAssessmentsActivity.this, AssessmentDetailsActivity.class);
        cartIntent.putExtras(bundle);
        this.startActivity(cartIntent);
    }

    /*
    public static RecyclerView getRecyclerView()
    {
        return recyclerView;
    }
    */

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addAssessment:
                intent = new Intent(this, AddAssessmentActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        recyclerView = findViewById(R.id.assessmentsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssessmentRecyclerViewAdapter(this, assessments);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public AssessmentRecyclerViewAdapter getAdapter(){
        return adapter;
    }
}
