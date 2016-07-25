package com.example.soroushmehraein.flickster;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.soroushmehraein.flickster.clients.YouTubeClient;
import com.example.soroushmehraein.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class TrailerActivity extends YouTubeBaseActivity {

    YouTubePlayerView ytTrailerPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        final String videoKey = getIntent().getStringExtra(Movie.INTENT_VIDEO_KEY);

        ytTrailerPlayer = (YouTubePlayerView) findViewById(R.id.ytTrailerPlayer);

        final Context savedContext = this;
        ytTrailerPlayer.initialize(YouTubeClient.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(savedContext, "Failed to initialize YouTube player", Toast.LENGTH_LONG).show();
            }
        });
    }
}
