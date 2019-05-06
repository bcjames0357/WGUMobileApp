package com.example.wgumobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wgumobileapp.Models.Course;
import com.example.wgumobileapp.Models.Term;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private TextView termTitle;
    private TextView startTextView;
    private TextView endTextView;
    private TextView assessmentTextView;
    private ImageButton termDetailsDelete;
    private Button showCoursesButton;
    private Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        startTextView = (TextView) findViewById(R.id.termDetailsStart);
        endTextView = (TextView) findViewById(R.id.termDetailsEnd);
        termTitle = (TextView) findViewById(R.id.termDetailsTitle);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        term = (Term) bundle.getSerializable("termDetails");
        termTitle.setText(term.getTitle());
        startTextView.setText("Start date: " + term.getStart());
        endTextView.setText("End date: " + term.getEnd());

        showCoursesButton = (Button) findViewById(R.id.showCoursesButton);
        showCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ViewCoursesActivity.setByTermID(term.getTermID());
                ViewCoursesActivity.setByTerm(true);
                startActivity(new Intent(TermDetailsActivity.this, ViewCoursesActivity.class));
            }

        });
        termDetailsDelete = (ImageButton) findViewById(R.id.termDetailsDelete);
        termDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TermDetailsActivity.this);
                builder.setCancelable(true);
                List<Course> courseArrayList = new ArrayList<>();
                courseArrayList = MainActivity.getDatabase().getCourseDAO().getCourses();

                boolean hasAssociatedCourse = false;
                AlertDialog dialog;

                for (Course c : courseArrayList) {
                    if (c.getTerm().equals(term.getTitle())) {
                        hasAssociatedCourse = true;
                        break;
                    }
                }

                if (hasAssociatedCourse) {
                    builder.setTitle("Term Deletion Error");
                    builder.setMessage("This term cannot be deleted because it is associated with at least one course.");
                    builder.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
                } else {
                    builder.setTitle("Delete Current Term");
                    builder.setMessage("Are you sure you want to delete this term? This action cannot be undone.");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.getDatabase().getTermDAO().delete(term);
                                    Intent intent = new Intent(TermDetailsActivity.this, ViewTermsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    dialog = builder.create();
                    dialog.show();
                }
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();


    }
}