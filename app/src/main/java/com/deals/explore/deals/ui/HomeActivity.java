package com.deals.explore.deals.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.deals.explore.deals.R;
import com.deals.explore.deals.bean.Deal;
import com.deals.explore.deals.util.DealsAdapter;
import com.deals.explore.deals.util.DividerItemDecoration;
import com.deals.explore.deals.util.SlidingTabLayout;
import com.deals.explore.deals.util.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Home Activty";
    /* Declaring variables & references */
    private Toolbar mToolBar;
    /* Pager related */
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private CharSequence mTitles[]={"Top","Popular"};
    private int mNumbOfTabs = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /* Initializing reference variables */
        Log.d(TAG, "Initialized");
        /* Assigning the toolbar object ot the view and setting the the Action bar to our toolbar */
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitleTextColor(getResources().getColor(R.color.abc_primary_text_material_dark));
        mToolBar.setTitle("Deals");
        setSupportActionBar(mToolBar);

        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mPager.setAdapter( new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumbOfTabs));
        // Assigning the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
    }

}
