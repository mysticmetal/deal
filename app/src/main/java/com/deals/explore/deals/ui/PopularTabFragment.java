package com.deals.explore.deals.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.deals.explore.deals.R;
import com.deals.explore.deals.api.DealApi;
import com.deals.explore.deals.bean.Deal;
import com.deals.explore.deals.util.DealsAdapter;
import com.deals.explore.deals.util.DividerItemDecoration;
import com.deals.explore.deals.util.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasneem on 17/5/15.
 */
public class PopularTabFragment  extends Fragment implements DealApi.DealListener{
    private static final String TAG = "Popular Tab";
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SwipeRefreshLayout dealLayout;
    private Toolbar mToolbar;
    private SlidingTabLayout mTab;
    private ViewPager mPager;


    public PopularTabFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_popular,container,false);
         /* Initializing recycler view to show deals */
        mContext = v.getContext();
        mToolbar = (Toolbar)getActivity().findViewById(R.id.tool_bar);
        mTab = (SlidingTabLayout)getActivity().findViewById(R.id.tabs);
        mPager = (ViewPager)getActivity().findViewById(R.id.pager);
        dealLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        dealLayout.setColorSchemeColors(R.color.ColorPrimary);
        final DealApi api = new DealApi(mContext, this);
        /* Pull to refresh */
        dealLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dealLayout.setRefreshing(true);
                api.fetchPopularDealsList();
            }
        });
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //setting up our OnScrollListener
        mRecyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                mToolbar.animate().translationY(-mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                mTab.animate().translationY(-mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                mPager.animate().translationY(-mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                float viewPagerHeight = (float) (mPager.getHeight() + mToolbar.getBottom());
                layoutParams.height = (int) viewPagerHeight;
                mPager.setLayoutParams(layoutParams);

            }

            @Override
            public void onShow() {
                mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                mTab.animate().translationY(0).setInterpolator(new AccelerateInterpolator()).start();
                mPager.animate().translationY(0).setInterpolator(new AccelerateInterpolator()).start();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                float viewPagerHeight = (float) (mPager.getHeight() - mToolbar.getBottom());
                layoutParams.height = (int) viewPagerHeight;
                mPager.setLayoutParams(layoutParams);
            }
        });
        api.fetchPopularDealsList();
        return v;
    }

    @Override
    public void onComplete(JSONArray jsonArray) {
        List<Deal> dealList = new ArrayList<Deal>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                dealList.add(new Deal(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e(TAG, "error :" + e.getMessage());
            }
        }
        mRecyclerView.setAdapter(new DealsAdapter(mContext, dealList));
        dealLayout.setRefreshing(false);
    }

    @Override
    public void onError(VolleyError error) {
        Log.e(TAG, "error: " + error.getMessage());
    }
}
