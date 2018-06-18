package com.example.reham.baking_app.Fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reham.baking_app.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by reham on 6/8/2018.
 */

public class StepFragment extends Fragment {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    boolean mOnePane;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    public StepFragment() {
    }

    String Content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        TextView textView = rootView.findViewById(R.id.description_view);
        playerView =(PlayerView) rootView.findViewById(R.id.video_player);
        if (getArguments().getString("mTwoPane") != null&& getArguments().getString("mTwoPane").equals("onePane") ) {
            mOnePane=true;
        }else {mOnePane=false;}
        if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
        String description = getArguments().getString("description");
        textView.setText(description);}
        if (getArguments().getString("videoURL") != null && !getArguments().getString("videoURL").equals("")) {
            Content = getArguments().getString("videoURL");
            Uri uri = Uri.parse(Content);
            initializePlayer(uri);
        } else if (getArguments().getString("thumbnail") != null && !getArguments().getString("thumbnail").equals("")) {
            Content = getArguments().getString("thumbnail");
            initializePlayer(Uri.parse(Content));
        } else {
            playerView.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mOnePane) hideSystemUi();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
           releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
          releasePlayer();
        }
    }

    private void initializePlayer(Uri uri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
