package com.example.oblig2test;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureRepository {
    private final PictureDao pictureDao;
    private final LiveData<List<Picture>> allPictures;

    public PictureRepository(Application application) {
        PictureDatabase db = PictureDatabase.getDatabase(application);
        pictureDao = db.pictureDao();
        allPictures = pictureDao.getAllPictures();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    public void insert(Picture picture) {
        new Thread(() -> pictureDao.insert(picture)).start();
    }
    public void delete(Picture picture) {
        new Thread(() -> pictureDao.delete(picture)).start();
    }
}

