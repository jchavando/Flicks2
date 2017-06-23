package com.jchavando.flicks;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.jchavando.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

//import static com.jchavando.flicks.MovieListActivity.API_BASE_URL;
//import static com.jchavando.flicks.MovieListActivity.API_KEY_PARAM;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    //constants
    // the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";


    //the parameter for the API key
    public final static String API_KEY_PARAM = "youtube_api_key";
    //tag for logging from this activity
    public final static String TAG = "MovieTrailerActivity";

    //instance fields
    AsyncHttpClient client;

    Movie movie;
    Integer movieId;


//    //the adapter wired to the recycler view
//    MovieAdapter adapter;
//    //image config
//    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // temporary test video id -- TODO replace with movie trailer video id
        final String videoId = "SUXWAEX2jlg";

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }


    private void getTrailer(){

        // can pass things in intent to access other activities

        String url = API_BASE_URL + "/movie/" + movie.getId() + "/videos"; //end of url

        //TODO

        //set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM,getString(R.string.youtube_api_key));//API key, always required
        //execute a GET request expecting a JSON object response
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movies list
                try {
                    JSONArray results = response.getJSONArray("results");

                    //iterate through result set and create Movie objects
                    for (int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        //movies.add(movie);
                        //notify adapter that a row was added
                        //adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    //logError("Failed to parse now playing movie", e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //logError("Failed to get data from now_playing endpoint", throwable, true);
            }
        });
    }


}
