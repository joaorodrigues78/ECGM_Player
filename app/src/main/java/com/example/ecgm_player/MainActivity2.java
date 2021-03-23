package com.example.ecgm_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {

    // inicializar Variaveis
    // Exoplayer
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btnFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;

    String videourl = "https://r2---sn-5hnekn7k.googlevideo.com/videoplayback?expire=1616519237&ei=5ctZYM7BBZuXkwbNiY6QDg&ip=45.72.81.154&id=o-ADlkMDP0ZSwWxJFz1NA6jSkvoJrdNqlS6XBfZOSmEmh-&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=F8DwV2XuMheHmFTb4fZEPoEF&gir=yes&clen=31535574&ratebypass=yes&dur=361.488&lmt=1614750460469124&fvip=2&fexp=24001373,24007246&c=WEB&txp=1430434&n=-d7ViUCxh7xPOWUYTZ&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAPZkkjY2pN_wDDG41I-yOPskDWKcYgxnDMNrS3lMohuSAiBtkD18WK7cMtikyOkrfIzAGQY67d0BTonE7f7E0WbtYQ%3D%3D&redirect_counter=1&cm2rm=sn-a5mrz7d&req_id=8a03624cb60fa3ee&cms_redirect=yes&mh=2t&mip=2001:8a0:f821:5e01:44ee:58be:6de2:aafc&mm=34&mn=sn-5hnekn7k&ms=ltu&mt=1616497487&mv=m&mvi=2&pl=42&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIhAM2MsxDJ5vMfRAvuNKXgxVH6IxL4AoswxJwdzSLYp1bkAiBIpdNczorkb8nqS4BTXBytPt7v_WSC-ZsUE3FeaFHyAw%3D%3D";
    String videourl2 = "https://r4---sn-5hne6nsr.googlevideo.com/videoplayback?expire=1616519309&ei=LcxZYOeaE5r97QS12KXQBA&ip=61.82.223.222&id=o-AOpbx_O19BRj1FUT0FAo67S2bN37DbczTyiBhd-_4fTA&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=DcuENBi2wVCZN3h8hfe5IxQF&gir=yes&clen=8481004&ratebypass=yes&dur=90.511&lmt=1558218877378948&fvip=8&fexp=24001374,24007246&c=WEB&txp=5531432&n=UAcm5oih8kE2ATsQyq&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhAOHnm9x3qXoNOOe_paKvXdQaQClne4xhQ5JzWHZUYat3AiB6MRZ2GCLKtEmY6VsQ6c71ImrDeBZZ63kqNPoiG8kBhQ%3D%3D&rm=sn-3u-20nk7r,sn-3u-bh2l67r&req_id=9dfcd7ddb6fba3ee&ipbypass=yes&redirect_counter=3&cm2rm=sn-ogue7e7z&cms_redirect=yes&mh=FC&mip=2001:8a0:f821:5e01:44ee:58be:6de2:aafc&mm=34&mn=sn-5hne6nsr&ms=ltu&mt=1616497243&mv=m&mvi=4&pl=42&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgTjWvCvdVDORGi-SFwrg53HwKAQXysZUeBeDk0mN6QGcCIQC487xJsPR28Su5jx-AV_NnbQHACxXcRFmikjZm8-GLhw%3D%3D";

    //String videourl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4";
    //String videourl2 = "https://www.epe.edu.pt/site/wp-content/uploads/2019/04/video.mp4";

    //sensor proximidade
    private SensorManager mSensorManager;
    private Sensor mSensor;

    //sensor acelerometro
    private SensorManager aSensorManager;
    private Sensor aSensor;
    private long updateTime;
    private boolean acel = false;

    // url do video
    Uri videoUrl = Uri.parse(videourl);
    Uri videoUrl2 = Uri.parse(videourl2);

    MediaSource mediaSource;
    MediaSource mediaSource2;

    DefaultHttpDataSourceFactory factory;
    ExtractorsFactory extractorsFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btnFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // proximidade
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // acelerometro
        aSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensorManager.registerListener(this, aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), aSensorManager.SENSOR_DELAY_NORMAL);
        updateTime = System.currentTimeMillis();

        // Se o sensor de proximidade não existir
        /*if (mSensor == null){
            finish();
        }*/
        // Se o sensor de acelerometro não existir
        /*if (aSensor == null){
            finish();
        }*/


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
        factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );

        // Inicializar extratorsFactory
        extractorsFactory = new DefaultExtractorsFactory();

        // Inicializar mediaSource
        mediaSource = new ExtractorMediaSource(
                videoUrl, factory, extractorsFactory, null, null
        );
        mediaSource2 = new ExtractorMediaSource(
                videoUrl2, factory, extractorsFactory, null, null
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
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {  }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

            @Override
            public void onLoadingChanged(boolean isLoading) { }

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
            public void onRepeatModeChanged(int repeatMode) {  }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {  }

            @Override
            public void onPlayerError(ExoPlaybackException error) { }

            @Override
            public void onPositionDiscontinuity(int reason) { }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

            @Override
            public void onSeekProcessed() { }
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
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if (event.values[0] < mSensor.getMaximumRange()){
                simpleExoPlayer.setPlayWhenReady(false);
                simpleExoPlayer.getPlaybackState();
            }else {
                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.getPlaybackState();
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            calculosAcelerometro(event);
        }
    }

    private void calculosAcelerometro(SensorEvent event){
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        long tempoAtual = System.currentTimeMillis();

        float aceleracao = (x*x + y*y + z*z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if (aceleracao >= 2) {
            if (tempoAtual - updateTime < 200) {
                return;
            }

            updateTime = tempoAtual;
            Toast.makeText(this, "Movimento Detetato!!!", Toast.LENGTH_SHORT).show();

            if (acel){
                mediaSource = new ExtractorMediaSource(videoUrl2, factory, extractorsFactory, null,
                        null);

                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(true);
            }

            acel = !acel;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();

        // proximidade
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // acelerometro
        aSensorManager.registerListener(this, aSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), aSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Para o video quando estiver pronto
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();

        // proximidade
        mSensorManager.unregisterListener(this);

        // acelerometro
        aSensorManager.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Dá Play ao video quando estiver pronto
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

}