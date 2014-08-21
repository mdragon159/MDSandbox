package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jawad Nasser on 8/16/2014.
 */
public class ActivityFileSpinnerTestA extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String[] selections; // Holds the text-version of each spinner option
    int selectedPosition = 0; // Determines what the save button actually does, value received from spinner

    // Used to get user input
    EditText mUserName;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multistorage);

        // Save EditTexts to get user input later
        mUserName = (EditText) findViewById(R.id.editUsername);
        mPassword = (EditText) findViewById(R.id.editPassword);

        // Save the spinner selections for assigning to spinner as well as comparing to user choice
        selections = getResources().getStringArray(R.array.fileSpinner);

            // Find and setup the spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item,selections);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set the item click listener to this activity
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //    Toast.makeText(this,"Position: " + position + ", Selection: " + selections[position],
    //            Toast.LENGTH_LONG).show();
        selectedPosition = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void save(View view){

        // TODO: Figure out better way to determine mode

        /* Each position to function:
        0: Shared Preferences
        1: Cache
        2: Internal File
        3: External File
         */

        // First get the entered username and password
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        switch(selectedPosition){
            case 0:
                saveSharedPrefs(username, password);
                break;
            case 1:
                saveCache(username, password);
                break;
            case 2:
                saveInternalFile(username, password);
                break;
            case 3:
                saveExternalFile(username, password);
                break;
            default:
                Toast.makeText(this, "Welp, something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void next(View view){
        Toast.makeText(this,"Next",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityFileSpinnerTestB.class);
        startActivity(intent);
    }

    public void saveSharedPrefs(String username, String password){
        // 1) Get SharedPreferences object [choose file name and privacy]
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        // 2) Get editor to edit the file
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 3) Put in data in terms of key:value structure
        editor.putString("name",username);
        editor.putString("password",password);
        // 4) Commit changes, else changes won't occur
        editor.commit();

        Toast.makeText(this,"Data was saved successfully",Toast.LENGTH_LONG).show();
    }

    public void saveCache(String username, String password){
        // 1) Get new file
        // 2) Open output stream
        // 3) Do rest normally

        // TODO: Check if line below is unneccessary by using getCacheDir().getAb...
        File cacheDir = getCacheDir();
        File file = new File(cacheDir.getAbsolutePath(),"mytest.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(username.getBytes());
            fos.write(password.getBytes());
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

    public void saveInternalFile(String username, String password){
        // The input into the file, with space for separation
        String input = username + " " + password;

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
            fos.write(input.getBytes());
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


        Toast.makeText(this, "Saved successfully to "+file, Toast.LENGTH_LONG).show();
    }

    public void saveExternalFile(String username, String password){
        // First check if external storage is available
        String state = Environment.getExternalStorageState();
        if(!(Environment.MEDIA_MOUNTED.equals(state))){
            Log.d("MD", "SD card was reported unusable with state " + state);
            Toast.makeText(this,"SD card was reported unusable with state "+state,
                    Toast.LENGTH_LONG).show();

            return;
        }

        String input = username + " " + password;

        // 1) Get new file
        // 2) Open output stream
        // 3) Do rest normally

        File file = new File(getExternalFilesDir(null),"mytest.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(input.getBytes());
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
}
