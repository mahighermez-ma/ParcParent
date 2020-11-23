package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class StructureActivity extends AppCompatActivity {
    TextView txttitle,txttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_structure);
        txttitle=(TextView)findViewById(pro.kidsgaurd.R.id.title);
        txttext=(TextView)findViewById(pro.kidsgaurd.R.id.textt);
        txttext.setText("1.\tGo to Add kids page and enter your kid name and if you know your kid device IMEI, please enter it\n" +
                "2.\tIf you don’t remember IMEI, please try to get the code\n" +
                "3.\tOpen the kid's app then choose your options, if you enter IMEI now tap on Active by IMEI, otherwise if you get code, now enter it and tap on Active by code\n" +
                "4.\tSetup is finished on your kid device\n" +
                "5.\tNow open the parent app and select your kid name\n" +
                "6.\tChoose the option\n" +
                "7.\tTo change your option please go to the call page on your kid device and call the code then Add kid.\n");
        Animation a = AnimationUtils.loadAnimation(StructureActivity.this, pro.kidsgaurd.R.anim.animzoomin);
        a.reset();
        txttitle.clearAnimation();
        txttitle.startAnimation(a);
        txttext.clearAnimation();
        txttext.startAnimation(a);
    }
    public void btnApp(View view){
        Intent intent=new Intent(StructureActivity.this,getChildActivity.class);
        intent.putExtra("activity","first");
        startActivity(intent);
    }
}
