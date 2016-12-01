package com.rohim.jasaservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.rohim.fragment.AddTriggerJasaService;
import com.rohim.fragment.BeforeLoginNotification;
import com.rohim.fragment.FragmentBeforeLogin;
import com.rohim.fragment.FragmentInsertAccount;
import com.rohim.fragment.FragmentMenuJasaService;
import com.rohim.fragment.FragmentRequestOrderActive;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Fragment> listFragments;
    FragmentManager fragmentManager;
    public static NavigationView navigationView;
    public static TextView textTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textTitle = (TextView) findViewById(R.id.text_title);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        String token = FirebaseInstanceId.getInstance().getId();
//        Log.d("Firebase", "Refreshed token: " + token);
//        token = FirebaseInstanceId.getInstance().getId();
//        Log.d("Firebase", "Refreshed token: " + token);

        // List Fragment
        listFragments = new ArrayList<Fragment>();
        listFragments.add(new FragmentBeforeLogin());

        // load first fragment as default:
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, listFragments.get(0)).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 0);

        }else if(id == R.id.nav_active_task){
            textTitle.setText("Active Task");
            popupNotificationShow("Belum Ada Active Task");
        }else if(id == R.id.nav_history){
            textTitle.setText("History");
            popupNotificationShow("Belum Ada History");
        }else if(id == R.id.nav_active_order){
            textTitle.setText("Active Order");
            //popupNotificationShow("Belum Ada Active Order");
            fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentRequestOrderActive()).commit();
        }else if(id == R.id.nav_jasa){
            textTitle.setText("Penyedia Jasa");
            fragmentManager.beginTransaction().replace(R.id.content_main,new AddTriggerJasaService()).commit();
        }else if(id == R.id.nav_logout) {
            textTitle.setText("Logout");
            popupNotificationShow("Logout");
        }else if(id == R.id.nav_account) {
            textTitle.setText("Account");
            fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentInsertAccount()).commit();
        }else if(id == R.id.nav_cari_jasa) {
            textTitle.setText("Cari Jasa Service");
            fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentMenuJasaService()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void popupNotificationShow(String title){
        BeforeLoginNotification beforeLoginNotification = new BeforeLoginNotification();
        beforeLoginNotification.setTitle(title);
        fragmentManager.beginTransaction().replace(R.id.content_main,beforeLoginNotification).commit();
    }
}
