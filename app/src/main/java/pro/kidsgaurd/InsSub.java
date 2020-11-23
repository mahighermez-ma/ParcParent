package pro.kidsgaurd;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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

public class InsSub {
    ProgressDialog dialog = null;
    public void insLock(final Context context, final String lock){
        dialog = ProgressDialog.show(context, "please wait", "connecting to server...", true);
        String url="https://im.kidsguard.pro/api/lock-phone/";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonlock=new JSONObject(response);
                            String status=jsonlock.getString("status");

                            switch (status){
                                case "ok":
                                    Log.e("phonelock", response );
                                    break;
                                default:
                                    String message=jsonlock.getString("message");
                                    SendEror.sender(context,message);
                                    break;
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            SendEror.sender(context,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Alert.shows(context,"","please check the connection","ok","");
                SendEror.sender(context,error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("kidToken",getctoken(context));
                params.put("lock",lock);
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
