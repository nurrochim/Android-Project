package com.rohim.jasaservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.rohim.common.DatabaseHelper;
import com.rohim.common.PopupNotification;
import com.rohim.fragment.AddTriggerJasaService;
import com.rohim.fragment.BeforeLoginNotification;
import com.rohim.fragment.FragmentBeforeLogin;
import com.rohim.fragment.FragmentHistory;
import com.rohim.fragment.FragmentInsertAccount;
import com.rohim.fragment.FragmentMenuJasaService;
import com.rohim.fragment.FragmentRequestActiveTask;
import com.rohim.fragment.FragmentRequestOrderActive;
import com.rohim.fragment.FragmentUpdateAccount;
import com.rohim.modal.DropDownList;
import com.rohim.modal.HistoryRequest;
import com.rohim.modal.ReasonList;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Fragment> listFragments;
    FragmentManager fragmentManager;
    public static NavigationView navigationView;
    public static TextView textTitle, textUserName, textEmail;
    public static DrawerLayout drawer;
    ImageView btnMenu;
    User user = null;

    public Dao<Service, String> serviceDao = null;
    public Dao<ServiceItem, Integer> serviceItemDao = null;
    public Dao<DropDownList, Integer> dropDownListDao = null;
    public Dao<ServiceProvide, String> serviceProvideDao = null;
    public Dao<RequestDetail, String> requestDetailDao = null;
    public Dao<RequestAccepted, String> requestAcceptedDao = null;
    public Dao<RequestOrder, String> requestOrderDao = null;
    public Dao<HistoryRequest, String> historyRequestDao = null;
    public Dao<ReasonList, Integer> reasonDao = null;
    public Dao<User, String> userDao = null;
    public DatabaseHelper dbh ;
    public SQLiteDatabase db;
    String idUser = "";
    boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textTitle = (TextView) findViewById(R.id.text_title);
        btnMenu = (ImageView)  findViewById(R.id.image_menu);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        textUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_user_name_slide);
        textEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.text_user_email_slide);

        // load user
        loadUser();

