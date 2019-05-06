package com.example.wgumobileapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName="assessments")
public class Assessment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int assessmentID;
    private String title;
    private String dueDate;
    private String notes;
    private boolean isObjective;
    private boolean isExpanded = false;

    public Assessment() { }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) {
        this.notes= notes;
    }

    public boolean isObjective() {
        return isObjective;
    }

    public void setObjective(boolean objective) {
        isObjective = objective;
    }

    public boolean getExpanded(){ return isExpanded; }

    public void setExpanded(boolean expanded) { isExpanded = expanded; }

}
