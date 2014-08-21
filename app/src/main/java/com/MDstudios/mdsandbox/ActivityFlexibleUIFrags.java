package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jawad Nasser on 8/13/2014.
 */
public class ActivityFlexibleUIFrags extends Activity implements FragmentFlexibleUIA.Communicator {
    FragmentFlexibleUIA fragmentA;
    FragmentFlexibleUIB fragmentB;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexui);

        manager = getFragmentManager();
        fragmentA = (FragmentFlexibleUIA) manager.findFragmentById(R.id.fragment);
        fragmentA.setCommunicator(this);
    }

    @Override
    public void respond(int index) {
        fragmentB = (FragmentFlexibleUIB) manager.findFragmentById(R.id.fragment2);

        if(fragmentB != null && fragmentB.isVisible()){
            fragmentB.changeData(index);
        }
        else{
            Log.d("MD","Tried to start intent with index " + index);

            Intent intent = new Intent(this, ActivityFlexibleUIFragsB.class);
            intent.putExtra("index",index);
            startActivity(intent);
        }
    }
}
