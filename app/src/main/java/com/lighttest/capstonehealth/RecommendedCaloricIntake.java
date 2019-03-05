package com.lighttest.capstonehealth;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
    TextView loseWeightText;
    TextView gainWeightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_caloric_intake);
        mPref = getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);
        greetingText = findViewById(R.id.greetingText);
        tmpStr = "Hello, " + mPref.getString("FIRST_NAME", "noData") + "!";
        greetingText.setText(tmpStr);

        if (mPref.getString("GENDER", "noData") == "Male"){
            gender = 0;
        }
        else{
            gender = 1;
        }

        height = (float) (mPref.getInt("HEIGHT", 0) * inchToCMRatio);
        weight = (float) (mPref.getInt("WEIGHT", 0) * lbsToKGRatio);
        activityFactor = mPref.getFloat("ACTIVITY_FACTOR", (float) 1.3);
        age = mPref.getInt("AGE", 0);
        if (gender == 0){
            suggestedIntake = (float) ((10 * weight + 6.25 * height - 5 * age + 5) * activityFactor);
        }
        else if (gender == 1){
            suggestedIntake = (float)((10 * weight + 6.25 * height - 5 * age - 161) * activityFactor);
        }

        calorieText = findViewById(R.id.recCalories);
        tmpStr = "Your Recommended Daily Intake:\n" + Math.round(suggestedIntake) + " Calories";
        calorieText.setText(tmpStr);

        loseWeightText = findViewById(R.id.twentyPercentLess);
        tmpStr = "Your Recommended Daily Intake for Weight Loss:\n" + Math.round(suggestedIntake * 0.8) + " Calories";
        loseWeightText.setText(tmpStr);

        gainWeightText = findViewById(R.id.twentyPercentMore);
        tmpStr = "Your Recommended Daily Intake for Weight Gain:\n" + Math.round(suggestedIntake * 1.2) + " Calories";
        gainWeightText.setText(tmpStr);
    }
}
