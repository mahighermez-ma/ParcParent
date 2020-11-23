package pro.kidsgaurd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class imageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent=getIntent();
        Bitmap bitmap = Utils2.getBitmap(intent.getStringExtra("image"));
        ImageView imageView=(ImageView)findViewById(R.id.imgshow);
        imageView.setImageBitmap(bitmap);
        Animation zoom = AnimationUtils.loadAnimation(imageActivity.this, R.anim.animzoomin);
        imageView.startAnimation(zoom);
    }
}
