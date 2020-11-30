package pro.kidsgaurd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.File;
import java.io.IOException;

public class ShowVidActivity extends AppCompatActivity {
    DownloadManager downloadManager;
    VideoView simpleVideoView;
    MediaController mediaControls;
    Uri videoUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/kidvideo.mp4");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vid);
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/kidvideo.mp4");
        if (file.exists()){
            file.delete();
        }
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");
        downloadFile(path);
    }
    public void downloadFile(String path) {

        Uri uri=Uri.parse(path);
        downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setTitle("downloading");
        request.setDescription("wait");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"kidvideo.mp4");
        long donid=downloadManager.enqueue(request);
        Toast.makeText(this, "please wait one minute", Toast.LENGTH_SHORT).show();
        BroadcastReceiver time=new BroadcastReceiver() {
            @RequiresApi(api = 29)
            @Override
            public void onReceive(Context context, Intent intent) {
                simpleVideoView = (VideoView) findViewById(R.id.simpleVideoView);

                if (mediaControls == null) {
                    // create an object of media controller class
                    mediaControls = new MediaController(ShowVidActivity.this);
                    mediaControls.setAnchorView(simpleVideoView);
                }
                // set the media controller for video view
                simpleVideoView.setMediaController(mediaControls);
                // set the uri for the video view
                simpleVideoView.setVideoURI(videoUri);
                // start a video
                simpleVideoView.start();

                // implement on completion listener on video view
                simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
                    }
                });
                simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
                        return false;
                    }
                });

            }
        };
        IntentFilter intentFilter=new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        this.registerReceiver(time,intentFilter);
    }
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}