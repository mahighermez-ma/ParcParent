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
import com.google.android.gms.dynamic.IFragmentWrapper;
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

public class imgGaleryActivity extends AppCompatActivity {
    ArrayList<String> img=new ArrayList<String>();
    ArrayList<String> ids=new ArrayList<String>();
    ArrayList<String> dating=new ArrayList<String>();
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ProgressDialog progressDialog;
    FloatingActionButton removefab;
    RecyclerviewImage dataAdapter;
    private SwipeRefreshLayout swpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_galery);
        removefab=(FloatingActionButton)findViewById(R.id.fab);
        swpref=(SwipeRefreshLayout)findViewById(R.id.swpref);
        swpref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swpref.setRefreshing(false);
            }
        });
        showgalery();
        removefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                int ii=0;
                while (ii<dataAdapter.getremovelist().size()){
                    jsonArray.put("/static/"+dataAdapter.getremovelist().get(ii).split("/static/")[1]);
                    ii++;
                }
                CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(imgGaleryActivity.this);
                try {

                    jsonObject.put("picturesId",jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("fuuuuuuuuuuuuuuukit", jsonObject.toString() );
                //send to server
                AlertDialog.Builder alertClose=new AlertDialog.Builder(imgGaleryActivity.this);
                alertClose.setMessage("Do you want to delete the Picture?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //send
                                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/delete-picture/",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.e("32122", response );
                                                try {
                                                    JSONObject j2=new JSONObject(response);
                                                    if (j2.getString("status").equals("ok")){
                                                        Toast.makeText(imgGaleryActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
//                                                        showgalery();
                                                        finish();
                                                        startActivity(getIntent());
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }



                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                       // progressDialog.dismiss();
                                        Alert.shows(imgGaleryActivity.this,"","please check the connection","ok","");
                                        SendEror.sender(imgGaleryActivity.this,error.toString());
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
                                RequestQueue requestQueue= Volley.newRequestQueue(imgGaleryActivity.this);
                                requestQueue.add(stringRequest);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showgalery();

                    }
                }).show();

            }
        });
    }
    public void showgalery(){
        Log.e("rererrrret", getctoken(imgGaleryActivity.this) );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.pro/api/picture-detail/",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Log.e("32122", response );
                        JSONObject alljs = null;
                        try {
                            alljs = new JSONObject(response);
                            if (alljs.has("token")){

//
                                    JSONArray imgaray=alljs.getJSONArray("Picture");
                                JSONArray idaray=alljs.getJSONArray("Id");
                                    int i=0;
                                    while (i<imgaray.length()){
                                        img.add(imgaray.getString(i));
                                        ids.add(idaray.getString(i));
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
                                    dating.add(String.valueOf(calendar.build().getTime()));
                                    b++;
                                }
//                                Log.e("onResponse", img.get(i));
                                    recyclerView = (RecyclerView) findViewById(R.id.imgrecyclerView);
                                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                    dataAdapter = new RecyclerviewImage(img,imgGaleryActivity.this,"img",removefab,ids,dating);
                                    recyclerView.setAdapter(dataAdapter);
                        }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(imgGaleryActivity.this, e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(imgGaleryActivity.this,"","please check the connection","ok","");
                SendEror.sender(imgGaleryActivity.this, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", getctoken(imgGaleryActivity.this));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(imgGaleryActivity.this);
        requestQueue.add(stringRequest);
    }
    public String getctoken(Context context) {
        CtokenDataBaseManager ctok = new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
}