//        String token = FirebaseInstanceId.getInstance().getId();
//        Log.d("Firebase", "Refreshed token: " + token);
//        token = FirebaseInstanceId.getInstance().getId();
//        Log.d("Firebase", "Refreshed token: " + token);

        // List Fragment
        listFragments = new ArrayList<Fragment>();
        listFragments.add(new FragmentBeforeLogin());
        listFragments.add(new AddTriggerJasaService());
        listFragments.add(new FragmentMenuJasaService());

        fragmentManager = getSupportFragmentManager();
        //user.setJenisUser("Pencari Jasa");
        // load first fragment as default:
        if(user!=null){
            if(user.getJenisUser().equals("Penyedia Jasa")){
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, listFragments.get(1)).commit();
                rebuildMenuView(true);
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, listFragments.get(2)).commit();
                rebuildMenuView(false);
            }
            isLogin = true;
        }
        if(idUser.isEmpty()){
            isLogin = false;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, listFragments.get(0)).commit();
            rebuildMenuBeforeLogin();
        }


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(Gravity.LEFT);
        //        databaseInit();
            }
        });

        // database init
        databaseInit();

        // get intent extra for handle notification
        if (getIntent().hasExtra("msgId")) {
            String msgId = getIntent().getStringExtra("msgId");
            if(msgId.equals("Konfirmasi Pendaftaran")){
                PopupNotification popupNotification = new PopupNotification();
                popupNotification.setParam(getApplicationContext(), getIntent().getStringExtra("msgTitle"),getIntent().getStringExtra("msgBody"), false);
                popupNotification.show(fragmentManager, "");
            }
            if(msgId.equals("RO")){
                if (getIntent().hasExtra("idRequest")) {
                    FragmentRequestActiveTask activeTask= new FragmentRequestActiveTask();
                    activeTask.setMsgId(msgId);
                    activeTask.setIdRequest(getIntent().getStringExtra("idRequest"));
                    activeTask.setIdUserAccepted(getIntent().getStringExtra("idUserAccepted"));
                    fragmentManager.beginTransaction().replace(R.id.content_main, activeTask).commit();
                }
            }
            if(msgId.equals("PROCESS")){
                FragmentRequestOrderActive activeOrder= new FragmentRequestOrderActive();
                activeOrder.setMsgId(msgId);
                activeOrder.setIdRequest(getIntent().getStringExtra("idRequest"));
                activeOrder.setIdUserAccepted(getIntent().getStringExtra("idUserAccepted"));
                activeOrder.setUserNameAccepted(getIntent().getStringExtra("userNameAccepted"));
                activeOrder.setUserNoTelp(getIntent().getStringExtra("userNoTelp"));
                activeOrder.setMsgTitle(getIntent().getStringExtra("msgTitle"));
                activeOrder.setMsgBody(getIntent().getStringExtra("msgBody"));
                fragmentManager.beginTransaction().replace(R.id.content_main, activeOrder).commit();
            }
            if(msgId.equals("CANCEL1")){
                FragmentRequestOrderActive activeOrder= new FragmentRequestOrderActive();
                activeOrder.setMsgId(msgId);
                activeOrder.setMsgTitle(getIntent().getStringExtra("msgTitle"));
                activeOrder.setMsgBody(getIntent().getStringExtra("msgBody"));
                activeOrder.setIdRequest(getIntent().getStringExtra("idRequest"));
                activeOrder.setIdUserAccepted("");
                activeOrder.setUserNameAccepted("Menunggu Process System");
                activeOrder.setUserNoTelp("");
                fragmentManager.beginTransaction().replace(R.id.content_main, activeOrder).commit();
            }
            if(msgId.equals("FINISH")){
                FragmentRequestOrderActive activeOrder= new FragmentRequestOrderActive();
                activeOrder.setMsgId(msgId);
                activeOrder.setMsgTitle(getIntent().getStringExtra("msgTitle"));
                activeOrder.setMsgBody(getIntent().getStringExtra("msgBody"));
                activeOrder.setIdRequest(getIntent().getStringExtra("idRequest"));
                fragmentManager.beginTransaction().replace(R.id.content_main, activeOrder).commit();
            }
            if(msgId.equals("CANCEL2")){
                FragmentRequestActiveTask activeTask = new FragmentRequestActiveTask();
                activeTask.setMsgId(msgId);
                activeTask.setMsgTitle(getIntent().getStringExtra("msgTitle"));
                activeTask.setMsgBody(getIntent().getStringExtra("msgBody"));
                activeTask.setIdRequest(getIntent().getStringExtra("idRequest"));
                fragmentManager.beginTransaction().replace(R.id.content_main, activeTask).commit();
            }
            if(msgId.equals("COMMENT")){
                FragmentRequestActiveTask activeTask = new FragmentRequestActiveTask();
                activeTask.setMsgId(msgId);
                activeTask.setMsgTitle(getIntent().getStringExtra("msgTitle"));
                activeTask.setMsgBody(getIntent().getStringExtra("msgBody"));
                activeTask.setIdRequest(getIntent().getStringExtra("idRequest"));
                activeTask.setMsgHasilService(getIntent().getStringExtra("msgUlasan"));
                activeTask.setMsgComment(getIntent().getStringExtra("msgComent"));
                fragmentManager.beginTransaction().replace(R.id.content_main, activeTask).commit();
            }
        }
        // jika di buka dari Penyedia Jasa
        // jika ada New Task maka screen yang pertama muncul adalah Accept/Ignore


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
            if(isLogin){
                fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentRequestActiveTask()).commit();
            }else{
                popupNotificationShow("Belum Ada Active Task", false);
            }
        }else if(id == R.id.nav_history){
            textTitle.setText("History");
            if(isLogin){
                fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentHistory()).commit();
            }else{
                popupNotificationShow("Belum Ada History", false);
            }
        }else if(id == R.id.nav_active_order){
            textTitle.setText("Active Order");
            if(isLogin){
                fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentRequestOrderActive()).commit();
            }else{
                popupNotificationShow("Belum Ada Active Order", false);
            }
        }else if(id == R.id.nav_jasa){
            textTitle.setText("Penyedia Jasa");
            fragmentManager.beginTransaction().replace(R.id.content_main,new AddTriggerJasaService()).commit();
        }else if(id == R.id.nav_logout) {
            textTitle.setText("Logout");
            if(isLogin){
                //fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentLogout()).commit();
                popupNotificationShow("Logout", true);
            }else{
                popupNotificationShow("Logout", false);
            }

        }else if(id == R.id.nav_account) {
            textTitle.setText("Account");
            if(isLogin){
                fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentUpdateAccount()).commit();
            }else{
                fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentInsertAccount()).commit();
            }
        }else if(id == R.id.nav_cari_jasa) {
            textTitle.setText("Cari Jasa Service");
            fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentMenuJasaService()).commit();
        }else if(id == R.id.nav_menu_utama) {
            fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentBeforeLogin()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void popupNotificationShow(String title, boolean isLogout){
        BeforeLoginNotification beforeLoginNotification = new BeforeLoginNotification();
        beforeLoginNotification.setTitle(title);
        beforeLoginNotification.setLogout(isLogout);
        fragmentManager.beginTransaction().replace(R.id.content_main,beforeLoginNotification).commit();
    }

    private void loadUser() {
        dbh = new DatabaseHelper(getApplicationContext());
        // load list view
        try {
            SharedPreferences prefs = getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorSharedPreference  = prefs.edit();
//            idUser = prefs.getString("IdUser","");

//            if(!idUser.isEmpty()) {
                // create conection db
                userDao = dbh.getUserDao();

                //List<User> listUser = userDao.queryForEq(User.clm_id_user, idUser);
                List<User> listUser = userDao.queryForAll();
                if (listUser != null && listUser.size()>0) {
                    user = listUser.get(0);
                    textUserName.setText(user.getUserName());
                    editorSharedPreference.putString("IdUser", user.getIdUser());
                    idUser = user.getIdUser();
                }
                // Close DB Conection
                dbh.close();
  //          }
        } catch (SQLException e) {
            Log.e("Error",  e.toString());
        }catch (Exception e) {
            Log.e("Error", e.toString());
        } finally {
            dbh.close();
        }

    }

    public void rebuildMenuView(Boolean isPenyediaJasa){
        navigationView.getMenu().getItem(2).setVisible(isPenyediaJasa);
        navigationView.getMenu().getItem(3).setVisible(isPenyediaJasa);
        navigationView.getMenu().getItem(4).setVisible(!isPenyediaJasa);
        navigationView.getMenu().getItem(5).setVisible(!isPenyediaJasa);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(0).setVisible(false);
    }

    public void rebuildMenuBeforeLogin(){
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        navigationView.getMenu().getItem(5).setVisible(false);
        navigationView.getMenu().getItem(6).setVisible(false);

    }

    public void databaseInit(){
        dbh = new DatabaseHelper(getApplicationContext());
        db = dbh.getWritableDatabase();
        ConnectionSource cs = dbh.getConnectionSource();

        try {
            // getCount
            long countDataService, countDataServiceItem, countDropdown = 0;
            serviceDao = dbh.getServiceDao();
            serviceItemDao = dbh.getServiceItemDao();
            dropDownListDao = dbh.getDropDownListDao();
            serviceProvideDao = dbh.getServiceProvideDao();
            requestDetailDao = dbh.getRequestDetailDao();
            requestAcceptedDao = dbh.getRequestAcceptedDao();
            requestOrderDao = dbh.getRequestOrderDao();
            historyRequestDao = dbh.getHistoryRequestDao();
            reasonDao = dbh.getReasonDao();
            userDao = dbh.getUserDao();

            if(!serviceDao.isTableExists()){
                TableUtils.createTable(cs, Service.class);
            }
            if(!serviceItemDao.isTableExists()){
                TableUtils.createTable(cs, ServiceItem.class);
            }
            if(!dropDownListDao.isTableExists()){
                TableUtils.createTable(cs, DropDownList.class);
            }
            if(!requestOrderDao.isTableExists()){
                TableUtils.createTable(cs, RequestOrder.class);
            }
            if(!requestAcceptedDao.isTableExists()){
                TableUtils.createTable(cs, RequestAccepted.class);
            }
            if(!requestDetailDao.isTableExists()){
                TableUtils.createTable(cs, RequestDetail.class);
            }
            if(!historyRequestDao.isTableExists()){
                TableUtils.createTable(cs, HistoryRequest.class);
            }
            if(!reasonDao.isTableExists()){
                TableUtils.createTable(cs, ReasonList.class);
            }
            if(!userDao.isTableExists()){
                TableUtils.createTable(cs, User.class);
            }


            SQLiteStatement statementCount = db.compileStatement(" SELECT COUNT(*) FROM "+Service.tbl_service);
            countDataService = statementCount.simpleQueryForLong();

            statementCount = db.compileStatement(" SELECT COUNT(*) FROM "+ServiceItem.tbl_service_item);
            countDataServiceItem = statementCount.simpleQueryForLong();

            statementCount = db.compileStatement(" SELECT COUNT(*) FROM "+DropDownList.tbl_dropDown_list);
            countDropdown = statementCount.simpleQueryForLong();

            if(countDataService == 0){
                dbh.AddDataService();
            }
            if(countDataServiceItem == 0){
                db.execSQL("DROP TABLE IF EXISTS "+ ServiceItem.tbl_service_item);
                TableUtils.createTable(cs, ServiceItem.class);
                dbh.AddDataServiceItem();
            }
            if(countDropdown == 0){
                db.execSQL("DROP TABLE IF EXISTS "+ DropDownList.tbl_dropDown_list);
                TableUtils.createTable(cs, DropDownList.class);
                dbh.addDataDropdown();
            }


            statementCount.close();
            db.close();
            cs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
