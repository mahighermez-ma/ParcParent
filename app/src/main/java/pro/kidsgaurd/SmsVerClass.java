package pro.kidsgaurd;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SmsVerClass {
    public static void smver(final Context context, final String phneNumber, final String code){
        String url="https://req.kidsguard.pro/api/register/";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonver=new JSONObject(response);
                            String status=jsonver.getString("status");
                            String message=jsonver.getString("message");
                            switch (status){
                                case "ok":
                                    JSONObject jsonparent=jsonver.getJSONObject("parent");
                                    String owner=jsonparent.getString("token");
                                    OwnerDataBaseManager own=new OwnerDataBaseManager(context);
                                    own.Insertowner(owner);
                                    Intent intent=new Intent(context,WelcomeActivity.class);
                                    context.startActivity(intent);
                                    break;
                                case "error":
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    break;
                            }

                        } catch (JSONException e) {
                            //Toast.makeText(context, "catch", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "fail", Toast.LENGTH_LONG).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile",phneNumber);
                params.put("code",code);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
