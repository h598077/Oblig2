package com.example.oblig2test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureViewModel extends AndroidViewModel {
    private final PictureRepository repository;
    private final LiveData<List<Picture>> allPictures;

    public PictureViewModel(Application application) {
        super(application);
        repository = new PictureRepository(application);
        allPictures = repository.getAllPictures();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    public void insert(Picture picture) {
        repository.insert(picture);
    }
    public void delete(Picture picture) {
        repository.delete(picture);
    }
}

