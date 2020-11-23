package pro.kidsgaurd;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneLockActivity extends AppCompatActivity {
    EditText edttitle,edtText;
    TextInputLayout inputLayouttitle, inputLayouttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_phonelock);
        edttitle=(EditText)findViewById(pro.kidsgaurd.R.id.edttitle);
        edtText=(EditText)findViewById(pro.kidsgaurd.R.id.edtText);
        inputLayouttitle = (TextInputLayout)findViewById(pro.kidsgaurd.R.id.inputTextPhoneLogin);
        inputLayouttext = (TextInputLayout)findViewById(pro.kidsgaurd.R.id.inputTextPassLogin);

    }
    public void btnlockPhone(View view){
        String title=edttitle.getText().toString();
        String text=edtText.getText().toString();
        InsSub insSub=new InsSub();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("title",title);
            jsonObject.put("message",text);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String lock= String.valueOf(jsonObject);
        insSub.insLock(PhoneLockActivity.this,lock);
        Alert.shows(PhoneLockActivity.this,"","If the kid's phone has connected to the internet, it will lock after a minute.","ok","");
        Animation shake = AnimationUtils.loadAnimation(PhoneLockActivity.this, pro.kidsgaurd.R.anim.animzoomout);
        view.startAnimation(shake);
    }
    public void btnUnlockphone(View view){
        InsSub insSub=new InsSub();
        insSub.insLock(PhoneLockActivity.this,"false");
        Alert.shows(PhoneLockActivity.this,"","If the kid's phone has connected to the internet, it will unlock after a minute.","ok","");

        Animation shake = AnimationUtils.loadAnimation(PhoneLockActivity.this, pro.kidsgaurd.R.anim.animzoomout);
        view.startAnimation(shake);
    }
    public void edttitle(View view){
        Animation shake = AnimationUtils.loadAnimation(PhoneLockActivity.this, pro.kidsgaurd.R.anim.animzoomin);
        view.startAnimation(shake);
    }
    public void edtText(View view){
        Animation shake = AnimationUtils.loadAnimation(PhoneLockActivity.this, pro.kidsgaurd.R.anim.animzoomin);
        view.startAnimation(shake);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }
}
