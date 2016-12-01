package com.rohim.fragment;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.rohim.adapter.NothingSelectedSpinnerAdapter;
import com.rohim.adapter.RecycleViewListAdapter;
import com.rohim.common.BaseFragment;
import com.rohim.common.PopupNotification;
import com.rohim.jasaservice.R;
import com.rohim.modal.DropDownList;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class AddTriggerJasaService extends BaseFragment{
    Button btnTambahJasa, btnSave;
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

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.tambah_jasa, container, false);
        btnTambahJasa = (Button) view.findViewById(R.id.btn_tambah_jasa_service);
        btnSave = (Button) view.findViewById(R.id.btn_save_tambah_jasa_service);
        contexts = getContext();
        fragmentManager= getFragmentManager();
        popupNotification = new PopupNotification();
        popupNotification.setParam(getContext(), "Save", "Buatlah Account, untuk bisa menggunakan fitur dengan baik");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //popupNotification.show(fragmentManager, "");
                AddJasaServiceData();
            }
        });
        btnTambahJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadAdapterSpinner();
//                spinnerJasaService.performClick();
                writeDBToSDCard();
            }
        });

        loadInit();
    }

    public void loadInit(){
        // load jasa service
        List<String> jasaService = new ArrayList<>();
        jasaService.add("Service AC");
        jasaService.add("Service Komputer");
        jasaService.add("Service Mesin Mobil");
        jasaService.add("Service Mesin Motor");
        jasaService.add("Service Ban Mobil");
        jasaService.add("Service Ban Motor");

        spinnerJasaService = (Spinner) view.findViewById(R.id.spinner_jasa_service);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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
                        data.add(item);
                        refreshAdapter();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        // load list view
        listview = (ListView) view.findViewById(R.id.list_view_jasa);
//
//        TambahJasa item = new TambahJasa();
//        item.setServiceName("Service AC");
//        TambahJasa item1 = new TambahJasa();
//        item1.setServiceName("Service Ban Motor");
//
//        data.add(item);
//        data.add(item1);
        adapterListView = new RecycleViewListAdapter(getContext(), data, spinnerJasaService);
        listview.setAdapter(adapterListView);
    }

    public static void refreshAdapter(){
        adapterListView = new RecycleViewListAdapter(contexts, data, spinnerJasaService);
        listview.setAdapter(adapterListView);
    }

    private void AddJasaServiceData(){
        List<Service> listService = new ArrayList<>();
        try {
//            db.execSQL("DROP TABLE IF EXISTS "+Service.tbl_service);
            db.execSQL("DROP TABLE IF EXISTS "+ ServiceItem.tbl_service_item);
            db.execSQL("DROP TABLE IF EXISTS "+ DropDownList.tbl_dropDown_list);
//            db.execSQL("DROP TABLE IF EXISTS "+ User.tbl_user);
//            db.execSQL("DROP TABLE IF EXISTS "+ ServiceProvide.tbl_service_provice);

            //db.execSQL("DELETE "+Service.tbl_service);
            //db.execSQL("DELETE FROM "+ ServiceItem.tbl_service_item);
            //db.execSQL("DELETE "+ DropDownList.tbl_dropDown_list);
            //db.close();
            //if(!serviceDao.isTableExists()){
                dbh.onCreate(db);
            //    dbh.close();
            //}
//            listService = serviceDao.queryForAll();
//            serviceDao.delete(listService);

            // add sample data
//            dbh.AddDataService();
            dbh.AddDataServiceItem();
            dbh.addDataDropdown();

            //listService = serviceDao.queryForAll();
            CharSequence text = "Yey... berhasil add data";
            Toast.makeText(getContext(),text, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

//        for (Service services:listService) {
//            jasaService.add(services.getServiceName());
//        }


    }

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
        }
    }
}
