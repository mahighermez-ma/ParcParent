package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class blockTimeActivity extends AppCompatActivity {
    EditText edtftime,edtstime;
    String appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_block_time);
        edtftime=(EditText)findViewById(pro.kidsgaurd.R.id.edtftime);
        edtstime=(EditText)findViewById(pro.kidsgaurd.R.id.edtstime);
        Intent intent=getIntent();
        appname=intent.getStringExtra("appname");

    }
    public void btnsendblock(View view){
        String ftime=edtftime.getText().toString();
        String stime=edtstime.getText().toString();
        blockAppdb blockAppdb=new blockAppdb(blockTimeActivity.this);
        blockAppdb.Insertjs(appname+":"+"1"+":"+ftime+":"+stime);


    }
}
