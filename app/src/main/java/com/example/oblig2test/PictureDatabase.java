package com.example.oblig2test;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Picture.class}, version = 1, exportSchema = false)
public abstract class PictureDatabase extends RoomDatabase {
    private static volatile PictureDatabase INSTANCE;

    public abstract PictureDao pictureDao();

    public static PictureDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PictureDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PictureDatabase.class, "picture_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

