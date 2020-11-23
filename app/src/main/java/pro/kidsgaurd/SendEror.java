package pro.kidsgaurd;

import android.content.Context;

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

public class SendEror {
    public static void sender(final Context context, final String message){
        String url="https://im.kidsguard.pro/api/add-error/";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonlogin= null;
                        try {
                            jsonlogin = new JSONObject(response);
                        } catch (JSONException e) {
                            SendEror.sender(context,e.toString());
                            e.printStackTrace();
                        }
                        String status= null;
                        try {
                            status = jsonlogin.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("error",message);
                params.put("token",getToken(context));
                params.put("typeToken","parent");
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public static String getToken(Context context){
        OwnerDataBaseManager tokenDataBaseManager=new OwnerDataBaseManager(context);
        return tokenDataBaseManager.getowner();
    }
}