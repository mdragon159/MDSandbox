package com.MDstudios.mdsandbox;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created by Jawad Nasser on 7/29/2014.
 * Used for testing easily reusable drawers
 */
public class DrawerActivity extends Activity {
    private SimpleNavDrawerFragment mNavDrawerFrag; // The fragment that handles the drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        // Save the drawerlayout temporarily to pass on later
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Test add fragment
        mNavDrawerFrag= new SimpleNavDrawerFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.navigation_drawer,mNavDrawerFrag);
        fragmentTransaction.commit();

        mNavDrawerFrag.setUpDrawer(this,drawerLayout, mNavDrawerFrag.SCREEN_HOME_MODE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
                // Note: NECESSARY FOR DRAWER TO FUNCTION [on actionbar at least]
            // TODO: Find out best implementation
        /*
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        */

        // Forward the action button click to the drawer listener
        if(mNavDrawerFrag.getDrawerToggle().onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Call syncState() from your Activity's onPostCreate to synchronize
        // the indicator with the state of the linked DrawerLayout after
        // onRestoreInstanceState has occurred.
        mNavDrawerFrag.getDrawerToggle().syncState();
            // Note: Indicator will NOT show without this
    }

}
