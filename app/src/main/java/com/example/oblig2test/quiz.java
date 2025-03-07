package com.example.oblig2test;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class quiz extends AppCompatActivity {
    private PictureViewModel pictureViewModel;
    private Button button1;
    private Button button2;
    private Button button3;
    private ImageView imageView;
    private int score = 0;
    private Context context;
    private List<Picture> allPictures;
    private Picture currentPicture;
    private TextView scoreView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = getApplicationContext();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageView = findViewById(R.id.quizImageView);
        scoreView = findViewById(R.id.scoreView);
        pictureViewModel = new ViewModelProvider(this).get(PictureViewModel.class);

        LiveData<List<Picture>> allPicsLD = pictureViewModel.getAllPictures();
        allPicsLD.observe(this, pictures -> {
            allPictures = pictures;
            if (!allPictures.isEmpty()) {
                currentPicture = getRandomPicture(allPictures);
                startQuiz(currentPicture);
            }
        });
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        int savedScore = prefs.getInt("quiz_score", 0); // Default is 0 if no score is found
        score=savedScore;
        scoreView.setText("Score: " + savedScore);
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


        button1.setOnClickListener(v ->
                checkAnswer(button1.getText().toString(), picture.name));
        button2.setOnClickListener(v ->
                checkAnswer(button2.getText().toString(), picture.name));
        button3.setOnClickListener(v ->
                checkAnswer(button3.getText().toString(), picture.name));
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
        if (allPictures != null) {
            while (incorrectNames.size() < 2) {
                Picture randomPicture = getRandomPicture(allPictures);
                if (!randomPicture.name.equals(picture.name) && !incorrectNames.contains(randomPicture.name)) {
                    incorrectNames.add(randomPicture.name);
                }
            }
        }
        return incorrectNames;
    }

    private void updateScore(){
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("quiz_score", score);
        editor.apply();

        scoreView.setText("Score: " + score);
    }

}















