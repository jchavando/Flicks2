package com.jchavando.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jchavando on 6/21/17.
 */

public class Config {

    //the base url for loading images
    String imageBaseUrl;
    //the poster size to use when fetching images, part fo the url
    String posterSize;

    public Config(JSONObject object) throws JSONException {
        //get this first because image base and poster size are subcategories of images
        JSONObject images = object.getJSONObject("images");
        //get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        //get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //use the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
    }

    //helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size, path);//concatenate all three
    }
    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}