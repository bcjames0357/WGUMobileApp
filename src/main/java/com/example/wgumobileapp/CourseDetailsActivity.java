package com.example.wgumobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wgumobileapp.Models.Course;

public class CourseDetailsActivity extends AppCompatActivity {
    private TextView courseTitle;
    private TextView startTextView;
    private TextView endTextView;
    private TextView assessmentTextView;
    private ImageButton courseDetailsEdit;
    private ImageButton courseDetailsDelete;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        course = (Course) bundle.getSerializable("courseDetails");
        courseDetailsEdit = (ImageButton) findViewById(R.id.courseDetailsEdit);
        courseDetailsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AddCourseActivity.setEditing(true);
                Bundle b = new Bundle();
                b.putSerializable("courseEdit", course);
                Intent cartIntent = new Intent(CourseDetailsActivity.this, AddCourseActivity.class);
                cartIntent.putExtras(b);
                startActivity(cartIntent);
            }
        });
        courseDetailsDelete = (ImageButton) findViewById(R.id.courseDetailsDelete);
        courseDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Current Course");
                builder.setMessage("Are you sure you want to delete this course? This action cannot be undone.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.getDatabase().getCourseDAO().delete(course);
                                Intent intent = new Intent(CourseDetailsActivity.this, ViewCoursesActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        startTextView = (TextView) findViewById(R.id.courseDetailsStart);
        endTextView = (TextView) findViewById(R.id.courseDetailsEnd);
        courseTitle = (TextView) findViewById(R.id.courseDetailsTitle);
        assessmentTextView = (TextView) findViewById(R.id.courseDetailsAssessments);



        courseTitle.setText(course.getTitle());
        startTextView.setText("Start date: " + course.getStart());
        endTextView.setText("End date: " + course.getEnd());
        if (course.getAssessment() == null) {
            assessmentTextView.setVisibility(View.GONE);
        } else {
            assessmentTextView.setText("Assessments: " + course.getAssessment());
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
