package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jawad Nasser on 8/16/2014.
 */
public class ActivityFileSpinnerTestB extends Activity implements AdapterView.OnItemSelectedListener {
    // Default string to be displayed just in case
    public static final String DEFAULT = "N/A";

    Spinner spinner;
    String[] selections; // Holds the text-version of each spinner option
    // Used to display saved data
    TextView userNameTextView, passwordTextView;

    int selectedPosition = 0; // Determines what the save button actually does, value received from spinner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multistorage_b);

        // Save references in order to show data later
        userNameTextView = (TextView) findViewById(R.id.displayUsername);
        passwordTextView = (TextView) findViewById(R.id.displayPassword);

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
        selectedPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void load(View view){

        // TODO: Figure out better way to determine mode

        /* Each position to function:
        0: Shared Preferences
        1: Cache
        2: Internal File
        3: External File
         */

        switch(selectedPosition){
            case 0:
                loadSharedPrefs();
                break;
            case 1:
                loadCache();
                break;
            case 2:
                loadInternalFile();
                break;
            case 3:
                loadExternalFile();
                break;
            default:
                Toast.makeText(this, "Welp, something went wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void previous(View view){
        Toast.makeText(this,"Previous",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityFileSpinnerTestA.class);
        startActivity(intent);
    }

    public void loadSharedPrefs(){
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

    public void loadCache(){

        // TODO: Figure out why crashes if this runs first thing

        // 1) Open same file input stream
        // 2) Read in from stream, bit by bit
        FileInputStream fis = null;
        try {
            File cacheDir = getCacheDir();
            File file = new File(cacheDir,"mytest.txt");
            fis = new FileInputStream(file);

            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ( (read = fis.read())!= -1 ){ // while file not empty ie returns -1, continue
                // Typecast to char and add to string buffer
                buffer.append((char)read);
            }

            // Split string between username and pass
            String text1 = buffer.substring(0, buffer.indexOf(" "));
            String text2 = buffer.substring(buffer.indexOf(" ")+1);

            userNameTextView.setText(text1);
            passwordTextView.setText(text2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_LONG).show();
    }

    public void loadInternalFile(){
        // 1) Open same file input stream
        // 2) Read in from stream, bit by bit
        FileInputStream fis = null;
        try {
            fis = openFileInput("vivz.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ( (read = fis.read())!= -1 ){ // while file not empty ie returns -1, continue
                // Typecast to char and add to string buffer
                buffer.append((char)read);
            }

            // Split string between username and pass
            String text1 = buffer.substring(0, buffer.indexOf(" "));
            String text2 = buffer.substring(buffer.indexOf(" ")+1);

            userNameTextView.setText(text1);
            passwordTextView.setText(text2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_LONG).show();
    }

    public void loadExternalFile(){
        // 1) Open same file input stream
        // 2) Read in from stream, bit by bit
        FileInputStream fis = null;
        try {
            File file = new File(getExternalFilesDir(null),"mytest.txt");
            fis = new FileInputStream(file);

            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ( (read = fis.read())!= -1 ){ // while file not empty ie returns -1, continue
                // Typecast to char and add to string buffer
                buffer.append((char)read);
            }

            // Split string between username and pass
            String text1 = buffer.substring(0, buffer.indexOf(" "));
            String text2 = buffer.substring(buffer.indexOf(" ")+1);

            userNameTextView.setText(text1);
            passwordTextView.setText(text2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_LONG).show();
    }
}
