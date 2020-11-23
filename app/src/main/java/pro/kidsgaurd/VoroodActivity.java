package pro.kidsgaurd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class VoroodActivity extends AppCompatActivity {
    int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_vorood);
        if (getIntent().getBooleanExtra("EXIT",false)){
            VoroodActivity.this.finish();
            System.exit(0);
        }
        activity=1;
        final SharedPreferences shared=getSharedPreferences("prefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor=shared.edit();
        boolean isFirstRun=shared.getBoolean("FIRSTRUN",true);
        if (isFirstRun){
            activity=2;

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (activity==1){
                    CtokenDataBaseManager ctoken=new CtokenDataBaseManager(VoroodActivity.this);
                    if (ctoken.getctoken().equals("12")){
                        Intent intent=new Intent(VoroodActivity.this,Main2Activity.class);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(VoroodActivity.this,WelcomeActivity.class);
                        startActivity(intent);}
                }else {
                    Intent intent=new Intent(VoroodActivity.this,Main2Activity.class);
                    startActivity(intent);
                }
            }
        },3400);
    }
}
