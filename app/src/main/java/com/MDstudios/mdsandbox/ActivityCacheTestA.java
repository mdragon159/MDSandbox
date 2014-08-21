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
 * Created by Jawad Nasser on 8/1/2014.
 */
public class ActivityCacheTestA extends Activity {

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

        // Can just store here if need be
            // File cacheDir = getCacheDir();

        // 1) Get new file
        // 2) Open output stream
        // 3) Do rest normally

        // TODO: Check if line below is unneccessary by using getCacheDir().getAb...
        File cacheDir = getCacheDir();
        File file = new File(cacheDir.getAbsolutePath(),"mytest.txt");
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
        Intent intent = new Intent(this, ActivityCacheTestB.class);
        startActivity(intent);
    }
}
