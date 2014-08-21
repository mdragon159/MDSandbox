package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jawad Nasser on 8/6/2014.
 *
 * Used to test internal file reading and writing
 */
public class ActivityInternalFileTestA extends Activity {
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
        String text1 = mUserName.getText().toString();
        String text2 = mPassword.getText().toString();

        // Add space to text1 to separate the two texts
        text1 = text1+" ";

        File file = null; // Simply used later to get file dir

        // 1) Create fos file
        // 2) Open file output stream
        // 3) Write to file
        // 4) Close output stream in finally part [and add own try/catch]
        // --Use Android Studio to add try/catch statements
        FileOutputStream fos = null;
        try {
            file = getFilesDir();

            fos = openFileOutput("vivz.txt", Context.MODE_PRIVATE);
            fos.write(text1.getBytes());
            fos.write(text2.getBytes());
        } catch (FileNotFoundException e) {
            // Catches if can't find file for whatever reason
            e.printStackTrace();
        } catch (IOException e) {
            // Catches if writing doesn't go perfectly
            e.printStackTrace();
        }finally {
            try {
                if(fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Toast.makeText(this, "Saved successfully "+file, Toast.LENGTH_LONG).show();
    }

    // Launches the next test page
    public void next(View view){
        Toast.makeText(this,"Next",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityInternalFileTestB.class);
        startActivity(intent);
    }
}
