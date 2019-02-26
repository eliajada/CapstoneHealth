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
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    TextView calorieText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_caloric_intake);
        mPref = getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);

        if (mPref.getString("GENDER", "noData") == "Male"){
            gender = 0;
        }
        else{
            gender = 1;
        }

        height = (float) (mPref.getInt("HEIGHT_IN", 0) * 2.54);
        weight = (float) (mPref.getInt("WEIGHT_LBS", 0) * 0.453592);
        activityFactor = mPref.getFloat("ACTIVITY_FACTOR", (float) 1.3);
        age = mPref.getInt("AGE", 0);
        if (gender == 0){
            suggestedIntake = (float) ((10 * weight + 6.25 * height - 5 * age + 5) * activityFactor);
        }
        else if (gender == 1){
            suggestedIntake = (float)((10 * weight + 6.25 * height - 5 * age - 161) * activityFactor);
        }

        calorieText = findViewById(R.id.recCalories);
        String tmp = "Recommended Calories:\n" + suggestedIntake;
        calorieText.setText(tmp);
    }
}
