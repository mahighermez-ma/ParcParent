package pro.kidsgaurd;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_main2);
        AlertDialog.Builder alertClose=new AlertDialog.Builder(Main2Activity.this);
        alertClose.setTitle("Privacy Policy").
                setMessage("At first,please study our privacy policy then if you are agree use the app").
                setPositiveButton("Studying Privacy Policy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUrl("https://policy.kidsguard.pro/privacy");

                    }
                }).
                setNegativeButton("I have Studied", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public void btparent(View view){
        Intent intent=new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);
    }
    public void btchild(View view){
        AlertDialog.Builder alertClose=new AlertDialog.Builder(Main2Activity.this);
        alertClose.setTitle("Warnning").
                setMessage("Please download Kids Guard[for kids] from Google play").
                setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUrl("https://play.google.com/store/apps/details?id=pro.kds.forkids.kidsguard");

                    }
                }).show();

    }
    public void bthelp(View view){
        Intent intent=new Intent(Main2Activity.this,HelpingActivity.class);
        startActivity(intent);
    }
}
