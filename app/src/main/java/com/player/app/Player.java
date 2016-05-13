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
    
    private AspectRatioFrameLayout m_videoFrame;
    private SurfaceView m_surfaceView;
    private DashPlayer m_player;
    private Uri m_contentUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button playButton = (Button)findViewById(R.id.button2);
        playButton.setOnClickListener(this);

        Button pauseButton = (Button)findViewById(R.id.button3);
        pauseButton.setOnClickListener(this);
        
        m_videoFrame = (AspectRatioFrameLayout) findViewById(R.id.video_frame);
        m_surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        m_surfaceView.getHolder().addCallback(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.button2:
            // it was the playButton
            m_player.start();
            break;
        case R.id.button3:
            // it was the pauseButton
            m_player.pause();
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
        m_contentUri = intent.getData();
        if (m_player == null) {
            preparePlayer();
        } else {
            m_player.setBackgrounded(false);
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
        if (m_player != null) {
            m_player.setSurface(holder.getSurface());
        }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing.
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (m_player != null) {
            m_player.blockingClearSurface();
        }
    }


    private void preparePlayer() {
        m_player = new DashPlayer(getApplicationContext(),  "DashPlayer", m_contentUri.toString());
        m_player.addListener(this);
        m_player.seekTo(0);
        m_player.prepare();
        m_player.setSurface(m_surfaceView.getHolder().getSurface());
        m_player.start();
    }
    
    private void releasePlayer() {
        if (m_player != null) {
            m_player.release();
            m_player = null;
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        m_videoFrame.setAspectRatio(
                                  height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }


    @Override
    public void onError(Exception e) {
        
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
    }
}
