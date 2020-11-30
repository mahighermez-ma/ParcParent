package pro.kidsgaurd;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ExplainItemActivity extends AppCompatActivity {

    ArrayList<String> aray = new ArrayList<String>();

    StringRequest stringRequest;
    RequestQueue requestQueue;
    RecyclerView recyclerViewDetail;
    ProgressDialog dialog = null;
    ArrayList<String> res=new ArrayList<String>();
    EditText edtphonesearch;
    private ImageButton imgleftdra;
    private String text;
    SwipeRefreshLayout swpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_explainitem);
        dialog = ProgressDialog.show(ExplainItemActivity.this, "please wait", "connecting to server...", true);
        recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
        Intent intent = getIntent();
        swpref=(SwipeRefreshLayout)findViewById(R.id.swpref);
        swpref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swpref.setRefreshing(false);
            }
        });

        String msgIntent = intent.getStringExtra("IntentName");

        switch (msgIntent)
        {
            case "SMS Data":
                //Code Here

                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/sms-list/",
                        new Response.Listener<String>() {
                            @TargetApi(Build.VERSION_CODES.N)
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                try {

                                    JSONObject jsonsms=new JSONObject(response);
                                    String status=jsonsms.getString("status");

                                    switch (status){
                                        case "ok":

                                            JSONArray diraray=jsonsms.getJSONArray("direction");
                                            JSONArray bodyaray=jsonsms.getJSONArray("body");
                                            JSONArray numberaray=jsonsms.getJSONArray("number");
                                            int i=0;
                                           // ArrayList<String> res=new ArrayList<String>();
                                            while (i<numberaray.length()){
                                                String name=numberaray.getString(i);
                                                String number=bodyaray.getString(i);
                                                String dir=diraray.getString(i);
                                                res.add(name+":"+"\n"+number+"\n"+dir);

                                                i++;
                                            }

                                           recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                           recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);


                                            break;
                                        default:
                                            String message=jsonsms.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());


                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());
                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                       // params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "Contact Data":
                //Code Here

                stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/contacts-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                Log.e("smsres", response );
                                try {
                                    JSONObject jsoncontact=new JSONObject(response);
                                    String status=jsoncontact.getString("status");

                                    switch (status){
                                        case "ok":

                                            JSONArray contactname=jsoncontact.getJSONArray("name");
                                            JSONArray contactnum=jsoncontact.getJSONArray("tell");

                                            int i=0;
                                            while (i<contactname.length()){
                                                String name=contactname.getString(i);
                                                String number=contactnum.getString(i);
                                                res.add(name+":"+"\n"+number);

                                                i++;
                                            }


                                            recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                            recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);


                                            break;
                                        default:
                                            String message=jsoncontact.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());

                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "Call Data":
                //Code Here

                stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/calls-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                try {
                                    JSONObject jsoncall=new JSONObject(response);
                                    String status=jsoncall.getString("status");

                                    switch (status){
                                        case "ok":
                                            JSONArray phnumberaray=jsoncall.getJSONArray("phnumber");
                                            JSONArray calldatearay=jsoncall.getJSONArray("calldate");
                                            JSONArray calldurationaray=jsoncall.getJSONArray("callduration");
                                            JSONArray diraray=jsoncall.getJSONArray("dir");


                                            int i=0;
                                            while (i<phnumberaray.length()){
                                                String phNumber=phnumberaray.getString(i);
                                                String callDate=calldatearay.getString(i);
                                                Date callDayTime = new Date(Long.valueOf(callDate));
                                                String callDuration=calldurationaray.getString(i);
                                                String dir=diraray.getString(i);
                                                res.add("number: "+phNumber+"\n"+"date: "+callDayTime+"\n"+"duration: "+callDuration+"\n"+"direction: "+dir);

                                                i++;
                                            }


                                           recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                           recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);

                                            break;
                                        default:
                                            String message=jsoncall.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    e.printStackTrace();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Alert.shows(ExplainItemActivity.this,"","please check the connection","ok","");
                        SendEror.sender(ExplainItemActivity.this,error.toString());


                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        //params.put("parentToken",getowner(ExplainItemActivity.this));
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);

                break;

            case "InstalledPackage Data":
                //Code Here
                stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/packagename-list/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                JSONObject jsonstatus = null;

                                try {
                                    jsonstatus = new JSONObject(response);
                                    String status = jsonstatus.getString("status");
                                    switch (status){
                                        case "ok":
                                            try {
                                                JSONArray appnamearay=jsonstatus.getJSONArray("name");

                                                int i=0;
                                                while(i<appnamearay.length()){
                                                    res.add(appnamearay.getString(i));
                                                    i++;
                                                }

                                                recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                                recyclerViewAddList(ExplainItemActivity.this,res,recyclerViewDetail);

                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                SendEror.sender(ExplainItemActivity.this,e.toString());

                                            }
                                            break;
                                        default:
                                            String message=jsonstatus.getString("message");
                                            SendEror.sender(ExplainItemActivity.this,message);
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    dialog.dismiss();
                                    SendEror.sender(ExplainItemActivity.this,e.toString());

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        SendEror.sender(ExplainItemActivity.this,error.toString());



                    }

                })
                {
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("token",getctoken(ExplainItemActivity.this));
                        return params;
                    }
                };
                requestQueue=Volley.newRequestQueue(ExplainItemActivity.this);
                requestQueue.add(stringRequest);


                break;

        }

        setress();

    }
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
    public String getowner(Context context){
        OwnerDataBaseManager owne=new OwnerDataBaseManager(context);
        return owne.getowner();
    }

    public void recyclerViewAddList(Context context , ArrayList<String> arrayList,RecyclerView recyclerView){
        RecyclerViewDetailItemAdapter adapter = new RecyclerViewDetailItemAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);

        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(context, pro.kidsgaurd.R.anim.layout_animation_slide_from_left);
        recyclerView.setLayoutAnimation(animation);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }
    public void setress(){
        edtphonesearch=(EditText) findViewById(R.id.edtphonesearch);
        imgleftdra=(ImageButton)findViewById(R.id.imgleftdra);
        edtphonesearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Drawable img = getResources().getDrawable(R.drawable.ic_close);
                img.setBounds(0, 0, 60, 60);
                imgleftdra.setImageDrawable(img);
                text=editable.toString();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        if (editable.toString().equals(text)){
                            if (editable.toString().equals("")){
                                Drawable img = getResources().getDrawable(R.drawable.ic_search_black_24dp);
                                img.setBounds(0, 0, 60, 60);
                                imgleftdra.setImageDrawable(img);
                                recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);
                            }else {
                                ArrayList<String> resfilter =new ArrayList<>( res.stream()
                                        .filter(x->x.toLowerCase().contains(editable.toString()))
                                        .collect(Collectors.toList()));
                                recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                                recyclerViewAddList(getApplicationContext(),resfilter,recyclerViewDetail);

                            }
                        }
                    }
                },300);

            }
        });
        imgleftdra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtphonesearch.setText("");
                recyclerViewDetail = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewDetailItem);
                recyclerViewAddList(getApplicationContext(),res,recyclerViewDetail);
            }
        });
            }



}
