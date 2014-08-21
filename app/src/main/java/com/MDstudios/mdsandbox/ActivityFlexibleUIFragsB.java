package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jawad Nasser on 8/13/2014.
 */
public class ActivityFlexibleUIFragsB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexui_b);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);

        FragmentFlexibleUIB fragmentB = (FragmentFlexibleUIB) getFragmentManager().findFragmentById(R.id.fragment2);
        if(fragmentB != null) fragmentB.changeData(index);

    }
}
