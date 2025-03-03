package com.example.oblig2test;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PictureViewModel extends AndroidViewModel {
    private final PictureRepository repository;
    private  LiveData<List<Picture>> allPictures;
    private boolean isAscending = true;



    public PictureViewModel(Application application) {
        super(application);
        repository = new PictureRepository(application);
        allPictures = repository.getAllPicturesSortedAsc();
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
    public void toggleSortOrder() {
        // Toggle sorting order and update the live data
        isAscending = !isAscending;
        if (isAscending) {
            allPictures = repository.getAllPicturesSortedAsc();
        } else {
            allPictures = repository.getAllPicturesSortedDesc();
        }

    }
}

