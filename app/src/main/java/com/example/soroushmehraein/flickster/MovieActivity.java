package com.example.soroushmehraein.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.soroushmehraein.flickster.adapters.MovieArrayAdapter;
import com.example.soroushmehraein.flickster.clients.MovieDbClient;
import com.example.soroushmehraein.flickster.models.Movie;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    SwipeRefreshLayout swipeContainer;
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        configureSwipeRefreshContainer();

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        assert lvItems != null;
        lvItems.setAdapter(movieAdapter);

        setupViewListener();

        fetchMovieDataAsync();
    }


    private void setupViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movies.get(position);
                Intent intent = new Intent(MovieActivity.this, MovieDetailsActivity.class);
                intent.putExtra(Movie.INTENT_BACKDROP_IMAGE, selectedMovie.getBackdropPath(Movie.BACKDROP_IMAGE_SIZES.w780));
                intent.putExtra(Movie.INTENT_TITLE, selectedMovie.getOriginalTitle());
                intent.putExtra(Movie.INTENT_OVERVIEW, selectedMovie.getOverview());
                intent.putExtra(Movie.INTENT_RATING, selectedMovie.getVoteAverage());
                intent.putExtra(Movie.INTENT_VIDEO_KEY, selectedMovie.getVideoKey());
                startActivity(intent);
            }
        });
    }

    /**
     * Fetches movie data from MovieDClient, adds it to the 'movies' ArrayList and notifies the adapter.
     */
    private void fetchMovieDataAsync() {
        MovieDbClient.getMoviesListAsync(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    movies.clear();
                movies.addAll(Movie.fromJSONArray(response));
                    movieAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Sets up container for managing swipe-to-refresh
     */
    private void configureSwipeRefreshContainer() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovieDataAsync();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

}
