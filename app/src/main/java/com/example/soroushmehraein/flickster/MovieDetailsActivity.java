package com.example.soroushmehraein.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soroushmehraein.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView ivDetailBackdrop;
    TextView tvDetailTitle;
    TextView tvDetailOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_activity);

        // Extract values form intent
        String imagePath = getIntent().getStringExtra(Movie.INTENT_BACKDROP_IMAGE);
        String title = getIntent().getStringExtra(Movie.INTENT_TITLE);
        String overview = getIntent().getStringExtra(Movie.INTENT_OVERVIEW);

        // Get views
        ivDetailBackdrop = (ImageView) findViewById(R.id.ivDetailBackdrop);
        tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        tvDetailOverview = (TextView) findViewById(R.id.tvDetailOverview);

        // Assign values
        Picasso.with(this).load(imagePath).placeholder(R.drawable.loading64).transform(new RoundedCornersTransformation(15, 0)).into(ivDetailBackdrop);
        tvDetailTitle.setText(title);
        tvDetailOverview.setText(overview);

    }
}
