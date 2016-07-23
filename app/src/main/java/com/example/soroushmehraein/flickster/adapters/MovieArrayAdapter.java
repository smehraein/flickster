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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data item for this position
        Movie movie = getItem(position);

        // Check if the existing view is being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        // Find image view
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);

        // Clear out last image
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        // Populate text data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        // Select image based on orientation
        String imagePath;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath();
        } else {
            imagePath = movie.getBackdropPath();
        }

        // Populate image
        Picasso.with(getContext()).load(imagePath).placeholder(R.drawable.loading64).into(ivImage);

        // Return the view
        return convertView;

    }
}
