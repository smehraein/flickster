package com.example.soroushmehraein.flickster.clients;

import com.example.soroushmehraein.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Author: soroushmehraein
 * Project: Flickster
 * Date: 7/24/16.
 */
public class MovieDbClient {
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=%s";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=%s";

    /**
     * Fetches movies from The Movie DB and invokes a callback with a JSON Array of movies
     *
     * @param handler Must implement onSuccess method which takes a JSONArray
     */
    public static void getMoviesListAsync(final JsonHttpResponseHandler handler) {
        String url = String.format(NOW_PLAYING_URL, API_KEY);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONResults;
                try {
                    movieJSONResults = response.getJSONArray("results");
                    handler.onSuccess(statusCode, headers, movieJSONResults);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getAndSetVideoKey(final Movie movie) {
        String url = String.format(VIDEOS_URL, movie.getId(), API_KEY);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray videoJSONResults = response.getJSONArray("results");
                    if (videoJSONResults.length() > 0) {
                        movie.setVideoKey(videoJSONResults.getJSONObject(0).getString("key"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
