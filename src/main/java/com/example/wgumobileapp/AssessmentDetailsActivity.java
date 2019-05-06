package com.example.wgumobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.wgumobileapp.Models.Assessment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssessmentDetailsActivity extends AppCompatActivity
{
    private TextView assessmentTitle;
    private TextView startTextView;
    private TextView notesTextView;
    private ImageButton assessmentDetailsEdit;
    private ImageButton assessmentDetailsDelete;
    private ImageButton assessmentDetailsShare;
    private Assessment assessment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        assessment = (Assessment) bundle.getSerializable("assessmentDetails");
        assessmentDetailsEdit = (ImageButton) findViewById(R.id.assessmentDetailsEdit);
        assessmentDetailsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AddAssessmentActivity.setEditing(true);
                Bundle b = new Bundle();
                b.putSerializable("assessmentEdit", assessment);
                Intent cartIntent = new Intent(AssessmentDetailsActivity.this, AddAssessmentActivity.class);
                cartIntent.putExtras(b);
                startActivity(cartIntent);
            }
        });
        assessmentDetailsDelete = (ImageButton) findViewById(R.id.assessmentDetailsDelete);
        assessmentDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentDetailsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Current Assessment");
                builder.setMessage("Are you sure you want to delete this assessment? This action cannot be undone.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.getDatabase().getAssessmentDAO().delete(assessment);
                                Intent intent = new Intent(AssessmentDetailsActivity.this, ViewAssessmentsActivity.class);
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
        assessmentDetailsShare = (ImageButton) findViewById(R.id.assessmentDetailsShare);
        assessmentDetailsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, assessment.getTitle());
                if(assessment.getNotes() != null) {
                    intent.putExtra(Intent.EXTRA_TEXT, assessment.getNotes());
                }
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });


        startTextView = (TextView) findViewById(R.id.startTextView);
        notesTextView = (TextView) findViewById(R.id.notesTextView);
        assessmentTitle = (TextView) findViewById(R.id.assessmentDetailsTitle);

        assessmentTitle.setText(assessment.getTitle());
        startTextView.setText("Due: " + assessment.getDueDate());
        if(assessment.getNotes() != null){
            notesTextView.setText("Notes: " + assessment.getNotes());
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }
}
