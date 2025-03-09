package com.example.oblig2test;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class gallery extends AppCompatActivity {

        private PictureViewModel pictureViewModel;
        private RecyclerView recyclerView;
        private ImageAdapter imageAdapter;

        private Context context;

    final String[] imageNames = {"cow", "whale", "pig"};
    final String[] imageNames2 = {"cat", "dog", "turkey"};

    private static final int[] predefinedImages = {
            R.drawable.cow,  // Your predefined image resource
            R.drawable.whale,  // Your predefined image resource
            R.drawable.pig   // Your predefined image resource
    };
    private static final int[] choseimage = {
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
                    for (Picture picture : pictures) {
                        Log.d("Gallery", "Picture Name: " + picture.name);
                    }
                    imageAdapter.setPictures(pictures);
                }
                if(pictures.size()==0){
                    openPredefinedImages();
                }
            });

            // Add Picture button functionality
            FloatingActionButton fabAddImage = findViewById(R.id.fabAddImage);
            fabAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageSelectorDialog();
                }
            });


            // Set up button to open the image selector dialog
            Button sortButton = findViewById(R.id.sorter);
            sortButton.setOnClickListener(v -> {
                pictureViewModel.toggleSortOrder(); // Toggle sorting order
                pictureViewModel.getAllPictures().observe(this, pictures -> {
                    if (pictures != null) {
                        imageAdapter.setPictures(pictures);
                    }
                });


            });



        }




    // Method to open the image selection dialog
    private void openImageSelectorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Image");

        // Set predefined image names in the dialog
        builder.setItems(imageNames2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected image's URI
                int selectedImageResId = choseimage[which];
                Uri selectedImageUri = Uri.parse("android.resource://" + getPackageName() + "/" + selectedImageResId);

                // Insert the selected image into the database
                String selectedImageName = imageNames2[which];
                Picture newPicture = new Picture(selectedImageName, selectedImageUri.toString());
                pictureViewModel.insert(newPicture);
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    // Method to use predefined images instead of opening the gallery picker
    private void openPredefinedImages() {
        for (int imageResId : predefinedImages) {
            Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + imageResId);

            // You can use a default image name or any logic to name the image
            String imageName = getResources().getResourceEntryName(imageResId);

            // Insert the new picture into the database
            Picture newPicture = new Picture(imageName, imageUri.toString());
            pictureViewModel.insert(newPicture);
        }
    }



    }

