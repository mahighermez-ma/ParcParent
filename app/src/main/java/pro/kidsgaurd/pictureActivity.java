package pro.kidsgaurd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class pictureActivity extends AppCompatActivity {
    String type;
    ArrayList<String> img=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_picture);
    }

    public void btnShowPhoto(View view) {
        ShowPic(pictureActivity.this);
    }

    public void request(final Context context, final String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.pro/api/request-pic/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject alljs = null;
                        Log.e("lock123", response);
                        try {
                            alljs = new JSONObject(response);
                            String status = alljs.getString("status");

                            switch (status) {
                                case "ok":
                                    Alert.shows(pictureActivity.this,"","Please wait for 5 minutes, then click on the 'Show requested picture' button and see the photo.","ok","");
                                    break;
                                default:
                                    String message = alljs.getString("message");
                                    SendEror.sender(context, message);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(pictureActivity.this, e.toString());

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(pictureActivity.this,"","please check the connection","ok","");
                SendEror.sender(pictureActivity.this, error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cam", type);
                params.put("parentToken", getowner(context));
                params.put("kidToken", getctoken(context));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void ShowPic(final Context context) {
        startActivity(new Intent(getApplicationContext(),imgGaleryActivity.class));
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


    public void requestFrontCamera(View view) {
        request(pictureActivity.this, "1");
    }

    public void requestRearCamera(View view) {
        request(pictureActivity.this, "2");
    }
}
