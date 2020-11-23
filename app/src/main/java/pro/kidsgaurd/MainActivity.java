package pro.kidsgaurd;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class

MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, TabLayout.BaseOnTabSelectedListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_main);
        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(pro.kidsgaurd.R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(pro.kidsgaurd.R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(pro.kidsgaurd.R.id.pager);

        //Creating our pager adapter
        pager adapter = new pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);


        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

