package com.example.oblig2test;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    private ActivityScenario<MainActivity> activityScenario;

    @Before
    public void setUp() {
        activityScenario = activityRule.getScenario();
        Intents.init();
    }



    @After
    public void tearDown() {
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void testAppFunctionality() {
        //
        // clicking a button in the main-menu (if you have one) takes you to the right sub-activity (i.e. to the Quiz or the Gallery; testing one button is enough);
        onView(withId(R.id.gallery)).perform(ViewActions.click());

        // is the score updated correctly in the quiz (the test submits at least one right/wrong answer each and you check if the score is correct afterwards);
        //
        Intent resultIntent = new Intent();
        resultIntent.putExtra("image_uri", "android.resource://com.example.oblig2test/drawable/cat"); // Example image URI
        resultIntent.putExtra("image_name", "cat"); // Example image name
        Intents.intending(IntentMatchers.hasComponent(gallery.class.getName()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultIntent));
        //a test that checks that the number of registered pictures/persons is correct after adding/deleting an entry. For adding, use Intent Stubbing to return some image data (e.g. from the resource-folder) without any user interaction.

    }
}

