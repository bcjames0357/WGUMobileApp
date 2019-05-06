package com.example.wgumobileapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.*;

import com.example.wgumobileapp.Adapters.AssessmentRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Assessment;
import com.example.wgumobileapp.Receivers.AlarmReceiver;

public class AddAssessmentActivity extends AppCompatActivity implements AssessmentRecyclerViewAdapter.ItemClickListener, View.OnClickListener  {

    private Button cancel;
    private Button submit;
    private RadioGroup assessmentGroup;
    private RadioButton objectiveRB;
    private int objectiveID;
    private Assessment assessment;
    private EditText titleBox;
    private EditText startBox;
    private EditText notesBox;
    private static boolean editing;
    private boolean reminder;
    private Switch reminderSwitch;
    private final String DATE_PATTERN = "((1[0-2][-/][0-3]\\d[-/]20\\d\\d)|(0?[1-9]+[-/][0-3][0-9][-/]20\\d\\d))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        assessment = new Assessment();
        titleBox = (EditText) findViewById(R.id.titleBox);
        startBox = (EditText) findViewById(R.id.startDateBox);
        notesBox = (EditText) findViewById(R.id.notesBox);
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        assessmentGroup = (RadioGroup) findViewById(R.id.assessmentGroup);
        objectiveRB = (RadioButton) findViewById(R.id.objectiveRB);
        objectiveID = objectiveRB.getId();
        reminderSwitch = (Switch) findViewById(R.id.addAssessmentReminderSwitch);
        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reminder = isChecked;
            }
        });
        reminder = reminderSwitch.isChecked();
        assessment.setObjective(assessmentGroup.getCheckedRadioButtonId() == (objectiveID));

        if(editing){
            submit.setText("Save");
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            assessment = (Assessment) bundle.getSerializable("assessmentEdit");

            titleBox.setText(assessment.getTitle());
            startBox.setText(assessment.getDueDate());
            notesBox.setText(assessment.getNotes());
            if(assessment.isObjective()){
                assessmentGroup.check(objectiveID);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAssessmentReminderSwitch:
                reminder = !reminder;
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.submit:
                assessment.setObjective(assessmentGroup.getCheckedRadioButtonId() == (objectiveID));
                if(startBox.getText().toString().trim().length() > 0
                        && Pattern.matches(DATE_PATTERN, startBox.getText())){
                    assessment.setDueDate(startBox.getText().toString());
                } else {
                    startBox.setError("Please enter a valid date in MM/DD/YYYY format.");
                    break;
                }
                if(titleBox.getText().toString().trim().length() > 0){
                    assessment.setTitle(titleBox.getText().toString());
                } else {
                    titleBox.setError("Please enter an assessment title.");
                    break;
                }
                if(notesBox.getText().toString().trim().length() > 0) {
                    assessment.setNotes(notesBox.getText().toString());
                }
                if(editing){
                    MainActivity.getDatabase().getAssessmentDAO().update(assessment);
                } else {
                    MainActivity.getDatabase().getAssessmentDAO().insert(assessment);
                }

                editing = false;
                if(reminder){
                    setReminder();
                }

                Intent intent = new Intent(AddAssessmentActivity.this, ViewAssessmentsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    public static void setEditing(boolean b){
        editing = b;
    }

    public void setReminder(){
        String notificationTitle = "Assessment Reminder";
        String notificationText = "Assessment '" + assessment.getTitle() + "' is scheduled for today.";

        Intent notificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        notificationIntent.putExtra("notificationTitle", notificationTitle);
        notificationIntent.putExtra("notificationContent", notificationText);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), assessment.getAssessmentID(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = dateFormat.parse(assessment.getDueDate());

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            notificationIntent.setAction(Long.toString(System.currentTimeMillis()));
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }
}