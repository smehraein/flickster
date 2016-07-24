package com.example.soroushmehraein.flickster.models;

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

    private static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w342/%s";
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
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
     * Returns full url for image by appending IMAGE_URL_PREFIX
     * @return Full url for poster image
     */
    public String getPosterPath() {
        return String.format(IMAGE_URL_PREFIX, posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    /**
     * Returns full url for image by appending IMAGE_URL_PREFIX
     * @return Full url for backdrop image
     */
    public String getBackdropPath() {
        return String.format(IMAGE_URL_PREFIX, backdropPath);
    }
}
