package de.eiselecloud.testplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

/**
 * Created by alexander on 15.10.17.
 */

public class MyVideoPlayer {

    Context context;
    Handler mainHandler;
    DataSource.Factory dataSourceFactory;
    SimpleExoPlayer player;


    public MyVideoPlayer(Context context) {
        this.context = context;
    }

    public void initPlayer(SimpleExoPlayerView exoPlayerView) {
        // 1. Create a default TrackSelector

        mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        exoPlayerView.setPlayer(player);

        dataSourceFactory = new DefaultDataSourceFactory(context, "testplayer");
        // Produces Extractor instances for parsing the media data.
    }

    public void playHLS(Uri url){
        HlsMediaSource videoSource = new HlsMediaSource(url, dataSourceFactory, mainHandler, null);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    public void stop(){
        player.stop();
    }

}
