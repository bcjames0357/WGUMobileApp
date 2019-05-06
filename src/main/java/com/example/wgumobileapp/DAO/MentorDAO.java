package com.example.wgumobileapp.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.wgumobileapp.Models.Mentor;

import java.util.List;

@Dao
public interface MentorDAO {
    @Insert
    public void insert(Mentor... mentors);
    @Update
    public void update(Mentor... mentors);
    @Delete
    public void delete(Mentor mentors);
    @Query("SELECT * FROM mentors")
    List<Mentor> getMentors();
    @Query("SELECT * FROM mentors WHERE mentorID= :id")
    Mentor getMentorByID(int id);
}
