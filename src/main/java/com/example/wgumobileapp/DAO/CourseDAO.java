package com.example.wgumobileapp.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.wgumobileapp.Models.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert
    public void insert(Course... courses);
    @Update
    public void update(Course... courses);
    @Delete
    public void delete(Course courses);
    @Query("SELECT * FROM courses")
    List<Course> getCourses();
    @Query("SELECT * FROM courses WHERE courseID= :id")
    Course getCourseByID(int id);
}
