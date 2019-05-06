package com.example.wgumobileapp.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import com.example.wgumobileapp.Models.Assessment;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert
    void insert(Assessment... assessments);
    @Update
    void update(Assessment... assessments);
    @Delete
    void delete(Assessment assessments);
    @Query("SELECT * FROM assessments")
    List<Assessment> getAssessments();
    @Query("SELECT * FROM assessments WHERE assessmentID= :id")
    Assessment getAssessmentByID(int id);
}
