package com.MDstudios.mdsandbox;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
 * Created by Jawad Nasser on 7/29/2014.
 *
 * TODO: Optimize "getActivity" methods and see if ways to avoid usage
 * TODO: Change text of actionbar when drawer toggles
 * TODO: Open drawer if first time viewing
 * TODO: Figure out how to get syncState() or alternative working
 */
public class CustomNavDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    // Helper component that ties the action bar to the navigation drawer.
    private ActionBarDrawerToggle mDrawerToggle; // Obtained from activity
    private DrawerLayout mDrawerLayout; // Obtained from activity
    // Save fragment's container view in order to check drawer's state and force open if need be
    private View mFragmentContainerView;

    private ListView mListView; // Save the standard list view from the layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Catch the view first to get child views easily
        View view = inflater.inflate(R.layout.frag_navigation_drawer,container,false);

        // Grab the list view to set it up later
        mListView = (ListView) view.findViewById(R.id.drawerList);
        // Set up the basic list view
        drawerListAdapter myAdapter = new drawerListAdapter(getActivity());
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);

        // Finally return the view as necessary by the method
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: Set up interactions with listview

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
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     *                     Used to easily get fragment container view
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {

        // Save fragment container view and drawerlayout
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
            // TODO: Find out how this works specifically
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Enable action bar button functionality
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // Set up drawerlistener/ActionBarDrawerToggle object
            // Ties together actionbar and drawer
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(getActivity(), " Drawer Closed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(getActivity(), " Drawer Opened ", Toast.LENGTH_SHORT).show();
            }
        };

        // Set up content, if need be
    }

    // Simple adapter for non-simple listView/appearance on drawer
    class drawerListAdapter extends BaseAdapter {
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
    } // end basic listView adapter "drawerListAdapter"
}
