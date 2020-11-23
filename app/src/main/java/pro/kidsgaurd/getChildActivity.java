package pro.kidsgaurd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
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
import java.util.Map;

public class getChildActivity extends AppCompatActivity {
    ProgressDialog dialog = null;
    String activity="1";
    String a="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_get_child);
        getchild(getChildActivity.this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(pro.kidsgaurd.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                if (intent.getStringExtra("activity").isEmpty()){}else {
                    if (intent.getStringExtra("activity").equals("welcome")){
                        a="2";
                    }else {}}
                Intent b9 = new Intent(getChildActivity.this ,AddChildActivity.class);
                b9.putExtra("activity","child"+a);
                startActivity(b9);

            }
        });

        dialog = ProgressDialog.show(getChildActivity.this, "please wait", "connecting to server...", true);

    }
    public void getchild(final Context context){

        String url="https://im.kidsguard.pro/api/all-kids/";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();
                        try {
                            JSONObject jsonchilldcondition=new JSONObject(response);
                            String status=jsonchilldcondition.getString("status");
                            switch (status){
                                case "ok":
                                    JSONArray childnamearay=jsonchilldcondition.getJSONArray("kidsName");
                                    JSONArray childtokenaray=jsonchilldcondition.getJSONArray("kidsToken");
                                    final ArrayList<String> childtoken=new ArrayList<String>();
                                    ArrayList<String> childname=new ArrayList<String>();
                                    ArrayList<Boolean> activestatus=new ArrayList<Boolean>();
                                    CtokenDataBaseManager ctokenDataBaseManager=new CtokenDataBaseManager(context);
                                    String ctokenn=ctokenDataBaseManager.getctoken();
                                    int i=0;
                                    while (i<childnamearay.length()){
                                        if (childtokenaray.getString(i).equals("")){}else {
                                            childname.add(childnamearay.getString(i));
                                            childtoken.add(childtokenaray.getString(i));
                                            activestatus.add(false);
                                        }
                                        i++;
                                    }
                                    int a;
                                    if (ctokenn == "12") {a=0;}else {
                                        a=1;
                                    activestatus.set(childtoken.indexOf(ctokenn),true);}
                                    RecyclerView recyclerViewgetChild=(RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewGetChild);
                                    RecyclerViewGetchildAdapter adapter = new RecyclerViewGetchildAdapter(childname,activestatus,childtoken,context,a);
                                    recyclerViewgetChild.setAdapter(adapter);
                                    LayoutAnimationController animation =
                                            AnimationUtils.loadLayoutAnimation(context, pro.kidsgaurd.R.anim.layout_animation_fall_down);
                                    recyclerViewgetChild.setLayoutAnimation(animation);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                    recyclerViewgetChild.setLayoutManager(layoutManager);
                                    break;
                                default:
//                                    Alert.shows(context,"","please try again","ok","");
                                    String message=jsonchilldcondition.getString("message");
                                    SendEror.sender(getChildActivity.this,message);
                                    break;
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            SendEror.sender(getChildActivity.this,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(getChildActivity.this,error.toString());

            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("parentToken",getToken(context));
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public String getToken(Context context){
        OwnerDataBaseManager own=new OwnerDataBaseManager(context);
        return own.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent=getIntent();
        //Toast.makeText(this, intent.getStringExtra("activity"), Toast.LENGTH_LONG).show();
        if (intent.getStringExtra("activity").isEmpty()){allert();}else {
        if (intent.getStringExtra("activity").equals("welcome")){
            startActivity(new Intent(getChildActivity.this,WelcomeActivity.class));
        }else {
            allert();
        }}
    }
    public void allert(){
        AlertDialog.Builder alertClose=new AlertDialog.Builder(getChildActivity.this);
        alertClose.setTitle(pro.kidsgaurd.R.string.titleExitConfirm).
                setMessage(pro.kidsgaurd.R.string.bodyExitConfirm).
                setPositiveButton(pro.kidsgaurd.R.string.acceptExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getChildActivity.this,VoroodActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT",true);
                        startActivity(intent);
                    }
                }).
                setNegativeButton(pro.kidsgaurd.R.string.declineExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
