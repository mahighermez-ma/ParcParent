package pro.kidsgaurd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class RegisterClass {
    public static void regis(final Context context, final String phoneNumber, final String pass, final String email){
    String url="https://im.kidsguard.pro/api/register/";
    StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonregister=new JSONObject(response);
                        String status=jsonregister.getString("status");

                        switch (status){
                            case "ok":
                                final SharedPreferences shared=context.getSharedPreferences("prefs",MODE_PRIVATE);
                                final SharedPreferences.Editor editor=shared.edit();
                                editor.putBoolean("FIRSTRUN",false);
                                editor.commit();
//                                JSONObject jsonparent=jsonregister.getJSONObject("parent");
                                String owner=jsonregister.getString("token");
                                OwnerDataBaseManager own=new OwnerDataBaseManager(context);
                                own.Insertowner(owner);
                                Intent intent=new Intent(context,getChildActivity.class);
                                intent.putExtra("activity","first");
                                context.startActivity(intent);
                                break;
                            case "error":
                                String message=jsonregister.getString("message");
                                SendEror.sender(context,message.toString());
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        SendEror.sender(context,e.toString());
                    }





                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context, "please check the connection", Toast.LENGTH_LONG).show();
            SendEror.sender(context,error.toString());
        }

    })
    {
        @Override
        protected Map<String, String> getParams(){
            Map<String,String> params=new HashMap<String, String>();
            params.put("username",phoneNumber);
            params.put("password",pass);
            params.put("email",email);
            return params;
        }
    };
    RequestQueue requestQueue=Volley.newRequestQueue(context);
    requestQueue.add(stringRequest);
}
}
