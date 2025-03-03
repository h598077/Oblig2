package com.example.oblig2test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface PictureDao {
    @Insert
    void insert(Picture picture);

    @Delete
    void delete(Picture picture);

    @Query("SELECT * FROM pictures")
    LiveData<List<Picture>> getAllPictures();

    // Sorting images alphabetically by name
    @Query("SELECT * FROM pictures ORDER BY name ASC")
    LiveData<List<Picture>> getAllPicturesSortedAsc();

    @Query("SELECT * FROM pictures ORDER BY name DESC")
    LiveData<List<Picture>> getAllPicturesSortedDesc();
}

