package com.example.oblig2test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pictures")
public class Picture {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String imageUri;

    public Picture(String name, String imageUri) {
        this.name = name;
        this.imageUri = imageUri;
    }
}

