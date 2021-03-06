package com.example.reham.baking_app.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reham.baking_app.R;
import com.example.reham.baking_app.Strings;
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

public class StepFragment extends Fragment implements Strings {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    boolean mOnePane;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    String description;
    public StepFragment() {
    }

    String Content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        TextView textView = rootView.findViewById(R.id.description_view);
        playerView = (PlayerView) rootView.findViewById(R.id.video_player);
     if (savedInstanceState==null){
        if (getArguments().getString(mTwoPaneText) != null && getArguments().getString(mTwoPaneText).equals("onePane")) {
            mOnePane = true;
        } else {
            mOnePane = false;
        }
        if (getArguments().getString(descriptionText) != null && !getArguments().getString(descriptionText).equals("")) {
            description = getArguments().getString(descriptionText);
            textView.setText(description);
        }
        if (getArguments().getString(VideoUrlText) != null && !getArguments().getString(VideoUrlText).equals(NO_VIDEO)) {
            Content = getArguments().getString(VideoUrlText);
            Uri uri = Uri.parse(Content);
            initializePlayer();
        } else if (getArguments().getString(thumbnailUrlText) != null && !getArguments().getString(thumbnailUrlText).equals(NO_VIDEO)) {
            Content = getArguments().getString(thumbnailUrlText);
            initializePlayer();
        }}

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            currentWindow= savedInstanceState.getInt("current window");
            playbackPosition=savedInstanceState.getLong("current position");
            playWhenReady=savedInstanceState.getBoolean("ready");
            description=savedInstanceState.getString("description");
            Content=savedInstanceState.getString("content");
            initializePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (getArguments().getString(VideoUrlText) != null && !getArguments().getString(VideoUrlText).equals(NO_VIDEO)) {
                Content = getArguments().getString(VideoUrlText);
                Uri uri = Uri.parse(Content);
                initializePlayer();
            } else if (getArguments().getString(thumbnailUrlText) != null && !getArguments().getString(thumbnailUrlText).equals(NO_VIDEO)) {
                Content = getArguments().getString(thumbnailUrlText);
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().getString(mTwoPaneText) != null && getArguments().getString(mTwoPaneText).equals("onePane")) {
            mOnePane = true;

          if (mOnePane) hideSystemUi();
        }
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (getArguments().getString(VideoUrlText) != null && !getArguments().getString(VideoUrlText).equals(NO_VIDEO)) {
                Content = getArguments().getString(VideoUrlText);
                initializePlayer();
            } else if (getArguments().getString(thumbnailUrlText) != null && !getArguments().getString(thumbnailUrlText).equals(NO_VIDEO)) {
                Content = getArguments().getString(thumbnailUrlText);
                initializePlayer();
            }
        }
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

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow,playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(Content));
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
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("Baking_App"))
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("ready",playWhenReady);
        outState.putLong("current position",playbackPosition);
        outState.putInt("current window",currentWindow);
        outState.putString("description",description);
        outState.putString("content", Content);
        super.onSaveInstanceState(outState);
    }
}