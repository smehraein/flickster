package com.example.soroushmehraein.flickster;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soroushmehraein.flickster.clients.YouTubeClient;
import com.example.soroushmehraein.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    ImageView ivDetailBackdrop;
    RatingBar rbMovieRating;
    TextView tvDetailTitle;
    TextView tvDetailOverview;
    YouTubePlayerView ytDetailPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_activity);

        // Extract values form intent
        String imagePath = getIntent().getStringExtra(Movie.INTENT_BACKDROP_IMAGE);
        String title = getIntent().getStringExtra(Movie.INTENT_TITLE);
        String overview = getIntent().getStringExtra(Movie.INTENT_OVERVIEW);
        float rating = getIntent().getFloatExtra(Movie.INTENT_RATING, (float) 0.0);
        final String videoKey = getIntent().getStringExtra(Movie.INTENT_VIDEO_KEY);

        // Get views
        ivDetailBackdrop = (ImageView) findViewById(R.id.ivDetailBackdrop);
        tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        tvDetailOverview = (TextView) findViewById(R.id.tvDetailOverview);
        rbMovieRating = (RatingBar) findViewById(R.id.rbMovieRating);
        ytDetailPlayer = (YouTubePlayerView) findViewById(R.id.ytDetailPlayer);


        // Assign values
        Picasso.with(this).load(imagePath).placeholder(R.drawable.loading64).transform(new RoundedCornersTransformation(15, 0)).into(ivDetailBackdrop);
        tvDetailTitle.setText(title);
        tvDetailOverview.setText(overview);
        rbMovieRating.setRating(rating);

        // Initialize Video
        if (videoKey != null) {
            final Context savedContext = this;
            ytDetailPlayer.initialize(YouTubeClient.API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.cueVideo(videoKey);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(savedContext, "Failed to initialize YouTube player", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
