package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jawad Nasser on 8/1/2014.
 */
public class ActivitySharedPrefTest extends Activity {

    // Used to get user input
    EditText mUserName;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        // Find and save the editTexts for future use
        mUserName = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
    }


    // Saves the user input
    public void save(View view){
        // 1) Get SharedPreferences object [choose file name and privacy]
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        // 2) Get editor to edit the file
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 3) Put in data in terms of key:value structure
        editor.putString("name",mUserName.getText().toString());
        editor.putString("password",mPassword.getText().toString());
        // 4) Commit changes, else changes won't occur
        editor.commit();

        Toast.makeText(this,"Data was saved successfully",Toast.LENGTH_LONG).show();
    }

    // Launches the next test page
    public void next(View view){
        Toast.makeText(this,"Next",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivitySharedPrefTestB.class);
        startActivity(intent);
    }
}
