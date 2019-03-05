package com.lighttest.capstonehealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //SharedPreferences sharedPreferences;

    SharedPreferences mPrefs;

    SharedPreferences.Editor editor;
    EditText txtFirstName;
    EditText txtLastName;
    EditText txtWeight;
    EditText txtHeight;
    EditText txtAge;
    String txtGender;

    Spinner dropdown;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (dropdown.getSelectedItem() != null) {
            if (dropdown.getSelectedItem().toString() == "Male") {
                txtGender = "Male";
            } else {
                txtGender = "Female";
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        txtGender = "Male";
    }

    public void toSecondActivity(View view) {

        Intent intent = new Intent(getApplicationContext(), HowActive.class);


        //sharedPreferences.edit().putString("firstName", String.valueOf(txtFirstName)).apply();
        //Expanded out the above to make it more readable in code
        mPrefs = this.getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);
        //initialized the editor using the above sharedPreferences variable
        editor = mPrefs.edit();
        //add the user information into SharedPreferences for use throughout the app
        editor.putString("FIRST_NAME", txtFirstName.getText().toString());
        editor.putString("LAST_NAME", txtLastName.getText().toString());
        editor.putInt("WEIGHT", Integer.valueOf(txtWeight.getText().toString()));
        editor.putInt("HEIGHT", Integer.valueOf(txtHeight.getText().toString()));
        editor.putInt("AGE", Integer.valueOf(txtAge.getText().toString()));
        editor.putString("GENDER", txtGender);

        //Apply the changes made to SharedPreferences
        editor.apply();

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = this.getSharedPreferences("com.lighttest.sharedpreferences", MODE_PRIVATE);
        if (mPrefs.getString("FIRST_NAME", null) != null){
            if (mPrefs.getString("LAST_NAME", null) != null){
                if (mPrefs.getInt("WEIGHT", 0) != 0){
                    if (mPrefs.getInt("HEIGHT", 0) != 0){
                        if (mPrefs.getInt("AGE", 0) != 0){
                            if (mPrefs.getString("GENDER", null) != null){
                                if (mPrefs.getFloat("ACTIVITY_FACTOR", -1) == -1){
                                    Intent intent1 = new Intent(getApplicationContext(), HowActive.class);
                                    startActivity(intent1);
                                }
                                else {
                                    Intent intent2 = new Intent(getApplicationContext(), RecommendedCaloricIntake.class);
                                    startActivity(intent2);
                                }
                            }
                        }
                    }
                }
            }
        }

        txtFirstName = findViewById(R.id.textFirstName);
        txtLastName = findViewById(R.id.textLastName);
        txtWeight = findViewById(R.id.textWeight);
        txtHeight = findViewById(R.id.textHeight);
        txtAge = findViewById(R.id.textAge);

        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

    }
}