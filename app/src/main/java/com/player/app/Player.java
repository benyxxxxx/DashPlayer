package com.player.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.exercise.dashsdk.DashPlayer;
import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.util.Util;

public class Player extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener,
                                                         DashPlayer.Listener {
    
    private AspectRatioFrameLayout videoFrame;
    private SurfaceView surfaceView;
    private DashPlayer player;
    private Uri contentUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button playButton = (Button)findViewById(R.id.button2);
        playButton.setOnClickListener(this);

        Button pauseButton = (Button)findViewById(R.id.button3);
        pauseButton.setOnClickListener(this);
        
        videoFrame = (AspectRatioFrameLayout) findViewById(R.id.video_frame);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.button2:
            // it was the playButton
            player.start();
            break;
        case R.id.button3:
            // it was the pauseButton
            player.pause();
            break;
        }
    }
    
    public void onResume() {
        super.onResume();
            onShown();
    }
    
    public void onStart() {
        super.onStart();
        onShown();
    }

    private void onShown() {
        Intent intent = getIntent();
        contentUri = intent.getData();
        if (player == null) {
            preparePlayer();
        } else {
            player.setBackgrounded(false);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        setIntent(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
    
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (player != null) {
            player.setSurface(holder.getSurface());
        }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player != null) {
            player.blockingClearSurface();
        }
    }


    private void preparePlayer() {
        String userAgent = Util.getUserAgent(this, "DashPlayer");
        player = new DashPlayer(getApplicationContext(), userAgent, contentUri.toString());
        player.addListener(this);
        player.seekTo(0);
        player.prepare();
        player.setSurface(surfaceView.getHolder().getSurface());
        player.start();
    }
    
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        videoFrame.setAspectRatio(
                                  height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }


    @Override
    public void onError(Exception e) {
        
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
    }
}
