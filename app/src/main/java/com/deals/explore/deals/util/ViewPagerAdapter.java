package com.deals.explore.deals.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.deals.explore.deals.ui.PopularTabFragment;
import com.deals.explore.deals.ui.TopTabFragment;

/**
 * Created by tasneem on 17/5/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "Pager Adapter";
    private CharSequence mTitles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int mNumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence titles[], int numbOfTab) {
        super(fm);
        mTitles = titles;
        mNumbOfTabs = numbOfTab;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            Log.d(TAG, "position 0 called");
            // if the position is 0 we are returning the First tab
            return new TopTabFragment();
        } else {
            Log.d(TAG, "position 1 called");
            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            return new PopularTabFragment();
        }
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "Title :"+mTitles[position]);
        return mTitles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return mNumbOfTabs;
    }
}
