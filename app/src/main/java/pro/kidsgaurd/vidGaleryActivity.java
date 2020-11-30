package pro.kidsgaurd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class vidGaleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ArrayList<String> imageUrlList=new ArrayList<String>();
    ArrayList<String> ids=new ArrayList<String>();
    ArrayList<String> dating=new ArrayList<String>();
    ProgressDialog progressDialog;
    FloatingActionButton fabremove;
    RecyclerviewImage dataAdapter;
    Intent intent3;
    String type="";
    String datess="";
    private SwipeRefreshLayout swpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_galery);
        intent3=getIntent();
        if (intent3!=null){
        type=intent3.getStringExtra("Type").split(",,::")[2];
        datess=intent3.getStringExtra("Type").split(",,::")[1];}
        fabremove=(FloatingActionButton)findViewById(R.id.fab);
        swpref=(SwipeRefreshLayout)findViewById(R.id.swpref);
        swpref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swpref.setRefreshing(false);
            }
        });
        loadvideo();
        fabremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                int ii=0;
                while (ii<dataAdapter.getremovelist().size()){
                    jsonArray.put("/static/"+dataAdapter.getremovelist().get(ii).split("/static/")[1]);
                    ii++;
                }
                CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(vidGaleryActivity.this);
                try {
                    jsonObject.put("token",ctokenDataBaseManager.getctoken());
                    jsonObject.put("videoUrl",jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //send to server
                Log.e("fuuuuuuuuuuuuuuukit", jsonObject.toString() );
                AlertDialog.Builder alertClose=new AlertDialog.Builder(vidGaleryActivity.this);
                alertClose.setMessage("Do you want to delete the videos?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //send
                                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/delete-video/",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(vidGaleryActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
//                                                loadvideo();
                                                finish();
                                                startActivity(getIntent());


                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                        Alert.shows(vidGaleryActivity.this,"","please check the connection","ok","");
                                        SendEror.sender(vidGaleryActivity.this,error.toString());
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
                                RequestQueue requestQueue= Volley.newRequestQueue(vidGaleryActivity.this);
                                requestQueue.add(stringRequest);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadvideo();

                    }
                }).show();

            }
        });



    }
    public void loadvideo(){
        progressDialog = ProgressDialog.show(vidGaleryActivity.this, "please wait", "connecting to server...", true);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/video-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e("Ex32123", response );
                        try {
                            progressDialog.dismiss();
                            JSONObject vidobject=new JSONObject(response);
                            if (vidobject.has("token")) {
                                JSONArray viduri=vidobject.getJSONArray("VideoAddress");
                                JSONArray Typeaaray=vidobject.getJSONArray("Type");
                                JSONArray datearray=vidobject.getJSONArray("Date");
                                int i=0;
                                while (i<viduri.length()){
                                    if (Typeaaray.get(i).equals(type)){
                                        String[] all=datearray.getString(i).split("T");
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
                                        DateConverter converter = new DateConverter();
                                        converter.gregorianToPersian(calendar.build().get(Calendar.YEAR), calendar.build().get(Calendar.MONTH)+1, calendar.build().get(Calendar.DAY_OF_MONTH));
                                        String thisdate=String.valueOf(converter.getYear()+"/"+converter.getMonth()+"/"+converter.getDay());
                                        if (thisdate.equals(datess)){
                                            dating.add(String.valueOf(converter.getYear()+"/"+converter.getMonth()+"/"+converter.getDay()+"\n"+calendar.build().getTime().getHours()+":"+calendar.build().getTime().getMinutes()+":"+calendar.build().getTime().getSeconds()));
                                        imageUrlList.add("https://im.kidsguard.pro"+viduri.getString(i));
                                        //dating.add("");
                                            ids.add("");}}
                                            i++;
                                    }

                                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                                gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                dataAdapter = new RecyclerviewImage(imageUrlList,vidGaleryActivity.this,"vid",fabremove,ids,dating);
                                recyclerView.setAdapter(dataAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Alert.shows(vidGaleryActivity.this,"","please check the connection","ok","");
                            SendEror.sender(vidGaleryActivity.this,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Alert.shows(vidGaleryActivity.this,"","please check the connection","ok","");
                SendEror.sender(vidGaleryActivity.this,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("token",getctoken(vidGaleryActivity.this));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(vidGaleryActivity.this);
        requestQueue.add(stringRequest);    }
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}
