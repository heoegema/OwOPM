package owo.owopm;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class ViewResults extends AppCompatActivity {
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.elephanttrunk);
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }
}
