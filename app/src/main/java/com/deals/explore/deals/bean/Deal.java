package com.deals.explore.deals.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tasneem on 17/5/15.
 */
public class Deal {
    private String title;
    private String description;
    private String url;

    /**
     * Parameterized constructor extract values from JsonObject
     * @param jsonObject
     */
    public Deal(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("title")) {
            title = jsonObject.getString("title");
        }
        if (jsonObject.has("deal_detail")) {
            description = jsonObject.getString("deal_detail");
        }
        if (jsonObject.has("image_large")) {
            url = jsonObject.getString("image_large");
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
