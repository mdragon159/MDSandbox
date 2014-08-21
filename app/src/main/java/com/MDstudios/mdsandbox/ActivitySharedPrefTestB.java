package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jawad Nasser on 8/5/2014.
 */
public class ActivitySharedPrefTestB extends Activity {

    // Default string to be displayed just in case
    public static final String DEFAULT = "N/A";

    // Used to display saved data
    TextView userNameTextView, passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs_b);

        // Save references in order to show data later
        userNameTextView = (TextView) findViewById(R.id.username);
        passwordTextView = (TextView) findViewById(R.id.password);
    }

    // Once button is clicked, loads in saved data to textViews
    public void load(View view){
        // 1) Get SharedPref object, from same exact file as before
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        // 2) Request and store the data from sharedPreferences
        String name = sharedPreferences.getString("name",DEFAULT);
        String password = sharedPreferences.getString("password",DEFAULT);

        // Double check if value is default, if not continue with loading
        if(name.equals(DEFAULT) || password.equals(DEFAULT)){
            Toast.makeText(this,"No Data was Found",Toast.LENGTH_LONG).show();
        }
        else{
            // Otherwise, continue with loading the data as data exists
            Toast.makeText(this,"Data Loaded Successfully",Toast.LENGTH_LONG).show();

            userNameTextView.setText(name);
            passwordTextView.setText(password);
        }
    }

    // Returns to the previous ActivitySharedPrefTest activity
    public void previous(View view){
        Toast.makeText(this,"Previous",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivitySharedPrefTest.class);
        startActivity(intent);
    }
}
