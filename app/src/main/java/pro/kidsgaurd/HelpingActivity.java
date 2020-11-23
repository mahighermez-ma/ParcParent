package pro.kidsgaurd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.concurrent.ScheduledExecutorService;

public class HelpingActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,BlankFragment2.OnFragmentInteractionListener,BlankFragment3.OnFragmentInteractionListener,BlankFragment4.OnFragmentInteractionListener,BlankFragment5.OnFragmentInteractionListener,BlankFragment6.OnFragmentInteractionListener,BlankFragment7.OnFragmentInteractionListener,BlankFragment8.OnFragmentInteractionListener,BlankFragment9.OnFragmentInteractionListener,BlankFragment10.OnFragmentInteractionListener,BlankFragment11.OnFragmentInteractionListener{
    ScheduledExecutorService scheduledExecutorService;
    private ViewPager viewPager;
    private static final int NUM_PAGES = 5;
    private int dotscount;
    private ImageView[] dots;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_helping);
        //startService(new Intent(this,MyService.class));
        LinearLayout sliderDotspanel = (LinearLayout) findViewById(pro.kidsgaurd.R.id.SliderDots);
        mPager = (ViewPager) findViewById(pro.kidsgaurd.R.id.pager);
        HelpingPager sc=new HelpingPager(getSupportFragmentManager(), 11);
        mPager.setAdapter(sc);
        dotscount = sc.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), pro.kidsgaurd.R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), pro.kidsgaurd.R.drawable.active_dot));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), pro.kidsgaurd.R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), pro.kidsgaurd.R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });}

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void btnSkip(View view){
        startActivity(new Intent(HelpingActivity.this,Main2Activity.class));
    }
}
