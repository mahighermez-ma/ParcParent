package pro.kidsgaurd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecordVideoActivity extends AppCompatActivity {
    VideoView v;
    MediaController mediaController;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
    }

    public void btnShowVideo(View view) {
        ShowVideo();
    }

    public void request(final Context context, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.pro/api/request-media/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            String status = alljs.getString("status");

                            switch (status) {
                                case "ok":
                                    Alert.shows(RecordVideoActivity.this,"","Please wait for 5 minutes, then click on the 'Show captured video' button and see the video.","ok","");
                                    break;
                                default:
                                    String message = alljs.getString("message");
                                    SendEror.sender(context, message);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(RecordVideoActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(RecordVideoActivity.this,"","please check the connection","ok","");
                SendEror.sender(RecordVideoActivity.this, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("media", type);
                params.put("parentToken", getowner(context));
                params.put("kidToken", getctoken(context));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void requestFrontVideoCamera(View view) {
        request(RecordVideoActivity.this, "video1");
    }

    public void requestRearVideoCamera(View view) {
        request(RecordVideoActivity.this, "video2");
    }
    public void btnreqaudio(View view){
        request(RecordVideoActivity.this, "voice");
    }
    public void btnshowaudio(View view){
        String path="https://req.kidsguard.pro/static/"+getctoken(getApplicationContext())+"/voice/latest.mp3";
        goToUrl(path);

    }
    public void btnspicvid(View view){
        DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        Button btnrec=(Button)findViewById(R.id.btnrec);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        simpleDatePicker.setAlpha(1f);
        btnrec.setAlpha(1f);
        timePicker1.setAlpha(1f);
    }
    public void ShowVideo() {
        startActivity(new Intent(getApplicationContext(),VideoCategoryActivity.class));

    }

    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }

    public String getowner(Context context) {
        OwnerDataBaseManager owne = new OwnerDataBaseManager(context);
        return owne.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public void btnsenddate(View view){
        AlertDialog.Builder alertClose=new AlertDialog.Builder(RecordVideoActivity.this);
        alertClose.setTitle("").
                setMessage("").
                setPositiveButton("Front camera", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
                        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
                        int a=simpleDatePicker.getMonth()+1;
                        String b= String.valueOf(a);
                        String type="video1,"+simpleDatePicker.getYear()+","+b+","+simpleDatePicker.getDayOfMonth()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+"30000";
                        Toast.makeText(RecordVideoActivity.this, type, Toast.LENGTH_SHORT).show();
                        request(RecordVideoActivity.this,type);
                    }
                }).setNegativeButton("Rear camera", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
                TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
                int a=simpleDatePicker.getMonth()+1;
                String b= String.valueOf(a);
                String type="video2,"+simpleDatePicker.getYear()+","+b+","+simpleDatePicker.getDayOfMonth()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+"300000";
                Toast.makeText(RecordVideoActivity.this, type, Toast.LENGTH_SHORT).show();
                request(RecordVideoActivity.this,type);
            }
        }).show();
    }

}

