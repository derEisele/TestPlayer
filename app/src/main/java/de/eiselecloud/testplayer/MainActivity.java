package de.eiselecloud.testplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MyVideoPlayer videoPlayer;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = this.getApplicationContext();

        videoPlayer = new MyVideoPlayer(c);
        SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.video_player);
        videoPlayer.initPlayer(exoPlayerView);

        initGui();

    }

    private void initGui(){
        final EditText editTextUrl = (EditText) findViewById(R.id.etURL);
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        Button btnSHowlist = (Button) findViewById(R.id.btnShowList);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri url = Uri.parse(editTextUrl.getText().toString());
                videoPlayer.playHLS(url);

                Toast.makeText(c, "Play", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoPlayer.stop();
                Toast.makeText(c, "Stop", Toast.LENGTH_SHORT).show();
            }
        });

        btnSHowlist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShowList:
                openShowList();
                break;
        }
    }

    public void openShowList(){
        Intent intent = new Intent(c, ShowListActivity.class);
        startActivity(intent);
    }

    public void openShow(){
        Intent intent = new Intent(c, ShowActivity.class);
        startActivity(intent);
    }
}
