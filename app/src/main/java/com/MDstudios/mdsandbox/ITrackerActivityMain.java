package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jawad Nasser on 8/11/2014.
 */
public class ITrackerActivityMain extends Activity {
    /** Called when the activity is first created. */
    ITrackerDBAdapter db = new ITrackerDBAdapter(this);
    EditText rewards;
    EditText rewardsMonthly;
    EditText rewardsTD;
    DatePicker dateRewards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.itracker_activity_main);
            rewardsTD = (EditText)findViewById(R.id.RewardsTD);
            rewardsMonthly = (EditText)findViewById(R.id.RewardsMonthly);
            db.open();
            rewardsTD.setText(String.valueOf(db.getAllRewards()));
            rewardsMonthly.setText(String.valueOf(db.getMonthlyRewards()));
            db.close();
            // Capture our button from layout
            Button button = (Button)findViewById(R.id.Add);
            // Register the onClick listener with the implementation above
            button.setOnClickListener(mAddListener);

        }
        catch (Exception ex)
        {
            Context context = getApplicationContext();
            CharSequence text = ex.toString();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //System.out.println(ex.getMessage());
        }
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mAddListener = new View.OnClickListener() {
        public void onClick(View v) {
            long id = 0;
            // do something when the button is clicked
            db.open();
            try{
                //setContentView(R.layout.main);
                rewards = (EditText)findViewById(R.id.NumRewards);
                dateRewards = (DatePicker)findViewById(R.id.Date);
                rewardsMonthly = (EditText)findViewById(R.id.RewardsMonthly);
                rewardsTD = (EditText)findViewById(R.id.RewardsTD);
                id = db.insertRewards(String.valueOf(dateRewards.getYear()),
                        String.valueOf(dateRewards.getMonth()),
                        String.valueOf(dateRewards.getDayOfMonth()),
                        rewards.getText().toString());
                rewardsTD.setText(String.valueOf(db.getAllRewards()));
                rewardsMonthly.setText(String.valueOf(db.getMonthlyRewards()));
            }
            catch (Exception ex)
            {
                Context context = getApplicationContext();
                CharSequence text = ex.toString() + "ID = " + id;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //System.out.println(ex.getMessage());
            }

            db.close();

        }
    };
}
