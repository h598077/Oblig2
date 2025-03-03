package com.example.oblig2test;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureRepository {
    private final PictureDao pictureDao;
    private final LiveData<List<Picture>> allPicturesAsc;
    private final LiveData<List<Picture>> allPicturesDesc;

    public PictureRepository(Application application) {
        PictureDatabase db = PictureDatabase.getDatabase(application);
        pictureDao = db.pictureDao();
        allPicturesAsc = pictureDao.getAllPicturesSortedAsc();
        allPicturesDesc = pictureDao.getAllPicturesSortedDesc();
    }



    public void insert(Picture picture) {
        new Thread(() -> pictureDao.insert(picture)).start();
    }
    public void delete(Picture picture) {
        new Thread(() -> pictureDao.delete(picture)).start();
    }

    public LiveData<List<Picture>> getAllPicturesSortedAsc() {
        return allPicturesAsc;
    }

    public LiveData<List<Picture>> getAllPicturesSortedDesc() {
        return allPicturesDesc;
    }
}

