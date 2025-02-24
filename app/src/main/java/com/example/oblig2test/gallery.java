package com.example.oblig2test;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class gallery extends AppCompatActivity {

        private PictureViewModel pictureViewModel;
        private RecyclerView recyclerView;
        private ImageAdapter imageAdapter;

    private static final int[] predefinedImages = {
            R.drawable.cat,  // Your predefined image resource
            R.drawable.dog,  // Your predefined image resource
            R.drawable.turkey   // Your predefined image resource
    };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gallery);


            recyclerView = findViewById(R.id.recyclerView);
            imageAdapter = new ImageAdapter(this, (picture, position) -> {
                // Handle the delete action here
                pictureViewModel.delete(picture);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(imageAdapter);

            pictureViewModel = new ViewModelProvider(this).get(PictureViewModel.class);

            // Observe LiveData for updates to the picture list
            pictureViewModel.getAllPictures().observe(this, pictures -> {
                if (pictures != null) {
                    imageAdapter.setPictures(pictures);
                }
            });

            // Add Picture button functionality
            FloatingActionButton fabAddImage = findViewById(R.id.fabAddImage);
            fabAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPredefinedImages();
                }
            });


        }





    // Method to use predefined images instead of opening the gallery picker
    private void openPredefinedImages() {
        for (int imageResId : predefinedImages) {
            Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + imageResId);

            // You can use a default image name or any logic to name the image
            String imageName = "Picture ";

            // Insert the new picture into the database
            Picture newPicture = new Picture(imageName, imageUri.toString());
            pictureViewModel.insert(newPicture);
        }
    }

    }
