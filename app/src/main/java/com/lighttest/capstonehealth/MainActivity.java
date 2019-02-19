package com.lighttest.capstonehealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    SharedPreferences mPrefs;

    SharedPreferences.Editor editor;

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtWeight;
    EditText txtHeight;
    EditText txtAge;


    public void toSecondActivity(View view) {

        Intent intent = new Intent(getApplicationContext(), HowActive.class);

        startActivity(intent);

        String fname = String.valueOf(txtFirstName);

        /**
        editor.putString("FirstName", String.valueOf(txtFirstName));
        editor.putString("LastName", String.valueOf(txtLastName));
        editor.putString("Weight", String.valueOf(txtWeight));
        editor.putString("Height", String.valueOf(txtHeight));
        editor.putString("Age", String.valueOf(txtAge));
        editor.commit();

        Toast.makeText(this, "LOL" + fname, Toast.LENGTH_SHORT).show();
         **/

        sharedPreferences.edit().putString("firstName", String.valueOf(txtFirstName)).apply();
    }

    public void logInClicked(View view) {

        EditText txtFirstName = (EditText) findViewById(R.id.textFirstName);
        EditText txtLastName = (EditText) findViewById(R.id.textLastName);
        EditText txtWeight = (EditText) findViewById(R.id.textWeight);
        EditText txtHeight = (EditText) findViewById(R.id.textHeight);
        EditText txtAge = (EditText) findViewById(R.id.textAge);


        // Log.i("Username", txtUser.getText().toString());
        // Log.i("Password", txtPass.getText().toString());

        Toast.makeText(this, "First Name: " + txtFirstName.getText().toString() + "Last Name: " + txtLastName.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFirstName = (EditText) findViewById(R.id.textFirstName);
        txtLastName = (EditText) findViewById(R.id.textLastName);
        txtWeight = (EditText) findViewById(R.id.textWeight);
        txtHeight = (EditText) findViewById(R.id.textHeight);
        txtAge = (EditText) findViewById(R.id.textAge);



        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        sharedPreferences = this.getSharedPreferences("com.lighttest.sharedpreferences", Context.MODE_PRIVATE);


        /**
         mPrefs = getSharedPreferences("MainMenuValue", Context.MODE_PRIVATE);
        //Give any name for //preference as I have given "IDvalue" and value 0.
        editor = mPrefs.edit();

        // editor.putString(key, value);
        // give key value as "sound" you mentioned and value what you // to want give as "1" in you mentioned
        // editor.commit();

        **/
    }
}