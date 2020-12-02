package pro.kidsgaurd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class RecordVoiceActivity extends AppCompatActivity {

    ArrayList<String> voiceurl=new ArrayList<String>();
    ArrayList<String> voiceNmae=new ArrayList<String>();
    RecyclerView recyclerViewgetvoice;
//    private SwipeRefreshLayout swpref;
    private DateConverter converter;
    FloatingActionButton fabremove,fab2;
    private VoiceAdapterRecycler adapter ;
    DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_voice);
//        swpref=(SwipeRefreshLayout)findViewById(R.id.swpref);
        fabremove=(FloatingActionButton)findViewById(R.id.fab);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
//        swpref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                finish();
//                startActivity(getIntent());
//                swpref.setRefreshing(false);
//            }
//        });
        geturls(RecordVoiceActivity.this);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=0;
                while (i<adapter.getremovelist().size()){
                    downloadFile(adapter.getremovelist().get(i),voiceNmae.get(voiceurl.indexOf(adapter.getremovelist().get(i)))+".mp3");
                    i++;
                }
                finish();
                startActivity(getIntent());
            }
        });
        fabremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                int ii=0;
                while (ii<adapter.getremovelist().size()){
                    jsonArray.put("/static/"+adapter.getremovelist().get(ii).split("/static/")[1]);
                    ii++;
                }
                CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(RecordVoiceActivity.this);
                try {
                    jsonObject.put("token",ctokenDataBaseManager.getctoken());
                    jsonObject.put("voiceUrl",jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //send to server
                Log.e("fuuuuuuuuuuuuuuukit", jsonObject.toString() );
                AlertDialog.Builder alertClose=new AlertDialog.Builder(RecordVoiceActivity.this);
                alertClose.setMessage("Do you want to delete the videos?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //send
                                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/delete-voice/",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(RecordVoiceActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
//                                                loadvideo();
                                                finish();
                                                startActivity(getIntent());


                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                        progressDialog.dismiss();
                                        Alert.shows(RecordVoiceActivity.this,"","please check the connection","ok","");
                                        SendEror.sender(RecordVoiceActivity.this,error.toString());
                                    }

                                })
                                {
                                    @Override
                                    protected Map<String, String> getParams(){
                                        Map<String,String> params=new HashMap<String, String>();
                                        params.put("data",jsonObject.toString());
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue= Volley.newRequestQueue(RecordVoiceActivity.this);
                                requestQueue.add(stringRequest);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       geturls(RecordVoiceActivity.this);

                    }
                }).show();
            }
        });




    }
    public void btnreqaudio(View view){
        request(RecordVoiceActivity.this, "voice");
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
                                    Alert.shows(RecordVoiceActivity.this,"","Please wait for 5 minutes, then click on the 'Play voice of your kid device' button and listen the voice.","ok","");
                                    break;
                                default:
                                    String message = alljs.getString("message");
                                    SendEror.sender(context, message);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(RecordVoiceActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(RecordVoiceActivity.this, error.toString());
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
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }



    public void geturls(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.pro/api/voice-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse", response );
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            if (alljs.has("token")){
                                    JSONArray jsonArray=alljs.getJSONArray("VideoAddress");
                                    int i=0;
                                    while (i<jsonArray.length()){
                                        voiceurl.add("https://im.kidsguard.pro"+jsonArray.getString(i));
                                        i++;
                                    }
                                JSONArray datearray=alljs.getJSONArray("Date");
                                int b=0;
                                while (b<datearray.length()){
                                    String[] all=datearray.getString(b).split("T");
                                    String[] date=all[0].split("-");
                                    int year= Integer.parseInt(date[0]);
                                    int mounth= Integer.parseInt(date[1]);
                                    int day= Integer.parseInt(date[2]);
                                    String[] time=all[1].split(":");
                                    int hour= Integer.parseInt(time[0]);
                                    int min= Integer.parseInt(time[1]);
                                    Calendar mCalendar = new GregorianCalendar();
                                    mCalendar.set(year,mounth,day,hour,min,00);
                                    Calendar.Builder calendar=new Calendar.Builder();
                                    calendar.setDate(year,mounth-1,day);
                                    calendar.setTimeOfDay(hour,min,0);
                                    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    voiceNmae.add(String.valueOf(calendar.build().getTime()));
                                    b++;
                                }


                                setRecycler();}

                        } catch (JSONException e) {
                            Log.e("onResponse error", e.toString());
                            SendEror.sender(RecordVoiceActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(RecordVoiceActivity.this, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", getctoken(context));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void setRecycler(){
        try {

            recyclerViewgetvoice=(RecyclerView)findViewById(R.id.voiceRecyclerView);

            adapter = new VoiceAdapterRecycler(voiceurl,voiceNmae,RecordVoiceActivity.this,fabremove,fab2);
            recyclerViewgetvoice.setAdapter(adapter);
            LayoutAnimationController animation =
                    AnimationUtils.loadLayoutAnimation(RecordVoiceActivity.this, pro.kidsgaurd.R.anim.layout_animation_fall_down);
            recyclerViewgetvoice.setLayoutAnimation(animation);
            LinearLayoutManager layoutManager = new LinearLayoutManager(RecordVoiceActivity.this);
            recyclerViewgetvoice.setLayoutManager(layoutManager);
        }catch (Exception e){
            Log.e("catch", e.toString() );
        }

    }
    public void btnspicvid(View view){
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                //.setTypeFace(new Typeface())
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        converter = new DateConverter();
                        converter.persianToGregorian(persianCalendar.getPersianYear(),persianCalendar.getPersianMonth(),persianCalendar.getPersianDay());
                      //  Toast.makeText(this, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                       // Datato.setText(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());

                    }

                    @Override
                    public void onDisimised() {

                    }

                });

        picker2.show();
        Button btnrec=(Button)findViewById(R.id.btnrec);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        EditText edtduration=(EditText)findViewById(R.id.edtduration);
        edtduration.setVisibility(View.VISIBLE);
        btnrec.setAlpha(1f);
        timePicker1.setAlpha(1f);
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
    public String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnsenddate(View view){
        EditText edtduration=(EditText)findViewById(R.id.edtduration);
        TimePicker timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
       // String type2=converter.getYear()+","+converter.getMonth()+","+converter.getDay();
        String dur= String.valueOf(Integer.parseInt(edtduration.getText().toString())*60000);
        String type="voice,"+converter.getYear()+","+converter.getMonth()+","+converter.getDay()+","+timePicker1.getHour()+","+timePicker1.getMinute()+","+dur;
//        Log.e("fdfdfgg", type2 );
//        Log.e("fdfdfgg", type );
        request(RecordVoiceActivity.this,type);
    }
    public void downloadFile(String path,String subpath) {

        Uri uri=Uri.parse(path);
        downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setTitle("downloading");
        request.setDescription("wait");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,subpath);
        long donid=downloadManager.enqueue(request);
        Toast.makeText(this, "please wait one minute", Toast.LENGTH_SHORT).show();
        BroadcastReceiver time=new BroadcastReceiver() {
            @RequiresApi(api = 29)
            @Override
            public void onReceive(Context context, Intent intent) {
               // Toast.makeText(context, "file "+subpath+" downloaded ", Toast.LENGTH_SHORT).show();


            }
        };
        IntentFilter intentFilter=new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        this.registerReceiver(time,intentFilter);
    }
}







