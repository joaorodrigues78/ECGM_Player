package com.example.ecgm_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity2 extends AppCompatActivity {

    // inicializar Variaveis
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btnFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;
    String videourl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4";
    String videourl2 = "https://www.epe.edu.pt/site/wp-content/uploads/2019/04/video.mp4";
    String videourl3 = "https://www.epe.edu.pt/site/wp-content/uploads/2019/04/video.mp4";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btnFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // url do video
        Uri videoUrl = Uri.parse(videourl2);

        // Inicializar load control
        LoadControl loadControl = new DefaultLoadControl();

        // Inicializar band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // Inicializar trackSelector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );

        // Inicializar simpleExoPlayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                MainActivity2.this, trackSelector, loadControl
        );

        // Inicializar dataSourceFactory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );

        // Inicializar extratorsFactory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // Inicializar mediaSource
        MediaSource mediaSource = new ExtractorMediaSource(
                videoUrl, factory, extractorsFactory, null, null
        );

        // Player
        playerView.setPlayer(simpleExoPlayer);

        // Ecrã ligado
        playerView.setKeepScreenOn(true);

        // Prepare Media
        simpleExoPlayer.prepare(mediaSource);

        // Play video quando carregamento estiver concluido
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                // Verifica condição
                if (playbackState == Player.STATE_BUFFERING){
                    // Quando está a carregar o video, mostra a progressBar
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY){
                    // Quando o video está carregado, esconde a progressBar
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar condição
                if (flag){
                    // Quando a flag é verdadeira (true), coloca o video em fullscreen
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));

                    // portrait
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                } else {
                    // Quando a flag é falsa (false), o video sai de fullscreen
                    btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));

                    // Landscape
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Para o video quando estiver pronto
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Dá Play ao video quando estiver pronto
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }
}