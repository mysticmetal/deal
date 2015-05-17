package com.deals.explore.deals.api;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.deals.explore.deals.bean.Deal;
import com.deals.explore.deals.util.VolleyAppController;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tasneem on 17/5/15.
 */
public class DealApi {

    private static final String TAG = "Deal Api";
    private static final String accessToken = "7d7c5cacb355d043f07c7c9e4b38257ea5683f8d643b578683877a9b6a8bee1b";
    private static final String contentType = "application/json";

    public interface DealListener {
        void onComplete(JSONArray jsonArray);
        void onError(VolleyError error);
    }

    private Context mContext;
    private DealListener mListener;

    public DealApi(Context context, DealListener listener) {
        mContext = context;
        mListener = listener;
    }

    /**
     * Fetches top deals
     */
    public void fetchTopDealsList() {
        RequestQueue requestQueue = VolleyAppController.getInstance(mContext).getRequestQueue();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, "http://api.desidime.com/v1/deals/top.json", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mListener.onComplete(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        requestQueue.add(arrayRequest);
    }

    /**
     * Fetches popular deals
     */
    public void fetchPopularDealsList() {
        RequestQueue requestQueue = VolleyAppController.getInstance(mContext).getRequestQueue();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, "http://api.desidime.com/v1/deals/popular.json", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mListener.onComplete(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaders();
            }
        };
        requestQueue.add(arrayRequest);
    }

    /**
     * Sets header of any request
     * @return
     */
    private Map<String, String> setHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", contentType);
        headers.put("X-Desidime-Client", accessToken);
        return headers;
    }
}
