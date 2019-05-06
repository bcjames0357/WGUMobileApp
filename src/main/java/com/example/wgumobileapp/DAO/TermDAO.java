package com.example.wgumobileapp.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.wgumobileapp.Models.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert
    public void insert(Term... terms);
    @Update
    public void update(Term... terms);
    @Delete
    public void delete(Term terms);
    @Query("SELECT * FROM terms")
    List<Term> getTerms();
    @Query("SELECT * FROM terms WHERE termID= :id")
    Term getTermByID(int id);
}
