package pro.kidsgaurd;

import android.content.Context;
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

public class blockIns {
    public void insblock(final Context context, final String bloc){
        String url="https://im.kidsguard.pro/api/update-packagename/";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonaddchild=new JSONObject(response);
                            String status=jsonaddchild.getString("status");

                            switch (status){
                                case "ok":
                                    Toast.makeText(context, "saved", Toast.LENGTH_LONG).show();
                                    break;
                                default:

                                    String message=jsonaddchild.getString("message");
                                    SendEror.sender(context,message);
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender(context,e.toString());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(context,error.toString());

            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                //params.put("parentToken",getowner(context));
                params.put("token",getctoken(context));
                params.put("packages",bloc);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
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

}
