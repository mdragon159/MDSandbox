package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jawad Nasser on 8/6/2014.
 */
public class ActivityInternalFileTestB extends Activity {
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

        Toast.makeText(this, "Load successful", Toast.LENGTH_LONG).show();
    }

    // Returns to the previous ActivitySharedPrefTest activity
    public void previous(View view){
        Toast.makeText(this,"Previous",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ActivityInternalFileTestA.class);
        startActivity(intent);
    }
}
