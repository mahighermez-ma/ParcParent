package pro.kidsgaurd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class RecyclerViewAdapterWelcome extends RecyclerView.Adapter<RecyclerViewAdapterWelcome.ViewHolder> {
    ProgressDialog dialog = null;
    Intent i;
    private ArrayList<String> Name;
    private Context context;

    public RecyclerViewAdapterWelcome(ArrayList<String> name, Context context) {
        Name = name;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;
        public ViewHolder (CardView v){
            super(v);
            cardViieeww = v;
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(pro.kidsgaurd.R.layout.card_view_welcome, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardViieeww;
        TextView Namee = (TextView)cardView.findViewById(pro.kidsgaurd.R.id.txtwelcome);
        Namee.setText(Name.get(position));
        ImageView infsign=(ImageView)cardView.findViewById(pro.kidsgaurd.R.id.infsign);
        ImageView aboveIcon=(ImageView)cardView.findViewById(pro.kidsgaurd.R.id.ivIconWelcome);

        switch (position){
            case 0:
                aboveIcon.setImageResource(R.drawable.ic_sms_black);
                break;
            case 1:
                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_contact_phone_black);
                break;
            case 2:
                aboveIcon.setImageResource(R.drawable.ic_call_black_24dp);
                break;

//            case 3:
//                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_apps_black);
//                break;
//            case 4:
//                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_phone_locked_black);
//                break;
//            case 5:
//                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_block_black);
//                break;
            case 3:
                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_camera_black);
                break;
            case 4:
                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_location_black);
                break;
            case 5:
                aboveIcon.setImageResource(pro.kidsgaurd.R.drawable.ic_videocam);
                break;
            case 6:
                aboveIcon.setImageResource(R.drawable.ic_volume_up_black_24dp);
                break;

        }

        LinearLayout linearLayout=(LinearLayout) cardView.findViewById(pro.kidsgaurd.R.id.welcomeLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake = AnimationUtils.loadAnimation(context, pro.kidsgaurd.R.anim.animzoomout);
                cardView.startAnimation(shake);
                android.os.Handler handler=new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (position){
                            case 0:
                                i = new Intent(context,ExplainItemActivity.class);
                                i.putExtra("IntentName","SMS Data");
                                context.startActivity(i);
                                break;
                            case 1:
                                i = new Intent(context,ExplainItemActivity.class);
                                i.putExtra("IntentName","Contact Data");
                                context.startActivity(i);
                                break;
                            case 2:
                                i = new Intent(context,ExplainItemActivity.class);
                                i.putExtra("IntentName","Call Data");
                                context.startActivity(i);
                                break;
                          /*  case 3:

                                Intent i2 = new Intent(context,CurrentAppActivity.class);
                                context.startActivity(i2);
                                break;*/
//                            case 3:
//                                i = new Intent(context,ExplainItemActivity.class);
//                                i.putExtra("IntentName","InstalledPackage Data");
//                                context.startActivity(i);
//                                break;
//                            case 4:
//                                Intent i3 = new Intent(context,PhoneLockActivity.class);
//                                context.startActivity(i3);
//                                break;
//                            case 5:
//                                Intent i4 = new Intent(context,BlockAppActivity.class);
//                                context.startActivity(i4);
//                                break;
                            case 3:
                                Intent i5 = new Intent(context,pictureActivity.class);
                                context.startActivity(i5);
                                break;
                            case 4:
                                RxPermissions rxPermissions=new RxPermissions((FragmentActivity) context);
                                rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION)
                                        .subscribe(granted ->{
                                            if (granted){
                                                dialog = ProgressDialog.show(context, "please wait", "connecting to server...", true);
                                                StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/coordinate-list/",
                                                        new Response.Listener<String>() {
                                                            @TargetApi(Build.VERSION_CODES.O)
                                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                                            @Override
                                                            public void onResponse(String response) {

                                                                dialog.dismiss();
                                                                try {
                                                                    JSONObject jsoncorr=new JSONObject(response);
                                                                    String status=jsoncorr.getString("status");

                                                                    switch (status){
                                                                        case "ok":
                                                                            Log.e("orance", getctoken(context) );
                                                                            JSONArray xaray=jsoncorr.getJSONArray("y");
                                                                            JSONArray yaray=jsoncorr.getJSONArray("x");
                                                                            JSONArray datearray=jsoncorr.getJSONArray("date");
                                                                            ArrayList<String> x=new ArrayList<String>();
                                                                            ArrayList<String> y=new ArrayList<String>();
                                                                            ArrayList<String> date1=new ArrayList<String>();
                                                                            int i=0;
                                                                            while (i<xaray.length()){
                                                                                Log.e("iron", xaray.getString(i) );
                                                                                x.add(xaray.getString(i));
                                                                                y.add(yaray.getString(i));
                                                                                String[] all=datearray.getString(i).split("T");
                                                                                String[] date=all[0].split("-");
                                                                                int year= Integer.parseInt(date[0]);
                                                                                int mounth= Integer.parseInt(date[1]);
                                                                                int day= Integer.parseInt(date[2]);
                                                                                String[] time=all[1].split(":");
                                                                                int hour= Integer.parseInt(time[0]);
                                                                                int min= Integer.parseInt(time[1]);
                                                                                Calendar mCalendar = new GregorianCalendar();
                                                                                mCalendar.set(year,mounth,day,hour,min,00);
                                                                                Calendar.Builder calendar=new Calendar.Builder();
                                                                                calendar.setDate(year,mounth-1,day);
                                                                                calendar.setTimeOfDay(hour,min,0);
                                                                                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                                                                                date1.add(String.valueOf(calendar.build().getTime()));
                                                                                i++;
                                                                            }

                                                                            Intent intent=new Intent(context,MapsActivity.class);
                                                                            Bundle args = new Bundle();
                                                                            args.putSerializable("x",(Serializable)x);
                                                                            args.putSerializable("y",(Serializable)y);
                                                                            args.putSerializable("date",(Serializable)date1);
                                                                            intent.putExtra("BUNDLE",args);
                                                                            context.startActivity(intent);

                                                                            break;
                                                                        default:
                                                                            String message=jsoncorr.getString("message");
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
                                                        Alert.shows(context,"","please check the connetion","ok","");
                                                        SendEror.sender(context,error.toString());


                                                    }

                                                })
                                                {
                                                    @Override
                                                    protected Map<String, String> getParams(){
                                                        Map<String,String> params=new HashMap<String, String>();
                                                       // params.put("parentToken",getowner(context));
                                                        params.put("token",getctoken(context));
                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue=Volley.newRequestQueue(context);
                                                requestQueue.add(stringRequest);
                                            }else {
                                                Alert.shows(context,"","Sorry, we need permission to do this","ok","");
                                            }
                                        });

                                break;
                            case 5:
                                Intent i6 = new Intent(context,RecordVideoActivity.class);
                                context.startActivity(i6);
                                break;
                            case 6:
                                Intent i7 = new Intent(context,RecordVoiceActivity.class);
                                context.startActivity(i7);
                                break;
                        }
                    }
                },1000);

            }
        });
        infsign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        allert(context,"You can see your kid sms text with sender or receiver number and direction");
                        break;
                    case 1:
                        allert(context,"You can see contact numbers and names on your kid device");
                        break;
                    case 2:
                        allert(context,"You can see call logs of your kid device with call number, date, duration and direction");
                        break;
                    /*case 3:
                        allert(context,"You can see and block the application that is running on your kid device right now");
                        break;*/
//                    case 3:
//                        allert(context,"You can see the list of applications that have installed on your kid device");
//                        break;
//                    case 4:
//                        allert(context,"You can lock your kid phone and send message to him/her like: Hi, lunch time");
//                        break;
//                    case 5:
//                        allert(context,"You can block or unblock your kid applications for ever or for specified time, for block app first you should enter time and then activate switch, for unblocking please inactivate it");
//                        break;
                    case 3:
                        allert(context,"If you request for take a picture, we will send a photo of your kid environment after a few minutes when he/she uses the device");
                        break;
                    case 4:
                        allert(context,"You can check your kid location if the gps was activated on your kid device");
                        break;
                    case 5:
                        allert(context,"If you request for capturing video, we will send a video of your kid environment after a few minutes");
                        break;
                    case 6:
                        allert(context,"If you request for capturing voice, we will send a voice of your kid environment after a few minutes");
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Name.size();
    }

    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
    public String getowner(Context context){
        OwnerDataBaseManager owne=new OwnerDataBaseManager(context);
        return owne.getowner();
    }
    public void allert(Context context,String inf){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(inf)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
