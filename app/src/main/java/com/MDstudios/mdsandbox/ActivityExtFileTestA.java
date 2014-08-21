package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jawad Nasser on 8/1/2014.
 */
public class ActivityExtFileTestA extends Activity {

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
        // First check if external storage is available
        String state = Environment.getExternalStorageState();
        if(!(Environment.MEDIA_MOUNTED.equals(state))){
            Log.d("MD", "SD card was reported unusable with state "+state);
            Toast.makeText(this,"SD card was reported unusable with state "+state,
                    Toast.LENGTH_LONG).show();

            return;
        }

        String text1 = mUserName.getText().toString();
        String text2 = mPassword.getText().toString();

        // Add space to text1 to separate the two texts
        text1 = text1+" ";

        // 1) Get new file
        // 2) Open output stream
        // 3) Do rest normally

        File file = new File(getExternalFilesDir(null),"mytest.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(text1.getBytes());
            fos.write(text2.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(this,"Data was saved successfully",Toast.LENGTH_LONG).show();
    }

    // Launches the next test page
    public void next(View view){
        Toast.makeText(this,"Next",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityExtFileTestB.class);
        startActivity(intent);
    }
}
