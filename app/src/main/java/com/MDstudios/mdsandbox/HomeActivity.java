package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jawad Nasser on 7/23/2014.
 */
public class HomeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button buttonNA;
    Button buttonToTab;
    Button buttonToDrawer;
    Button buttonToPrefs;
    Button buttonToFile;
    Button buttonToQuotes;
    Button buttonToQuotesEn;
    Button buttonToITracker;
    Button buttonToGrid;
    Button buttonToFlexUI;
    Button buttonToCache;
    Button buttonToExtFile;
    Button buttonToSpinner;

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private drawerListAdapter myAdapter;
    private ActionBarDrawerToggle mDrawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        buttonNA = (Button) findViewById(R.id.buttonNA);
        buttonNA.setOnClickListener(this);
        buttonToTab = (Button) findViewById(R.id.buttonToTab);
        buttonToTab.setOnClickListener(this);
        buttonToDrawer = (Button) findViewById(R.id.buttonToDrawer);
        buttonToDrawer.setOnClickListener(this);
        buttonToPrefs = (Button) findViewById(R.id.buttonToPrefs);
        buttonToPrefs.setOnClickListener(this);
        buttonToFile = (Button) findViewById(R.id.buttonToFile);
        buttonToFile.setOnClickListener(this);
        buttonToQuotes = (Button) findViewById(R.id.buttonToQuotes);
        buttonToQuotes.setOnClickListener(this);
        buttonToQuotesEn = (Button) findViewById(R.id.buttonToQuotesEn);
        buttonToQuotesEn.setOnClickListener(this);
        buttonToITracker = (Button) findViewById(R.id.buttonToITracker);
        buttonToITracker.setOnClickListener(this);
        buttonToGrid = (Button) findViewById(R.id.buttonToGrid);
        buttonToGrid.setOnClickListener(this);
        buttonToFlexUI = (Button) findViewById(R.id.buttonToFlexUI);
        buttonToFlexUI.setOnClickListener(this);
        buttonToCache = (Button) findViewById(R.id.buttonToCache);
        buttonToCache.setOnClickListener(this);
        buttonToExtFile = (Button) findViewById(R.id.buttonToExtFile);
        buttonToExtFile.setOnClickListener(this);
        buttonToSpinner = (Button) findViewById(R.id.buttonToSpinner);
        buttonToSpinner.setOnClickListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mListView = (ListView) findViewById(R.id.drawerList);
        myAdapter = new drawerListAdapter(this);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);

        mDrawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView){
                Toast.makeText(HomeActivity.this, " Drawer Closed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView){
                Toast.makeText(HomeActivity.this, " Drawer Opened ", Toast.LENGTH_SHORT).show();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerListener);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Forward the action button click to the drawer listener
        if(mDrawerListener.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // If orientation or such changes, change the drawer listener as well
        mDrawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Call syncState() from your Activity's onPostCreate to synchronize
        // the indicator with the state of the linked DrawerLayout after
        // onRestoreInstanceState has occurred.
        mDrawerListener.syncState();
    }

    public void ToMain(View view){
        Log.d("MD", "Button1 was clicked");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonToTab)
        {
            Toast.makeText(this, "To the tab page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SlideTabActivity.class);
            startActivity(intent);
        }
        else if(v==buttonToDrawer) {
            Toast.makeText(this, "To the drawer test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DrawerActivity.class);
            startActivity(intent);
        }
        else if(v==buttonToPrefs) {
            Toast.makeText(this, "To the Shared Prefs test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivitySharedPrefTest.class);
            startActivity(intent);
        }
        else if(v == buttonToFile){
            Toast.makeText(this, "To the Internal File test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityInternalFileTestA.class);
            startActivity(intent);
        }
        else if(v == buttonToQuotes){
            Toast.makeText(this, "To the Random Quotes test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QuotesActivityMain.class);
            startActivity(intent);
        }
        else if(v == buttonToQuotesEn){
            Toast.makeText(this, "To the Enhanced Quotes test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QuotesEnActivityMain.class);
            startActivity(intent);
        }
        else if(v == buttonToITracker){
            Toast.makeText(this, "To the Information Tracker test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ITrackerActivityMain.class);
            startActivity(intent);
        }
        else if(v == buttonToGrid){
            Toast.makeText(this, "To the Gridview test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityGridViewTest.class);
            startActivity(intent);
        }
        else if(v == buttonToFlexUI){
            Toast.makeText(this, "To the Flexible UI test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityFlexibleUIFrags.class);
            startActivity(intent);
        }
        else if(v == buttonToCache){
            Toast.makeText(this, "To the Cache test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityCacheTestA.class);
            startActivity(intent);
        }
        else if(v == buttonToExtFile){
            Toast.makeText(this, "To the External File test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityExtFileTestA.class);
            startActivity(intent);
        }
        else if(v == buttonToSpinner){
            Toast.makeText(this, "To the File Spinner test page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityFileSpinnerTestA.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Why would you press me?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    public void selectItem(int position) {
        mListView.setItemChecked(position, true);
    }
    public void setTitle(String title){
        getActionBar().setTitle(title);
    }

    class drawerListAdapter extends BaseAdapter{
        private Context mContext;
        String[] socialSites;
        int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};
        public drawerListAdapter(Context context){
            mContext = context;
            socialSites = context.getResources().getStringArray(R.array.planets);
        }

        @Override
        public int getCount() {
            return socialSites.length;
        }

        @Override
        public Object getItem(int position) {
            return socialSites[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = null;
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.custom_row, parent, false);
            }
            else{
                row = convertView;
            }

            TextView titleTextView = (TextView)row.findViewById(R.id.textView1);
            ImageView titleImageView = (ImageView) row.findViewById(R.id.imageView1);

            titleTextView.setText(socialSites[position]);
            titleImageView.setImageResource(images[position]);

            return row;
        }
    }

}
