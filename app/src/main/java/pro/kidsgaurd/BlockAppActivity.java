package pro.kidsgaurd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BlockAppActivity extends AppCompatActivity {
    int a=0;
    RecyclerView recyclerViewAppLock;
    ProgressDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_blockapp);
        dialog = ProgressDialog.show(BlockAppActivity.this, "please wait", "connecting to server...", true);
        recyclerViewAppLock = (RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewAppLock);
//        getPackage(BlockAppActivity.this);
        getblockPackage(BlockAppActivity.this);



    }

//    public void getPackage(final Context context){
//        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://req.kidsguard.pro/api/getPackages/",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        dialog.dismiss();
//                        try {
//                            JSONObject alljs=new JSONObject(response);
//                            String status=alljs.getString("status");
//
//                            switch (status){
//                                case "ok":
//                                    JSONArray pknamearay=alljs.getJSONArray("packs");
//                                    ArrayList<String> allpack=new ArrayList<String>();
//                                    ArrayList<Boolean> lock=new ArrayList<Boolean>();
//                                    ArrayList<String> firstTime=new ArrayList<String>();
//                                    ArrayList<String> secondTime=new ArrayList<String>();
//                                    int i=0;
//                                    while (i<pknamearay.length()){
//                                        JSONObject pjs=pknamearay.getJSONObject(i);
//                                        String s=pjs.getString("packagename");
//                                        allpack.add(s);
//                                        lock.add(false);
//                                        firstTime.add("");
//                                        secondTime.add("");
//
//                                        i++;
//
//                                    }
//                                    getblockPackage(context,allpack,lock,firstTime,secondTime);
//                                    break;
//                                default:
//                                    String message=alljs.getString("message");
//                                    SendEror.sender(BlockAppActivity.this,message);
//                                    break;
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            SendEror.sender(BlockAppActivity.this,e.toString());
//                            dialog.dismiss();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//                Alert.shows(context,"","please check the connection","ok","");
//                SendEror.sender(BlockAppActivity.this,error.toString());
//
//
//            }
//
//        })
//        {
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("childToken",getctoken(context));
//                return params;
//            }
//        };
//        RequestQueue requestQueue=Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//    }
    public void getblockPackage(final Context context) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/packagename-list/",
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

                                        JSONArray pkgnamearay=jsonstatus.getJSONArray("packagename");
                                        JSONArray blockaray=jsonstatus.getJSONArray("block");
                                        JSONArray starttimearay=jsonstatus.getJSONArray("startTime");
                                        JSONArray endtimearay=jsonstatus.getJSONArray("endTime");
                                        JSONArray appnamearay=jsonstatus.getJSONArray("name");
                                        ArrayList<String> allpack=new ArrayList<String>();
                                        ArrayList<Boolean> lock=new ArrayList<Boolean>();
                                        ArrayList<String> starttime=new ArrayList<String>();
                                        ArrayList<String> endtime=new ArrayList<String>();
                                        ArrayList<String> appName=new ArrayList<String>();

                                        int i=0;
                                        while(i<pkgnamearay.length()){
                                            allpack.add(pkgnamearay.getString(i));
                                            lock.add(blockaray.getBoolean(i));
                                            starttime.add(starttimearay.getString(i));
                                            endtime.add(endtimearay.getString(i));
                                            appName.add(appnamearay.getString(i));
                                            i++;
                                        }

                                        RecyclerViewAppLockAdapter adapter = new RecyclerViewAppLockAdapter(BlockAppActivity.this,lock,appName,starttime,endtime,allpack);
                                        recyclerViewAppLock.setAdapter(adapter);
                                        LayoutAnimationController animation =
                                                AnimationUtils.loadLayoutAnimation(context, pro.kidsgaurd.R.anim.layout_animation_fall_down);
                                        recyclerViewAppLock.setLayoutAnimation(animation);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                        recyclerViewAppLock.setLayoutManager(layoutManager);

                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                       // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        SendEror.sender(BlockAppActivity.this,e.toString());

                                    }
                                    break;
                                default:
                                    String message=jsonstatus.getString("message");
                                    SendEror.sender(BlockAppActivity.this,message);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            dialog.dismiss();
                            SendEror.sender(BlockAppActivity.this,e.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                SendEror.sender(BlockAppActivity.this,error.toString());



            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("token",getctoken(context));
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {

        alert();
    }


    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            alert();
        }
        return true;
    }
    public void alert(){
        AlertDialog.Builder Alert_close=new AlertDialog.Builder(BlockAppActivity.this);
        Alert_close.setTitle("warning").
                setMessage("Do you want to save changes?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        blockAppdb blockAppdb=new blockAppdb(BlockAppActivity.this);
                        JSONArray jsapp=new JSONArray();
                        ArrayList<String> appname=new ArrayList<String>();
                        if (blockAppdb.getjs().size()>0){
                        while (a<blockAppdb.getjs().size()){
                            String[] appblock=blockAppdb.getjs().get(a).split(":");
                            appname.add(appblock[0]);
                            String lock=appblock[1];
                            String ftime=appblock[2];
                            String stime=appblock[3];
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("packagename",appblock[0]);
                                jsonObject.put("block",lock);
                                jsonObject.put("startTime",ftime);
                                jsonObject.put("endTime",stime);
                                jsapp.put(jsonObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            a++;
                        }

                        blockIns blockIns=new blockIns();
                        String json= String.valueOf(jsapp);
                        blockIns.insblock(BlockAppActivity.this,json);
                        blockAppdb.delall();
                        }
                        startActivity(new Intent(BlockAppActivity.this,WelcomeActivity.class));

                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        blockAppdb blockAppdb=new blockAppdb(BlockAppActivity.this);
                        blockAppdb.delall();
                        startActivity(new Intent(BlockAppActivity.this,WelcomeActivity.class));
                        dialog.cancel();
                    }
                }).show();
    }

}
