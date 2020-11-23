package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SmsVerActivity extends AppCompatActivity {
    EditText edtcod;
    public int second = 60;
    public boolean run;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null)
            second = savedInstanceState.getInt("second");
        setContentView(pro.kidsgaurd.R.layout.activity_sms_ver);
        edtcod=(EditText)findViewById(pro.kidsgaurd.R.id.edtvercode);
        run=true;
        runTimer();


    }
    public void btnsabt(View view){
        Intent intent=getIntent();
       // SmsVerClass.smver(SmsVerActivity.this,intent.getStringExtra("mobile"),edtcod.getText().toString());


    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                TextView txtTime = (TextView)findViewById(pro.kidsgaurd.R.id.txtTimer_verSMS);
                int minute = second/60;
                int sec = second%60;
                String time = String.format("%02d:%02d",minute,sec);
                txtTime.setText(time);

                if (run && second>0)
                {
                    second--;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("second", second);
        super.onSaveInstanceState(outState);
    }
}
