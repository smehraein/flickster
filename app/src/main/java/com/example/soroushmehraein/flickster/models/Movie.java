package com.example.soroushmehraein.flickster.models;

import com.example.soroushmehraein.flickster.clients.MovieDbClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Author: soroushmehraein
 * Project: Flickster
 * Date: 7/21/16.
 */
public class Movie {

    public static final String INTENT_POSITION = "intent_movie_position";
    public static final String INTENT_VIDEO_KEY = "intent_movie_video_key";
    private static final String POSTER_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w342/%s";
    private static final float POPULAR_RATING_THRESHOLD = (float) 5.0;
    public static ArrayList<Movie> fetchedMovies = new ArrayList<>();
    private int id;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private Float voteAverage;
    private String videoKey;

    private Movie(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.voteAverage = (float) jsonObject.getDouble("vote_average");
        MovieDbClient.getAndSetVideoKey(this);
    }

    /**
     * Converts a JSON array of movie information from TheMoviesDb to a list of Movie objects
     *
     * @param array JSON array of movie data
     * @return ArrayList of Movie objects
     */
    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * Indicates if a movie is popular (specified by POPULAR_RATING_THRESHOLD)
     *
     * @return Boolean indicating if a movie is popular
     */
    public boolean isPopular() {
        return voteAverage > POPULAR_RATING_THRESHOLD;
    }

    /**
     * Returns full url for image by appending POSTER_IMAGE_URL_PREFIX
     *
     * @return Full url for poster image
     */
    public String getPosterPath() {
        return String.format(POSTER_IMAGE_URL_PREFIX, posterPath);
    }

    /**
     * Returns full url for image by appending POSTER_IMAGE_URL_PREFIX
     *
     * @return Full url for backdrop image
     */
    public String getBackdropPath(BACKDROP_IMAGE_SIZES sizeEnum) {
        String baseUrl = String.format("https://image.tmdb.org/t/p/%s/", sizeEnum.name());
        return baseUrl.concat(backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public enum BACKDROP_IMAGE_SIZES {
        w300, w780, w1280
    }

    public enum POSTER_IMAGE_SIZES {
        w185, w342, w500, w780
    }
}
