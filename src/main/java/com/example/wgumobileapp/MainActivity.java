package com.example.wgumobileapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.arch.persistence.room.Room;

import com.example.wgumobileapp.Receivers.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Button viewCourses;
    private Button viewAssessments;
    private Button viewMentors;
    private Button viewTerms;
    private TextView wguTextView;
    private static AppDatabase database;

    AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;
    Calendar alarmStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        viewCourses = (Button) findViewById(R.id.viewCourses);
        viewCourses.setOnClickListener(this);
        viewAssessments = (Button) findViewById(R.id.viewAssessments);
        viewAssessments.setOnClickListener(this);
        viewMentors = (Button) findViewById(R.id.viewMentors);
        viewMentors.setOnClickListener(this);
        viewTerms = (Button) findViewById(R.id.viewTerms);
        viewTerms.setOnClickListener(this);
        wguTextView = (TextView) findViewById(R.id.wguTextView);

    }

    @Override
    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId()) {
            case R.id.viewAssessments:
                intent = new Intent(this, ViewAssessmentsActivity.class);
                startActivity(intent);
                break;
            case R.id.viewCourses:
                intent = new Intent(this, ViewCoursesActivity.class);
                startActivity(intent);
                break;
            case R.id.viewMentors:
                intent = new Intent(this, ViewMentorsActivity.class);
                startActivity(intent);
                break;
            case R.id.viewTerms:
                intent = new Intent(this, ViewTermsActivity.class);
                startActivity(intent);
                break;

        }
    }

    public void resetViews(){
        ViewCoursesActivity.setByTerm(false);
        AddCourseActivity.setEditing(false);
        AddAssessmentActivity.setEditing(false);
    }

    @Override protected void onResume() {
        super.onResume();
        resetViews();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
