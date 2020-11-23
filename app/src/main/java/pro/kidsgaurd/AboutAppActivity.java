package pro.kidsgaurd;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AboutAppActivity extends AppCompatActivity {
    TextView txtTeamapp,txtdescapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_aboutapp);
        txtTeamapp=(TextView)findViewById(pro.kidsgaurd.R.id.txtTeamapp);
        txtdescapp=(TextView)findViewById(pro.kidsgaurd.R.id.txtdescapp);

        Animation a = AnimationUtils.loadAnimation(AboutAppActivity.this, pro.kidsgaurd.R.anim.animzoomin);
        a.reset();
        txtTeamapp.clearAnimation();
        txtTeamapp.startAnimation(a);
        txtdescapp.clearAnimation();
        txtdescapp.startAnimation(a);
        txtTeamapp.setText("This app designed for parents that want to protect their child against hurts due to phone device. They \n" +
                "can check SMS, contacts, calls or will be able to block device apps, lock phone and take a picture \n" +
                "from their child present environment by the back or front device's camera. They can also find their child's \n" +
                "location.\n" +
                "You can access your kid's phone by just one click.\n" +
                "You can use this app for your other kids with our instruction:\n" +
                "1.Add your kid in add kid page and if you don’t have Kids Guard KD app download and install it on your kids' device\n" +
                "2.Use the code or IMEI to activate the kid's device with every item you want to check it\n" +
                "3.Open this app and choose your kid name form your kids' list\n" +
                "4.Use the options that you had already chosen\n" +
                "\n");

    }
}
