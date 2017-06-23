package com.jchavando.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jchavando.flicks.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    //view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    TextView tvReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap (getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        tvReleaseDate= (TextView) findViewById(R.id.tvReleaseDate);

        //set title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());

        //set rating bar
        float voteAverage = movie.getVoteAverage().floatValue();
        //if voteAverage is >0, then divide it by 2
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage/2.0f : voteAverage);



    }

//    public void onClick(View v) { //ImageView
//        //item position
//        int position =  ;
//                //getAdapterPosition();
//
//        //make sure the position is valid, actually exits in the view
//        if(position != null){
//            //get the movie at the position
////            Movie movie = movies.get(position);
//            //create intent for the new activity
//            Intent intent = new Intent(this, MovieTrailerActivity.class);
//            //serialize the movie using parceler, use its short name as a key
//            intent.putExtra(Movie.class.getSimpleName(), movie.getId());
//            //show the activity
//            this.startActivity(intent);
//        }
//    }



}
