package com.jchavando.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by jchavando on 6/21/17.
 */

@Parcel //annotation indicates class is Parcelable
public class Movie {

    //values from API
    String title;
    String overview;
    String posterPath; //only the path
    String backdropPath;
    Double voteAverage;
    Integer id;
    String releaseDate;
    Boolean adultFilm;

    public Movie() {}

    //initialize from JSON data
    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path"); //key from API
        voteAverage = object.getDouble("vote_average");
        //parse its value from the JSONOBject passed to the constructor
        id = object.getInt("id");

        adultFilm = object.getBoolean("adult");

        releaseDate = object.getString("release_date");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Boolean getAdultFilm() {
        return adultFilm;
    }
}
