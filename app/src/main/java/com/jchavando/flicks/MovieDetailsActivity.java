package com.jchavando.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jchavando.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    //view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView tvReleaseDate;
    TextView tvAdultFilm;


    //constants
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //the parameter for the API key
    public final static String API_KEY_PARAM = "api_key";
    //tag for logging from this activity
    public final static String TAG = "MovieTrailerActivity";

    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    //instance fields
    AsyncHttpClient client;
    String videoKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap (getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        client = new AsyncHttpClient();

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        tvReleaseDate= (TextView) findViewById(R.id.tvReleaseDate);
        tvAdultFilm = (TextView) findViewById(R.id.tvAdultFilm);

        //set title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());
        if(movie.getAdultFilm()) {
            tvAdultFilm.setText("adult film");
        }

        //set rating bar
        float voteAverage = movie.getVoteAverage().floatValue();
        //if voteAverage is > 0, then divide it by 2
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage/2.0f : voteAverage);

        getTrailer();



    }



    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser){
        //always log the error
        Log.e(TAG, message, error);
        //alert the user to avoid silent errors
        if(alertUser){
            //show a long toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

    }

    private void getTrailer(){

        String url = API_BASE_URL + "/movie/" + movie.getId()+ "/videos"; //end of url

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM,getString(R.string.api_key));//API key, always required
        //execute a GET request expecting a JSON object response

        client.get(url, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movies list
                Log.i(TAG, "here");
                try {
                    //String videoKey;
                    JSONArray results = response.getJSONArray("results");

                    JSONObject results_object = results.getJSONObject(0);

                    if(results_object != null) {
                        videoKey = results_object.getString("key");
                        Log.i(TAG, YOUTUBE_BASE_URL + videoKey);
                    }

                    tvTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //create intent for the new activity
                            Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);

                            //serialize the movie using parceler, use its short name as a key
                            intent.putExtra("Vid_Key", videoKey);

                            //show the activity
                            startActivityForResult(intent, 20);
                        }

                    });
                } catch (JSONException e) {
                    logError("Failed to parse youtube movie", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from youtube endpoint", throwable, true);
            }
        });


    }


}
