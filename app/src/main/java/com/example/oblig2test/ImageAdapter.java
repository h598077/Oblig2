package com.example.oblig2test;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PictureViewHolder> {
    private final List<Picture> pictures = new ArrayList<>();
    private final OnDeleteClickListener deleteClickListener;
    private final Context context; // You need the context to show the dialog

    // Constructor to pass the delete listener and context
    public ImageAdapter(Context context, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new PictureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);
        holder.bind(picture, position);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures.clear();
        this.pictures.addAll(pictures);
        notifyDataSetChanged();
    }



    public interface OnDeleteClickListener {
        void onDeleteClick(Picture picture, int position);
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameTextView;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }

        public void bind(Picture picture, int position) {
            nameTextView.setText(picture.name);
            // Load the image
            loadImageFromUri(picture.imageUri);

            // Set an OnClickListener for the imageView to show the dialog
            imageView.setOnClickListener(v -> {
                // Show the AlertDialog when the image is clicked
                new AlertDialog.Builder(context)
                        .setTitle("Delete Image")
                        .setMessage("Are you sure you want to delete this image?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            // Call the delete listener when the user confirms deletion
                            deleteClickListener.onDeleteClick(picture, position);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }

        private void loadImageFromUri(String imageUri) {
            try {
                Uri uri = Uri.parse(imageUri);
                InputStream inputStream = itemView.getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}