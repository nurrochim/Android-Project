package com.rohim.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.asyncTaskServer.addRequestOrderToServer;
import com.rohim.common.BaseFragment;
import com.rohim.common.DatabaseHelper;
import com.rohim.enumeration.EnumInputService;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nurochim on 19/10/2016.
 */

public class FragmentInputRequestDetail extends BaseFragment {
    Button btnSubmit, btnBack;
    private Spinner spinner;
    private RecyclerView listview;
    private RecycleViewListAdapterDetailRequest adapterListView;
    private List<RequestDetail> data = new ArrayList<>();
    private String idService = "";
    String idUser;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.input_request_jasa, container, false);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit_input_request);
        btnBack = (Button) view.findViewById(R.id.btn_back_input_request);
        listview = (RecyclerView) view.findViewById(R.id.list_view_jasa_input_request);
        loadInit();

        // get Id User
        SharedPreferences prefs = getContext().getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE);
        idUser = prefs.getString("IdUser","");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUser!=null && !idUser.isEmpty()){
                    if(!seePreviousRequestInProcess()) {
                        boolean isSucces = doSave();
                        if (isSucces) {
//                            createToast("Terimakasih... \n Request anda sedang dalam proses");
                            getFragmentManager().popBackStack();
                        } else {
                            createToast("Maaf... Sepertinya ada masalah \n kami akan memperbaikinya segera");
                            getFragmentManager().popBackStack();
                        }
                    }
                }
            }
        });
    }

    private void loadInit() {
        if(data.isEmpty()){
            // load list view
            try {
                List<ServiceItem> listServiceItems = serviceItemDao.queryForEq(ServiceItem.clm_fid_service, idService);
                dbh.close();
                RequestDetail item = null;
                for(ServiceItem serviceItem : listServiceItems){
                    item = new RequestDetail(serviceItem);
                    data.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListView = new RecycleViewListAdapterDetailRequest(getContext(), data, fragmentManager, false, getActivity());
        listview.setAdapter(adapterListView);
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    private boolean doSave() {
        int dataDetail = 1;
        // get USER DATA
        try {
            openDatabaseHelper();
            List<User> listUser = userDao.queryForEq(User.clm_id_user, idUser);
            if(listUser != null) {
                User user = listUser.get(0);

                // Save Request Order
                String idRequestOrder = idUser + "/" + idService + "_" + System.currentTimeMillis();
                RequestOrder ro = new RequestOrder();
                ro.setIdRequest(idRequestOrder);
                ro.setFidUserCreate(idUser);
                ro.setStatus("NEW");
                ro.setFidService(idService);
                ro.setUserName("Menunggu Process System");
                ro.setUserNoTelfon("");
                ro.setCreateDate(new Date());

                // get latlng
                String lat = getValueSharedPreference("latitude").toString();
                String lng = getValueSharedPreference("longitude").toString();
                ro.setLatitude(lat);
                ro.setLongitude(lng);
                requestOrderDao.create(ro);

                // Save Request Detail
                for (RequestDetail rqDet : data) {
                    if(rqDet.getJenisInput().equals(EnumInputService.Map.getVal())  ){
                        if(rqDet.getServiceItemValue()==null){
                            rqDet.setServiceItemValue(getValueSharedPreference("Address").toString());
                        }else if(!rqDet.getServiceItemValue().isEmpty()){
                            rqDet.setServiceItemValue(getValueSharedPreference("Address").toString());
                        }
                    }
                    rqDet.setIdRequestDetail(idRequestOrder + "/Detail_" + dataDetail);
                    rqDet.setFidRequest(idRequestOrder);
                    requestDetailDao.create(rqDet);
                    dataDetail++;
                }

                // synchronize to server
                Gson gson = new Gson();
                String listDataJson = gson.toJson(ro)+"#{requestDetail : "+gson.toJson(data)+"}";

                addRequestOrderToServer addRequestOrder = new addRequestOrderToServer();
                addRequestOrder.setIpServer(ipServer);
                addRequestOrder.setActivity(getActivity());
                addRequestOrder.setListDataJson(listDataJson);
                addRequestOrder.setContext(getContext());
                addRequestOrder.execute();
            }
            // Close DB Conection
            dbh.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbh.close();
        }
    }

    private boolean seePreviousRequestInProcess(){
        boolean isPreviousRequestStillProcess = false;
        try {
            openDatabaseHelper();
            List<String> status = new ArrayList<>();
            status.add("NEW");
            status.add("ACCEPT");

            List<RequestOrder> listOrder = requestOrderDao.queryBuilder().where().in(RequestOrder.clm_status,status).query();
            dbh.close();

            if(listOrder.size()>0){
                isPreviousRequestStillProcess = true;
                createToast("Maaf... Tidak dapat melanjutkan \n Sebab Request sebelumnya masih dalam proses");
            //requestOrderDao.deleteBuilder().where().in(RequestOrder.clm_status,status).query();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isPreviousRequestStillProcess;
    }
}
