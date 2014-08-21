package com.MDstudios.mdsandbox;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Jawad Nasser on 7/23/2014.
 */
public class SlideTabActivity extends FragmentActivity implements ActionBar.TabListener{

    ActionBar mActionBar;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_tab);

        // Get the view pager
        mViewPager = (ViewPager)findViewById(R.id.pager);
        MyAdapter pagerAdapter = new MyAdapter(getSupportFragmentManager());
        pagerAdapter.setFragments(this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Set up action bar to use tabs
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // Tell it that tabs are approaching

        // Add the tabs
        ActionBar.Tab tab1 = mActionBar.newTab();
        tab1.setText("Tab 1");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = mActionBar.newTab();
        tab2.setText("Tab 2");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = mActionBar.newTab();
        tab3.setText("Tab 3");
        tab3.setTabListener(this);

        mActionBar.addTab(tab1);
        mActionBar.addTab(tab2);
        mActionBar.addTab(tab3);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}

class MyAdapter extends FragmentPagerAdapter{

    // Three simple fragments
    BaseFragment fragA;
    BaseFragment fragB;
    BaseFragment fragC;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(Context c){

        // Set up the simple base fragments
        fragA = new BaseFragment();
        fragB = new BaseFragment();
        fragC = new BaseFragment();

        Resources res = c.getResources();

        fragA.changeText("This is Fragment A!");
        fragB.changeText("This is Fragment B!");
        fragC.changeText("This is Fragment C!");

        fragA.changeBG(res.getColor(R.color.dev_blue));
        fragB.changeBG(res.getColor(R.color.dev_green));
        fragC.changeBG(res.getColor(R.color.dev_orange));

    }

    @Override
    public Fragment getItem(int position) {
        // TODO: Make this more efficient, use a list or such, also comment more
        Fragment frag = null;
        if(position == 0){
            frag = fragA;
        }
        else if(position == 1){
            frag = fragB;
        }
        else if(position == 2){
            frag = fragC;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
