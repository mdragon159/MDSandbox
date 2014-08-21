package com.MDstudios.mdsandbox;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jawad Nasser on 8/1/2014.
 *
 * TODO: Optimize "getActivity" methods and see if ways to avoid usage
 * TODO: Change text of actionbar when drawer toggles
 * TODO: Open drawer if first time viewing
 * TODO: Figure out why getActivity()/getActionBar() are not working
 * TODO: Highlight selected listView item
 */
public class SimpleNavDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    /* Public constants to choose the drawer mode */
    public static final int SCREEN_TEST_DOS_MODE = -1;
    public static final int SCREEN_HOME_MODE = 1;

    public int mScreenMode = 0;

    // Helper component that ties the action bar to the navigation drawer.
    private ActionBarDrawerToggle mDrawerToggle; // Obtained from activity
    private DrawerLayout mDrawerLayout; // Obtained from activity

    private drawerListAdapter mListAdapter;
    private ListView mListView; // Save the standard list view from the layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer,container,false);

        // Grab the simple listView
        mListView = (ListView) v.findViewById(R.id.drawerList);
        // Set up the basic listView
        mListAdapter = new drawerListAdapter(getActivity());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // If indicated change in screen mode, now change it
        if(mScreenMode != 0){
            mListAdapter.setContent(mScreenMode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: Set up interactions with listView

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // If orientation or such changes, change the drawer listener as well
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
    * Users of this fragment must call this method to set up the navigation drawer interactions.
    *
    * @param activity The activity attached to the fragment [getActivity() is not working]
    * @param drawerLayout The DrawerLayout containing this fragment's UI.
    * @param screenMode sdsds
    * sdsds
    */
    public void setUpDrawer(Activity activity, DrawerLayout drawerLayout, int screenMode){
        // Save the DrawerLayout
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        // TODO: Find out how this works specifically
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Enable action bar button functionality
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // Set up drawerListener/ActionBarDrawerToggle object
        // Ties together actionbar and drawer
        mDrawerToggle = new ActionBarDrawerToggle(
                activity,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
            //    Toast.makeText(getActivity(), " Drawer Closed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(getActivity(), " Drawer Opened ", Toast.LENGTH_SHORT).show();
            }
        };

        // Assign the drawerToggle
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Indicate change drawer content to change later
        mScreenMode = SCREEN_HOME_MODE;
    //    mListAdapter.setContent(SCREEN_HOME_MODE);
    }

    public ActionBarDrawerToggle getDrawerToggle(){return mDrawerToggle;}

    // Simple adapter for non-simple listView/appearance on drawer
    class drawerListAdapter extends BaseAdapter {
        private Context mContext; // Used to get resources

        // Data holders
        String[] mListTitles;
        int[] mListImages;

        // Following are base data
        String[] socialSites;
        String[] planetsArray;
        int[] baseImages = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher};

        public drawerListAdapter(Context context){
            mContext = context;

            // Populate with base data for now
            planetsArray = mContext.getResources().getStringArray(R.array.planets);
            mListTitles = planetsArray;

            mListImages = baseImages;
        }

        // Updates content of the listview
        public void setContent(int drawerMode){
            // If this is the home screen mode, set content appropriately
            if(drawerMode == SimpleNavDrawerFragment.SCREEN_HOME_MODE){
                socialSites = mContext.getResources().getStringArray(R.array.socialSites);
                mListTitles = socialSites;

                mListImages = baseImages;
            }
            else if(drawerMode == SimpleNavDrawerFragment.SCREEN_TEST_DOS_MODE){
                planetsArray = mContext.getResources().getStringArray(R.array.planets);
                mListTitles = planetsArray;

                mListImages = baseImages;
            }
            else { // Otherwise, populate with basic data
                socialSites = mContext.getResources().getStringArray(R.array.socialSites);
                mListTitles = socialSites;

                mListImages = baseImages;
            }

            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
    //       Log.d("MD","Length of the mListTitles: " + mListTitles.length);
            return mListTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return mListTitles[position];
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

            titleTextView.setText(mListTitles[position]);
            titleImageView.setImageResource(mListImages[position]);

            return row;
        }
    } // end basic listView adapter "drawerListAdapter"
}
