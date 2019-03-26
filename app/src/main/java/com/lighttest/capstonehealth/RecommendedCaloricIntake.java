package com.lighttest.capstonehealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class RecommendedCaloricIntake extends AppCompatActivity {
    int gender;
    float height;
    float weight;
    float suggestedIntake;
    float activityFactor;
    int age;
    double inchToCMRatio = 2.54;
    double lbsToKGRatio = 0.453592;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    String tmpStr;
    TextView greetingText;
    TextView calorieText;
    TextView todayCalorieText;
    TextView progressMessage;
    int caloriesToday;
    //Get the current day below
    int day = ((int) System.currentTimeMillis() / 86400000);
    static final int NEW_FOOD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_caloric_intake);
        mPref = getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);
        editor = mPref.edit();
        greetingText = findViewById(R.id.greetingText);
        todayCalorieText = findViewById(R.id.currentCalories);
        progressMessage = findViewById(R.id.progressMessage);
        tmpStr = "Hello, " + mPref.getString("FIRST_NAME", "noData") + "!";
        greetingText.setText(tmpStr);
        if (day != mPref.getInt("DAY_OF_PREVIOUS_MEAL", 0)){
            caloriesToday = 0;
        }
        else{
            caloriesToday = mPref.getInt("DAILY_CALORIES", 0);
        }
        tmpStr = "Calories consumed today:\n" + mPref.getInt("DAILY_CALORIES", 0);
        todayCalorieText.setText(tmpStr);

        if (mPref.getString("GENDER", "noData") == "Male"){
            gender = 0;
        }
        else{
            gender = 1;
        }
        if (!mPref.getBoolean("METRIC", true)) {
            height = (float) (mPref.getInt("HEIGHT", 0) * inchToCMRatio);
            weight = (float) (mPref.getInt("WEIGHT", 0) * lbsToKGRatio);
        }
        else {
            height = (float) mPref.getInt("HEIGHT", 0);
            weight = (float) mPref.getInt("WEIGHT", 0);
        }
        activityFactor = mPref.getFloat("ACTIVITY_FACTOR", (float) 1.3);
        age = mPref.getInt("AGE", 0);
        if (gender == 0){
            suggestedIntake = (float) ((10 * weight + 6.25 * height - 5 * age + 5) * activityFactor);
        }
        else if (gender == 1){
            suggestedIntake = (float)((10 * weight + 6.25 * height - 5 * age - 161) * activityFactor);
        }

        int goal = mPref.getInt("GOAL", 1);
        calorieText = findViewById(R.id.recCalories);
        if (goal == 1) {
            tmpStr = "Your Recommended Daily Intake:\n" + Math.round(suggestedIntake) + " Calories";
            calorieText.setText(tmpStr);
        }
        else if (goal == 2) {
            tmpStr = "Your Recommended Daily Intake for Weight Loss:\n" + Math.round(suggestedIntake * 0.8) + " Calories";
            calorieText.setText(tmpStr);
        }
        else if (goal == 3){
            tmpStr = "Your Recommended Daily Intake for Weight Gain:\n" + Math.round(suggestedIntake * 1.2) + " Calories";
            calorieText.setText(tmpStr);
        }
    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    public void addFood(View view) {
        Intent newFood = new Intent(this, NewFood.class);
        startActivityForResult(newFood, NEW_FOOD_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == NEW_FOOD_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Gson gson = new Gson();
                String json = mPref.getString("NEW_FOOD", "noData");
                FoodItem foodItem1 = gson.fromJson(json, FoodItem.class);
                caloriesToday += (foodItem1.getCaloriesPerServing() * foodItem1.getNumServings());
                tmpStr = "Calories consumed today:\n" + caloriesToday;
                todayCalorieText.setText(tmpStr);
                editor.putInt("DAY_OF_PREVIOUS_MEAL", day);
                editor.putInt("DAILY_CALORIES", caloriesToday);
                editor.apply();
                if (caloriesToday <= suggestedIntake - 100 && caloriesToday >= suggestedIntake - 500){
                    tmpStr = "You're almost there!";
                    progressMessage.setText(tmpStr);
                    progressMessage.setVisibility(View.VISIBLE);
                }
                else if (caloriesToday > suggestedIntake - 100 && caloriesToday < suggestedIntake + 100){
                    tmpStr = "You've hit your goal!";
                    progressMessage.setText(tmpStr);
                    progressMessage.setVisibility(View.VISIBLE);
                }
                else if (caloriesToday > suggestedIntake + 100){
                    tmpStr = "Whoa! You've eaten too much today!";
                    progressMessage.setText(tmpStr);
                    progressMessage.setVisibility(View.VISIBLE);
                }
                else {
                    progressMessage.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
