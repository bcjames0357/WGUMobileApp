package com.example.wgumobileapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.wgumobileapp.DAO.*;
import com.example.wgumobileapp.Models.*;

@Database(entities = {Assessment.class, Course.class, Mentor.class, Term.class}, version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AssessmentDAO getAssessmentDAO();
    public abstract CourseDAO getCourseDAO();
    public abstract MentorDAO getMentorDAO();
    public abstract TermDAO getTermDAO();
}