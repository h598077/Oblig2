package com.example.oblig2test;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {
    private PictureViewModel pictureViewModel;
    private Button button1, button2, button3;
    private ImageView imageView;
    private int score = 0;
    private List<Picture> allPictures;
    private Picture currentPicture;
    private TextView scoreView;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        imageView = view.findViewById(R.id.quizImageView);
        scoreView = view.findViewById(R.id.scoreView);

        pictureViewModel = new ViewModelProvider(getActivity()).get(PictureViewModel.class);
        // Load saved score from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        int savedScore = prefs.getInt("quiz_score", 0);
        score = savedScore;
        scoreView.setText("Score: " + savedScore); // Set the score on the TextView
        LiveData<List<Picture>> allPicsLD = pictureViewModel.getAllPictures();
        allPicsLD.observe(getViewLifecycleOwner(), pictures -> {
            allPictures = pictures;
            if (!allPictures.isEmpty()) {
                currentPicture = getRandomPicture(allPictures);
                startQuiz(currentPicture);
            }
        });



        return view;
    }

    private Picture getRandomPicture(List<Picture> pictures) {
        Random random = new Random();
        int randomIndex = random.nextInt(pictures.size());
        return pictures.get(randomIndex);
    }

    private void startQuiz(Picture picture) {
        currentPicture = picture;
        Uri imageUri = Uri.parse(picture.imageUri);
        imageView.setImageURI(imageUri);
        imageView.setContentDescription(picture.name);

        // Name lists
        List<String> names = new ArrayList<>();
        names.add(picture.name);
        List<String> incorrectNames = getIncorrectNames(picture, allPictures);
        names.addAll(incorrectNames);

        Collections.shuffle(names);

        button1.setText(names.get(0));
        button2.setText(names.get(1));
        button3.setText(names.get(2));

        button1.setOnClickListener(v -> checkAnswer(button1.getText().toString(), picture.name));
        button2.setOnClickListener(v -> checkAnswer(button2.getText().toString(), picture.name));
        button3.setOnClickListener(v -> checkAnswer(button3.getText().toString(), picture.name));
    }

    private void checkAnswer(String selectedName, String correctName) {
        if (selectedName.equals(correctName)) {
            Toast.makeText(context, getString(R.string.riktig_svar), Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(context, getString(R.string.feil_svar), Toast.LENGTH_SHORT).show();
        }
        updateScore();
        currentPicture = getRandomPicture(allPictures);
        startQuiz(currentPicture);
    }

    private List<String> getIncorrectNames(Picture picture, List<Picture> allPictures) {
        List<String> incorrectNames = new ArrayList<>();
        while (incorrectNames.size() < 2) {
            Picture randomPicture = getRandomPicture(allPictures);
            if (!randomPicture.name.equals(picture.name) && !incorrectNames.contains(randomPicture.name)) {
                incorrectNames.add(randomPicture.name);
            }
        }
        return incorrectNames;
    }

    private void updateScore() {
        SharedPreferences prefs = getActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("quiz_score", score);
        editor.apply();

        scoreView.setText("Score: " + score);
    }
}