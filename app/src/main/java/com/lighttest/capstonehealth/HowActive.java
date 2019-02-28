package com.lighttest.capstonehealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HowActive extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<String> list;
    TextView activeText;
    TextView userInfoText;
    Spinner dropdown;
    String tmp;
    SharedPreferences mPrefs;
    SharedPreferences.Editor editor;
    Intent intent;
    float actFac;
    /**
    public void activeInfo(View view){


        if (dropdown.getSelectedItem().toString() == "Sedentary"){

            activeText.setText("You do not move much huh?");
            Toast.makeText(this, "SEDENTARY", Toast.LENGTH_SHORT).show();
        }

        if (dropdown.getSelectedItem().toString() == "Lightly Active"){

            activeText.setText("You move a bit");
            Toast.makeText(this, "LIGHTLY ACTIVE", Toast.LENGTH_SHORT).show();

        }

        if (dropdown.getSelectedItem().toString() == "Active"){

            activeText.setText("You wouldn't be using this app");
            Toast.makeText(this, "ACTIVE", Toast.LENGTH_SHORT).show();
        }
    }
    **/

    public void toNextActivity(View view){
        intent = new Intent(this, RecommendedCaloricIntake.class);
        if (dropdown.getSelectedItem().toString() == "Sedentary"){
            actFac = (float) 1.2;
        }
        else if (dropdown.getSelectedItem().toString() == "Lightly Active"){
            actFac = (float) 1.3;
        }
        else{
            actFac = (float) 1.4;
        }

        editor = mPrefs.edit();
        editor.putFloat("ACTIVITY_FACTOR",actFac);
        editor.apply();

        startActivity(intent);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        if (dropdown.getSelectedItem().toString() == "Sedentary"){

            tmp = "If you’re sedentary, your daily activities include:\n" +
                    "Activities of daily living only, such as shopping, cleaning, watering plants, taking out the trash, walking the dog, mowing the lawn and gardening.\n" +
                    "No moderate of vigorous activities.\n" +
                    "Unless you do at least 30 minutes per day of intentional exercise, you are considered sedentary.\n" +
                    "Spending most of the day sitting (e.g. bank teller, desk job)\n" +
                    "The majority of people will be considered sedentary.";
            activeText.setText(tmp);

           // Toast.makeText(this, "SEDENTARY", Toast.LENGTH_SHORT).show();
        }

        if (dropdown.getSelectedItem().toString() == "Lightly Active"){

            tmp ="If you’re lightly active, your daily activities include:\n" +
                    "Activities of daily living only, such as shopping, cleaning, watering plants, taking out the trash, walking the dog, mowing the lawn and gardening.\n" +
                    "Daily exercise that is equal to walking for 30 minutes at 4mph.  For an adult of average weight, this amount of exercise will burn about 130-160 additional calories.\n" +
                    "More intense exercise can be performed for less time to achieve the same goal.  For example, 15-20 minutes of vigorous activity, such as aerobics, skiing or jogging on a daily basis would put you in this category.\n" +
                    "Spending a good part of the day on your feet (e.g. teacher, salesman)";
            activeText.setText(tmp);
          //  Toast.makeText(this, "LIGHTLY ACTIVE", Toast.LENGTH_SHORT).show();

        }

        if (dropdown.getSelectedItem().toString() == "Active"){

            tmp ="If you’re active, your daily activities include:\n" +
                    "Activities of daily living only, such as shopping, cleaning, watering plants, taking out the trash, walking the dog, mowing the lawn and gardening.\n" +
                    "Daily exercise that is equal to walking for 1 hour and 45 minutes at 4mph.  For an adult of average weight, this amount of exercise will burn about 470-580 additional calories.\n" +
                    "More intense exercise can be performed for less time.  For example, jogging for 50 minutes per day.\n" +
                    "Spending a good part of the day doing some physical activity (e.g. waitress, mailman)";
            activeText.setText(tmp);
           // Toast.makeText(this, "ACTIVE", Toast.LENGTH_SHORT).show();
        }

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_active);

      activeText = findViewById(R.id.activeText);
      userInfoText = findViewById(R.id.userInfoDisplay);


        //get the spinner from the xml.
        dropdown = findViewById(R.id.activeSpinner);

        String[] choices = new String[]{"Sedentary", "Lightly Active", "Active"};
        list = new ArrayList<>(Arrays.asList(choices));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, choices);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);


        mPrefs = getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);
        String fName = mPrefs.getString("FIRST_NAME", "noData");
        String lName = mPrefs.getString("LAST_NAME", "noData");
        Integer weight = mPrefs.getInt("WEIGHT", 0);
        Integer height = mPrefs.getInt("HEIGHT", 0);
        Integer age = mPrefs.getInt("AGE", 0);
        String gender = mPrefs.getString("GENDER", "noData");

        String tmpText = ("Name: " + fName + " " + lName + "\nWeight: " + weight + " lbs\nHeight: " + height + " inches\nAge: " + age + " years old\nGender: " + gender);
        userInfoText.setText(tmpText);



        //Toast.makeText(this, "First Name: " + fName + "\nLast Name: " + lName + "\nWeight: " + weight + " lbs\nHeight: " + height + " inches\nAge: " + age + " years old\nGender: " + gender, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Name: "+ fName + " " + lName, Toast.LENGTH_SHORT).show();
    }
}
