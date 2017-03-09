package com.rohim.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.rohim.adapter.NothingSelectedSpinnerAdapter;
import com.rohim.adapter.RecycleViewListAdapter;
import com.rohim.asyncTaskServer.addServiceProvidesToServer;
import com.rohim.asyncTaskServer.getServiceProvideFromServer;
import com.rohim.common.BaseFragment;
import com.rohim.common.DatabaseHelper;
import com.rohim.common.PopupNotification;
import com.rohim.jasaservice.R;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceProvide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class AddTriggerJasaService extends BaseFragment{
    Button btnTambahJasa, btnSubmit;
    private static Spinner spinnerJasaService;
    private static ListView listview;
    private static RecycleViewListAdapter adapterListView;
    public static List<ServiceProvide> data = new ArrayList<>();
    private static Context contexts;
    String selectedJasa = "";
    Boolean initSpinner = false;
    Boolean addJasaFromSpinner = true;
    public PopupNotification popupNotification;
    public FragmentManager fragmentManager;
    private static DatabaseHelper dbhStatic ;
    private static Activity activity;
    private static String ipserver;

    private static String idUser;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.tambah_jasa, container, false);
        btnTambahJasa = (Button) view.findViewById(R.id.btn_tambah_jasa_service);
        btnSubmit = (Button) view.findViewById(R.id.btn_save_tambah_jasa_service);
        contexts = getContext();
        fragmentManager= getFragmentManager();
        activity = getActivity();
        ipserver = ipServer;

        idUser = sharedPreference.getString("IdUser","");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUser.isEmpty()) {
                    popupNotification.show(fragmentManager, "");
                } else {
                    Gson gson = new Gson();
                    String listServiceProvidesJson = "{serviceProvides : "+gson.toJson(data)+"}";
                    addServiceProvidesToServer addUser = new addServiceProvidesToServer();
                    addUser.setIpServer(ipServer);
                    addUser.setActivity(getActivity());
                    addUser.setListServiceProvides(listServiceProvidesJson);
                    addUser.setContext(getContext());
                    addUser.execute();

                }
            }
        });
        btnTambahJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadAdapterSpinner();
                spinnerJasaService.performClick();
//                writeDBToSDCard();
            }
        });

        loadInit();
        refreshAdapter();
        loadDataServiceProvideFromServer();
    }

    public void loadInit(){
        // load data from db
        final List<Service> listService = getDataService();

        // load jasa service
        List<String> jasaService = new ArrayList<>();
        for(Service service : listService) {
            jasaService.add(service.getServiceName());
        }

        spinnerJasaService = (Spinner) view.findViewById(R.id.spinner_jasa_service);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                view.getContext(), android.R.layout.simple_spinner_item, jasaService);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerJasaService.setAdapter(new NothingSelectedSpinnerAdapter(adapter,R.layout.spinner_not_selected,getContext()));
        spinnerJasaService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                addJasaFromSpinner = true;
                if(initSpinner == false){
                    initSpinner = true;
                }else {
                    selectedJasa = parent.getItemAtPosition(position).toString();
                    for (ServiceProvide jasa : data){
                        if(jasa.getServiceName().equalsIgnoreCase(selectedJasa)){
                            addJasaFromSpinner = false;
                            CharSequence text = "Item sudah ada dalam daftar";
                            Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(addJasaFromSpinner) {
                        ServiceProvide item = new ServiceProvide();
                        item.setServiceName(selectedJasa);
                        item.setFidService(listService.get(position-1).getIdService());
                        item.setIdServiceProvide(idUser+"/"+item.getFidService()+"/"+selectedJasa);
                        item.setFidUser(idUser);
                        // save data
                        openDatabaseHelper();
                        try {
                            serviceProvideDao.create(item);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            dbh.close();
                            refreshAdapter();
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // load list view
        listview = (ListView) view.findViewById(R.id.list_view_jasa);
        adapterListView = new RecycleViewListAdapter(getContext(), data, spinnerJasaService, getActivity(), ipServer);
        listview.setAdapter(adapterListView);
    }

    public static void refreshAdapter(){
        try {
            dbhStatic = new DatabaseHelper(activity);
            Dao<ServiceProvide, String>  staticServiceProvideDao = dbhStatic.getServiceProvideDao();
            data = staticServiceProvideDao.queryBuilder().where().eq(ServiceProvide.clm_fid_user,idUser).query();
            adapterListView = new RecycleViewListAdapter(contexts, data, spinnerJasaService, activity, ipserver);
            listview.setAdapter(adapterListView);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbhStatic.close();
        }
    }


    private List<Service> getDataService() {
        List<Service> listService = new ArrayList<>();
        try {
            openDatabaseHelper();
            listService = serviceDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbh.close();
        }
        return listService;
    }

    private void loadDataServiceProvideFromServer(){
        if(data.size()==0){
            getServiceProvideFromServer service = new getServiceProvideFromServer();
            service.setIpServer(ipServer);
            service.setActivity(getActivity());
            service.setIdUser(idUser);
            service.setContext(getContext());
            try {
                service.execute().get();
            } catch (InterruptedException e) {
                Log.e("Error", e.toString());
            } catch (ExecutionException e) {
                Log.e("Error", e.toString());
            }finally {
                if(!service.getStatus().equals(AsyncTask.Status.FINISHED)){
                    //data = service.getListServiceProvide();
                    for(ServiceProvide serviceProvide:service.getListServiceProvide()){
                        openDatabaseHelper();
                        try {
                            serviceProvideDao.create(serviceProvide);
                        } catch (SQLException e) {
                            Log.e("Error", e.toString());
                        }
                    }
                    refreshAdapter();
                }
            }
        }
    }
//
//    private void AddJasaServiceData(){
//        List<Service> listService = new ArrayList<>();
//        try {
////            db.execSQL("DROP TABLE IF EXISTS "+Service.tbl_service);
//            db.execSQL("DROP TABLE IF EXISTS "+ ServiceItem.tbl_service_item);
//            db.execSQL("DROP TABLE IF EXISTS "+ DropDownList.tbl_dropDown_list);
////            db.execSQL("DROP TABLE IF EXISTS "+ User.tbl_user);
////            db.execSQL("DROP TABLE IF EXISTS "+ ServiceProvide.tbl_service_provice);
//
//            //db.execSQL("DELETE "+Service.tbl_service);
//            //db.execSQL("DELETE FROM "+ ServiceItem.tbl_service_item);
//            //db.execSQL("DELETE "+ DropDownList.tbl_dropDown_list);
//            //db.close();
//            //if(!serviceDao.isTableExists()){
//                dbh.onCreate(db);
//            //    dbh.close();
//            //}
////            listService = serviceDao.queryForAll();
////            serviceDao.delete(listService);
//
//            // add sample data
////            dbh.AddDataService();
//            dbh.AddDataServiceItem();
//            dbh.addDataDropdown();
//
//            //listService = serviceDao.queryForAll();
//            CharSequence text = "Yey... berhasil add data";
//            Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        for (Service services:listService) {
//            jasaService.add(services.getServiceName());
//        }




    List<String> jasaService = new ArrayList<>();
    public void loadAdapterSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                view.getContext(), android.R.layout.simple_spinner_item, jasaService);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerJasaService.setAdapter(new NothingSelectedSpinnerAdapter(adapter,R.layout.spinner_not_selected,getContext()));

    }

    private void writeDBToSDCard(){
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getContext().getPackageName()+"//databases//JS.db";
                String backupDBPath = "JS_BackUp.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }
}
