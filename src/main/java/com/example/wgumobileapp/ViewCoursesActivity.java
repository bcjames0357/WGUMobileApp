package com.example.wgumobileapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.wgumobileapp.Adapters.CourseRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Course;

import java.util.ArrayList;
import java.util.List;

public class ViewCoursesActivity extends AppCompatActivity implements CourseRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    CourseRecyclerViewAdapter adapter;
    private FloatingActionButton addCourse;
    private static ArrayList<Course> courses = new ArrayList<>();
    private static boolean byTerm = false;
    private static int byTermID = 0;
    private String byTermTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.coursesRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courses.clear();

        if(byTerm){
            ArrayList<Course> ct = new ArrayList<>();
            ct.addAll(MainActivity.getDatabase().getCourseDAO().getCourses());
            byTermTitle = MainActivity.getDatabase().getTermDAO().getTermByID(byTermID).getTitle();

            for(Course c : ct) {
                if(c.getTerm().equals(byTermTitle)){
                    courses.add(c);
                }
            }
        } else {
            courses.addAll(MainActivity.getDatabase().getCourseDAO().getCourses());
        }

        adapter = new CourseRecyclerViewAdapter(this, courses);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        addCourse = (FloatingActionButton) findViewById(R.id.addCourse);

    }

    @Override
    public void onItemClick(View view, int position) {
        Course details = courses.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("courseDetails", details);
        Intent cartIntent = new Intent(ViewCoursesActivity.this, CourseDetailsActivity.class);
        cartIntent.putExtras(bundle);
        this.startActivity(cartIntent);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addCourse:
                intent = new Intent(this, AddCourseActivity.class);
                startActivity(intent);
                break;
        }
    }

    public static void setByTermID(int ID){
        byTermID = ID;
    }

    public int getByTermID(){
        return byTermID;
    }

    public static void setByTerm(boolean b){
        byTerm = b;
    }

    public boolean getByTerm() {
        return byTerm;
    }

}

