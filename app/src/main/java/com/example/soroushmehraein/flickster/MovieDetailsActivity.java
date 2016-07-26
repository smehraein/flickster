package com.example.soroushmehraein.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.soroushmehraein.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    ImageView ivDetailBackdrop;
    RatingBar rbMovieRating;
    TextView tvDetailTitle;
    TextView tvDetailOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_activity);

        // Extract movie and values from intent
        Movie movie = (Movie) getIntent().getSerializableExtra(Movie.INTENT_MOVIE);
        String imagePath = movie.getBackdropPath(Movie.BACKDROP_IMAGE_SIZES.w780);
        String title = movie.getOriginalTitle();
        String overview = movie.getOverview();
        float rating = movie.getVoteAverage();
        final String videoKey = movie.getVideoKey();

        // Get views
        ivDetailBackdrop = (ImageView) findViewById(R.id.ivDetailBackdrop);
        tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        tvDetailOverview = (TextView) findViewById(R.id.tvDetailOverview);
        rbMovieRating = (RatingBar) findViewById(R.id.rbMovieRating);

        // Assign values
        Picasso.with(this).load(imagePath).placeholder(R.drawable.loading64).transform(new RoundedCornersTransformation(15, 0)).into(ivDetailBackdrop);
        tvDetailTitle.setText(title);
        tvDetailOverview.setText(overview);
        rbMovieRating.setRating(rating);

        // Set onClick listener
        ivDetailBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, TrailerActivity.class);
                intent.putExtra(Movie.INTENT_VIDEO_KEY, videoKey);
                startActivity(intent);
            }
        });
    }
}
