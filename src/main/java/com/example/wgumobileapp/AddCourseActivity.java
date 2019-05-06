package com.example.wgumobileapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.wgumobileapp.Adapters.CourseRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Assessment;
import com.example.wgumobileapp.Models.Course;
import com.example.wgumobileapp.Models.Mentor;
import com.example.wgumobileapp.Models.Term;
import com.example.wgumobileapp.Receivers.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class AddCourseActivity extends AppCompatActivity implements CourseRecyclerViewAdapter.ItemClickListener, View.OnClickListener  {

    private Button cancel;
    private Button submit;
    private Spinner mentorSpinner;
    private Spinner assessmentSpinner;
    private Spinner statusSpinner;
    private Spinner termSpinner;
    private Course course;
    private EditText titleBox;
    private EditText startBox;
    private EditText endBox;
    private ArrayAdapter<String> mentorAdapter;
    private List<Mentor> mentorList;
    private List<String> mentorNames;
    private ArrayAdapter<String> assessmentAdapter;
    private List<Assessment> assessmentList;
    private List<String> assessmentTitles;
    private ArrayAdapter<String> termAdapter;
    private List<Term> termList;
    private List<String> termTitles;
    private static boolean editing = false;
    private boolean startReminder;
    private boolean endReminder;

    private Switch startSwitch;
    private Switch endSwitch;

    // Pattern requires MM/DD/YYYY format. Rejects years outside of 2000-2099.
    private final String DATE_PATTERN = "((1[0-2][-/][0-3]\\d[-/]20\\d\\d)|(0?[1-9]+[-/][0-3][0-9][-/]20\\d\\d))";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        course = new Course();
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        mentorSpinner = (Spinner) findViewById(R.id.mentorSpinner);
        assessmentSpinner = (Spinner) findViewById(R.id.assessmentSpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        titleBox = (EditText) findViewById(R.id.titleBox);
        startBox = (EditText) findViewById(R.id.startDateBox);
        endBox = (EditText) findViewById(R.id.endDateBox);
        startSwitch = (Switch) findViewById(R.id.courseStartSwitch);
        startSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                startReminder = isChecked;
            }
        });
        endSwitch = (Switch) findViewById(R.id.courseEndSwitch);
        endSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                endReminder = isChecked;
            }
        });
        startReminder = startSwitch.isChecked();
        endReminder = endSwitch.isChecked();

        assessmentList = new ArrayList<>();
        assessmentList.addAll(MainActivity.getDatabase().getAssessmentDAO().getAssessments());
        assessmentTitles = new ArrayList<>();
        for(Assessment a : assessmentList){
            assessmentTitles.add(a.getTitle());
        }
        assessmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentTitles);
        assessmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentSpinner.setAdapter(assessmentAdapter);


        mentorList = new ArrayList<>();
        mentorList.addAll(MainActivity.getDatabase().getMentorDAO().getMentors());
        mentorNames = new ArrayList<>();
        for(Mentor m : mentorList) {
            mentorNames.add(m.getName());
        }
        mentorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mentorNames);
        mentorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mentorSpinner.setAdapter(mentorAdapter);

        termList = new ArrayList<>();
        termList.addAll(MainActivity.getDatabase().getTermDAO().getTerms());
        termTitles = new ArrayList<>();
        for(Term t : termList) {
            termTitles.add(t.getTitle());
        }
        termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termTitles);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(termAdapter);

        if(editing) {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            course = (Course) bundle.getSerializable("courseEdit");

            mentorSpinner.setSelection(mentorAdapter.getPosition(course.getMentor()));
            termSpinner.setSelection(termAdapter.getPosition(course.getTerm()));
            assessmentSpinner.setSelection(assessmentAdapter.getPosition(course.getAssessment()));
            statusSpinner.setSelection(((ArrayAdapter)statusSpinner.getAdapter()).getPosition(course.getStatus()));
            startBox.setText(course.getStart());
            endBox.setText(course.getEnd());
            titleBox.setText(course.getTitle());

            submit.setText("Save");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.courseStartSwitch:
                startReminder = !startReminder;
                break;
            case R.id.courseEndSwitch:
                endReminder = !endReminder;
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.submit:
                if(titleBox.getText().toString().trim().length() > 0){
                    course.setTitle(titleBox.getText().toString());
                } else {
                    titleBox.setError("Please enter a course title.");
                    break;
                }
                if(startBox.getText().toString().trim().length() > 0
                        && startBox.getText().toString().matches(DATE_PATTERN)){
                    course.setStart(startBox.getText().toString());
                } else {
                    startBox.setError("Please enter a valid course start date in MM/DD/YYYY format.");
                    break;
                }
                if(endBox.getText().toString().trim().length() > 0
                        && endBox.getText().toString().matches(DATE_PATTERN)){
                    course.setEnd(endBox.getText().toString());
                } else {
                    endBox.setError("Please enter a valid course end date in MM/DD/YYYY format.");
                    break;
                }
                if(mentorSpinner.getSelectedItem() != null){
                    course.setMentor(mentorSpinner.getSelectedItem().toString());
                }
                if(assessmentSpinner.getSelectedItem() != null){
                    course.setAssessment(assessmentSpinner.getSelectedItem().toString());
                }
                if(statusSpinner.getSelectedItem() != null){
                    course.setStatus(statusSpinner.getSelectedItem().toString());
                }
                if(termSpinner.getSelectedItem() != null){
                    course.setTerm(termSpinner.getSelectedItem().toString());
                }
                if(editing){
                    MainActivity.getDatabase().getCourseDAO().update(course);
                } else {
                    MainActivity.getDatabase().getCourseDAO().insert(course);
                }

                editing = false;

                if(startReminder){
                    setReminder(course.getStart(), "start");
                }
                if(endReminder){
                    setReminder(course.getEnd(), "end");
                }

                Intent intent = new Intent(this, ViewCoursesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    public static void setEditing(boolean e) {
        editing = e;
    }

    public void setReminder(String dateString, String startOrEnd){
        String notificationTitle = "Course Reminder";
        String notificationText = "Course '" + course.getTitle() + "' is scheduled to " + startOrEnd + " today.";

        Intent notificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        notificationIntent.putExtra("notificationTitle", notificationTitle);
        notificationIntent.putExtra("notificationContent", notificationText);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), course.getCourseID(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = dateFormat.parse(dateString);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            notificationIntent.setAction((System.currentTimeMillis()) + startOrEnd);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

}
