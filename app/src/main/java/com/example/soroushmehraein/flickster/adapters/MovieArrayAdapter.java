package com.example.soroushmehraein.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soroushmehraein.flickster.R;
import com.example.soroushmehraein.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Author: soroushmehraein
 * Project: Flickster
 * Date: 7/23/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isPopular()) {
            return MOVIE_TYPES.POPULAR_MOVIE.ordinal();
        } else {
            return MOVIE_TYPES.REGULAR_MOVIE.ordinal();
        }
    }

    @Override
    public int getViewTypeCount() {
        return MOVIE_TYPES.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MOVIE_TYPES.POPULAR_MOVIE.ordinal()) {
            return getPopularMovieView(position, convertView, parent);
        } else {
            return getRegularMovieView(position, convertView, parent);
        }
    }

    private View getRegularMovieView(int position, View convertView, ViewGroup parent) {
        // Get data item for this position
        Movie movie = getItem(position);

        RegularHolder regularHolder;

        // Check if the existing view is being reused
        if (convertView == null) {
            regularHolder = new RegularHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            regularHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            regularHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            regularHolder.image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            convertView.setTag(regularHolder);
        } else {
            regularHolder = (RegularHolder) convertView.getTag();
        }

        // Populate text data
        regularHolder.title.setText(movie.getOriginalTitle());
        regularHolder.overview.setText(movie.getOverview());

        // Select image based on orientation
        String imagePath;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath(Movie.POSTER_IMAGE_SIZES.w342);
        } else {
            imagePath = movie.getBackdropPath(Movie.BACKDROP_IMAGE_SIZES.w780);
        }

        // Populate image
        Picasso.with(getContext()).load(imagePath).placeholder(R.drawable.loading64).transform(new RoundedCornersTransformation(15, 0)).into(regularHolder.image);

        // Return the view
        return convertView;
    }

    private View getPopularMovieView(int position, View convertView, ViewGroup parent) {
        // Get data item for this position
        Movie movie = getItem(position);

        PopularHolder popularHolder;

        // Check if the existing view is being reused
        if (convertView == null) {
            popularHolder = new PopularHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_popular_movie, parent, false);
            popularHolder.image = (ImageView) convertView.findViewById(R.id.ivPopularMovieImage);
            convertView.setTag(popularHolder);
        } else {
            popularHolder = (PopularHolder) convertView.getTag();
        }

        // Populate image
        String imagePath = movie.getBackdropPath(Movie.BACKDROP_IMAGE_SIZES.w780);
        Picasso.with(getContext()).load(imagePath).placeholder(R.drawable.loading64).transform(new RoundedCornersTransformation(15, 0)).into(popularHolder.image);

        // Return the view
        return convertView;
    }

    private enum MOVIE_TYPES {
        POPULAR_MOVIE, REGULAR_MOVIE
    }

    private static class RegularHolder {
        TextView title;
        TextView overview;
        ImageView image;
    }

    private static class PopularHolder {
        ImageView image;
    }
}
