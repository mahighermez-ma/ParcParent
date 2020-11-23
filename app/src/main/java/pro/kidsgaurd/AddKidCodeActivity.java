package pro.kidsgaurd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AddKidCodeActivity extends AppCompatActivity {

    TextView txtAddkidCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kid_code);

        txtAddkidCode = findViewById(R.id.txtAddKidCode);

        Intent intent = getIntent();
        String txtCode = "Your code is: " + intent.getStringExtra("StringAddKidCode");
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), pro.kidsgaurd.R.anim.animzoomin);
        a.reset();
        txtAddkidCode.clearAnimation();
        txtAddkidCode.startAnimation(a);
        txtAddkidCode.setText(txtCode);


    }
    public void btnMyKid(View view){
        startActivity(new Intent(getApplicationContext(),getChildActivity.class));
    }
}
