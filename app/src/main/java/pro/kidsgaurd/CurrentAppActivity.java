package pro.kidsgaurd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentAppActivity extends AppCompatActivity {

    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_currentapp);
        dialog = ProgressDialog.show(CurrentAppActivity.this, "please wait", "connecting to server...", true);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://req.kidsguard.pro/api/getApps/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonapp=new JSONObject(response);
                            String status=jsonapp.getString("status");

                            switch (status){
                                case "ok":
                                    JSONObject appjs=jsonapp.getJSONObject("app");
                                    String app=appjs.getString("app");
                                    if (app.equals("")){

                                        Toast.makeText(CurrentAppActivity.this, "The phone's screen is off", Toast.LENGTH_LONG).show();

                                    }else {

                                            // BlockCurr(CurrentAppActivity.this,"");


                                        String[] nam=app.split("\\.");
                                        ArrayList<String> appName=new ArrayList<String>();
                                        ArrayList<Boolean> lock=new ArrayList<Boolean>();
                                        ArrayList<String> startTime=new ArrayList<String>();
                                        ArrayList<String> endTime=new ArrayList<String>();
                                        ArrayList<String> allpack=new ArrayList<String>();
                                        appName.add(nam[nam.length-1]);
                                        lock.add(false);
                                        startTime.add("");
                                        endTime.add("");
                                        allpack.add(app);
                                        RecyclerView recyclerViewAppLock=(RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewAppLockCurr);
                                        RecyclerViewAppLockAdapter adapter = new RecyclerViewAppLockAdapter(CurrentAppActivity.this,lock,appName,startTime,endTime,allpack);
                                        recyclerViewAppLock.setAdapter(adapter);
                                        LayoutAnimationController animation =
                                                AnimationUtils.loadLayoutAnimation(CurrentAppActivity.this, pro.kidsgaurd.R.anim.layout_animation_fall_down);
                                        recyclerViewAppLock.setLayoutAnimation(animation);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(CurrentAppActivity.this);
                                        recyclerViewAppLock.setLayoutManager(layoutManager);

                            }

                                    break;
                                default:
                                    String message=jsonapp.getString("message");
                                    SendEror.sender(CurrentAppActivity.this,message);
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            SendEror.sender(CurrentAppActivity.this,e.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Alert.shows(CurrentAppActivity.this,"","please check the connection","ok","");
                SendEror.sender(CurrentAppActivity.this,error.toString());


            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("parentToken",getowner(CurrentAppActivity.this));
                params.put("childToken",getctoken(CurrentAppActivity.this));
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(CurrentAppActivity.this);
        requestQueue.add(stringRequest);

    }
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
    public String getowner(Context context){
        OwnerDataBaseManager owne=new OwnerDataBaseManager(context);
        return owne.getowner();
    }
    @Override
    public void onBackPressed() {
        int a=0;
        blockAppdb blockAppdb=new blockAppdb(CurrentAppActivity.this);
        JSONObject jsapp=new JSONObject();
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
                    jsonObject.put("lock",lock);
                    jsonObject.put("startTime",ftime);
                    jsonObject.put("endTime",stime);
                    jsapp.put(appblock[0],jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                a++;
            }

            blockIns blockIns=new blockIns();
            String json= String.valueOf(jsapp);
            blockIns.insblock(CurrentAppActivity.this,json);
            blockAppdb.delall();}
        startActivity(new Intent(CurrentAppActivity.this,WelcomeActivity.class));


    }

}
