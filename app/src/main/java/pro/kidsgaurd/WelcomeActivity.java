package pro.kidsgaurd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;


public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pro.kidsgaurd.R.layout.activity_welcome);

        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(pro.kidsgaurd.R.id.toolbarNavBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(pro.kidsgaurd.R.id.drawer_layout);
        NavigationView navigationView = findViewById(pro.kidsgaurd.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, pro.kidsgaurd.R.string.navigation_drawer_open, pro.kidsgaurd.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ArrayList<String> name=new ArrayList<String>();
        name.add("Sms");
        name.add("Contacts");
        name.add("Calls");
//        name.add("All Apps");
//        name.add("Lock phone");
//        name.add("Block apps");
        name.add("Take picture");
        name.add("Location");
        name.add("Take video");
        name.add("Take voice");
        RecyclerView recyclerViewwelcome=(RecyclerView)findViewById(pro.kidsgaurd.R.id.recyclerViewwelcone);
        RecyclerViewAdapterWelcome adapter = new RecyclerViewAdapterWelcome(name, WelcomeActivity.this);
        recyclerViewwelcome.setAdapter(adapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(WelcomeActivity.this, pro.kidsgaurd.R.anim.layout_animation_fall_down);
        recyclerViewwelcome.setLayoutAnimation(animation);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewwelcome.setLayoutManager(layoutManager);
    }

 @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(pro.kidsgaurd.R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.search_action:
                // Here's the code
                return true;
            case R.id.report_action:
                //Hear's the code
                return true;
            case R.id.setting_action:
                //Hear's the code
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case pro.kidsgaurd.R.id.nav_addChild:
                Intent b1 = new Intent(this ,AddChildActivity.class);
                b1.putExtra("activity","welcome");
                startActivity(b1);
                break;
            case pro.kidsgaurd.R.id.nav_otherChild:
                Intent b2 = new Intent(this ,getChildActivity.class);
                b2.putExtra("activity","welcome");
                startActivity(b2);
                break;
            case pro.kidsgaurd.R.id.nav_aboutApp:
                Intent b4 = new Intent(this ,AboutAppActivity.class);
                startActivity(b4);
                break;
            case pro.kidsgaurd.R.id.nav_Exit:
                OwnerDataBaseManager ownerDataBaseManager=new OwnerDataBaseManager(WelcomeActivity.this);
                ownerDataBaseManager.delall();
                CtokenDataBaseManager ctok=new CtokenDataBaseManager(WelcomeActivity.this);
                ctok.delall();
                Intent b5 = new Intent(this,MainActivity.class);
                startActivity(b5);
                break;

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

      /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder alertClose=new AlertDialog.Builder(WelcomeActivity.this);
        alertClose.setTitle(pro.kidsgaurd.R.string.titleExitConfirm).
                setMessage(pro.kidsgaurd.R.string.bodyExitConfirm).
                setPositiveButton(pro.kidsgaurd.R.string.acceptExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(WelcomeActivity.this,VoroodActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT",true);
                        startActivity(intent);
                    }
                }).
                setNegativeButton(pro.kidsgaurd.R.string.declineExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }
    public String getctoken(Context context){
        CtokenDataBaseManager ctok=new CtokenDataBaseManager(context);
        return ctok.getctoken();
    }
    public String getowner(Context context){
        OwnerDataBaseManager owne=new OwnerDataBaseManager(context);
        return owne.getowner();
    }
}