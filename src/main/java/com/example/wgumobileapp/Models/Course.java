package com.example.wgumobileapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="courses")
public class Course implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int courseID;
    private String title;
    private String start;
    private String end;
    private String mentor;
    private String assessment;
    private String status;
    private String term;

    public Course() { }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getMentor() { return mentor; }

    public void setMentor(String mentor) { this.mentor = mentor; }

    public String getAssessment() { return assessment; }

    public void setAssessment(String assessment) { this.assessment = assessment; }

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status; }

    public String getTerm() { return term; }

    public void setTerm(String term) { this.term = term; }
}

