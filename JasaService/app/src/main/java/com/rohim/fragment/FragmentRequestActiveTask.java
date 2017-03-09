package com.rohim.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rohim.adapter.NothingSelectedSpinnerAdapter;
import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.asyncTaskServer.AcceptRequestTaskToServer;
import com.rohim.asyncTaskServer.CancelRequestTaskToServer;
import com.rohim.asyncTaskServer.FinishRequestTaskToServer;
import com.rohim.asyncTaskServer.IgnoreRequestTaskToServer;
import com.rohim.asyncTaskServer.getRequestAcceptedFromServer;
import com.rohim.common.BaseFragment;
import com.rohim.common.PopupNotification;
import com.rohim.enumeration.EnumInputService;
import com.rohim.jasaservice.R;
import com.rohim.modal.DropDownList;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentRequestActiveTask extends BaseFragment {

    private List<RequestDetail> data = new ArrayList<>();
    private RecyclerView listview;
    private RecycleViewListAdapterDetailRequest adapterListView;
    Button btnIgnoreCancel, btnAcceptFinish;
    TextView textTitle, textUserName, textNoTelp;
    boolean isNew = false;
    private String idRequest;
    private RequestAccepted requestAccepted;
    String msgId, msgTitle, msgBody, reasonCancel, idUserAccepted, msgHasilService, msgComment;
    Spinner spinner;
    boolean isCommentMode = false;
    boolean isNewMode = false;
    RelativeLayout profil;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.active_request_task, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_jasa_request_task);
        textTitle = (TextView) view.findViewById(R.id.text_title_request_task);
        textUserName = (TextView) view.findViewById(R.id.text_user_name_request_task);
        textNoTelp = (TextView) view.findViewById(R.id.text_no_telp_request_task);
        spinner = (Spinner) view.findViewById(R.id.spinner_cancel_task);
        btnIgnoreCancel = (Button) view.findViewById(R.id.btn_cancel_request_task);
        btnAcceptFinish = (Button) view.findViewById(R.id.btn_accept_finish_request_task);
        profil = (RelativeLayout) view.findViewById(R.id.layout_profil_penyedia_jasa);
        textNoTelp.setPaintFlags(textNoTelp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loadSpinnerCancel();

        if(msgId!=null ) {
            if(msgId.equals("COMMENT"))
                isCommentMode = true;
            else if (msgId.equals("RO"))
                isNewMode = true;
        }

        loadInit();

        if(isNew){
            btnAcceptFinish.setText("Accept");
            btnIgnoreCancel.setText("Ignore");
        }else{
            btnAcceptFinish.setText("Finish");
            btnIgnoreCancel.setText("Cancel");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position)!=null) {
                    reasonCancel = parent.getItemAtPosition(position).toString();
                    new AlertDialog.Builder(getContext())
                            .setTitle("Cancel Order")
                            .setMessage("Anda yakin akan \"CANCEL\" request order "+textTitle.getText().toString()+" ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doCancelTask();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnIgnoreCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnIgnoreCancel.getText().toString().equalsIgnoreCase("Ignore")){
                    IgnoreRequestTaskToServer serviceServer = new IgnoreRequestTaskToServer();
                    serviceServer.setIpServer(ipServer);
                    serviceServer.setActivity(getActivity());
                    serviceServer.setContext(getContext());
                    serviceServer.setIdRequest(requestAccepted.getIdRequest());
                    serviceServer.setIdUserCreate(requestAccepted.getFidUserCreate());
                    serviceServer.setIdUserAccept(idUser);
                    serviceServer.setFragmentManager(getFragmentManager());
                    serviceServer.setBtnAcceptFinish(btnAcceptFinish);
                    serviceServer.setBtnIgnoreCancel(btnIgnoreCancel);
                    serviceServer.execute();

                    // update latitude longitude
                    saveSharedPreference("latitude", "");
                    saveSharedPreference("longitude", "");
                }else{
                    spinner.performClick();
                }
            }
        });

        btnAcceptFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnAcceptFinish.getText().toString().equalsIgnoreCase("Accept")){
                    openDatabaseHelper();
                    try {
                        requestAccepted.setStatus("PROCESS");
                        requestAcceptedDao.create(requestAccepted);

                        for(RequestDetail rd : data){
                            rd.setIdRequestDetail(rd.getIdRequestDetail()+"#Accept");
                            rd.setFidRequest(rd.getFidRequest()+"#Accept");
                            requestDetailDao.create(rd);
                        }

                        // synchronize data with server
                        AcceptRequestTaskToServer serviceServer = new AcceptRequestTaskToServer();
                        serviceServer.setIpServer(ipServer);
                        serviceServer.setActivity(getActivity());
                        serviceServer.setContext(getContext());
                        serviceServer.setBtnAcceptFinish(btnAcceptFinish);
                        serviceServer.setBtnIgnoreCancel(btnIgnoreCancel);
                        serviceServer.setIdRequest(requestAccepted.getIdRequest());
                        serviceServer.setIdUserCreate(requestAccepted.getFidUserCreate());
                        serviceServer.setIdUserAccept(idUser);

                        serviceServer.execute();
                    } catch (Exception e) {
                        Log.e("Insert Error", e.toString());
                    } finally {
                        dbh.close();
                    }


                }else{//do finish
                    // confirm dialog
                    new AlertDialog.Builder(getContext())
                            .setTitle("Finish Task")
                            .setMessage("Pastikan pekerjaan anda benar-benar telah selesai")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do finish
                                    FinishRequestTaskToServer serviceServer = new FinishRequestTaskToServer();
                                    serviceServer.setIpServer(ipServer);
                                    serviceServer.setActivity(getActivity());
                                    serviceServer.setContext(getContext());
                                    serviceServer.setIdRequest(requestAccepted.getIdRequest());
                                    serviceServer.setIdUserCreate(requestAccepted.getFidUserCreate());
                                    serviceServer.setIdUserAccept(idUser);
                                    serviceServer.setBtnAcceptFinish(btnAcceptFinish);
                                    serviceServer.setBtnIgnoreCancel(btnIgnoreCancel);
                                    serviceServer.execute();

                                    // update latitude longitude
                                    saveSharedPreference("latitude", "");
                                    saveSharedPreference("longitude", "");

                                    // update data in device
                                    openDatabaseHelper();
                                    try {
                                        requestAccepted.setStatus("FINISH");
                                        requestAcceptedDao.update(requestAccepted);
                                    } catch (SQLException e) {
                                        Log.e("Insert Error", e.toString());
                                    } finally {
                                        dbh.close();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();


                }
            }
        });

        if(msgId!=null){
            notifMode();
        }

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestAccepted!=null && requestAccepted.getClientNoTelfon()!=null && !requestAccepted.getClientNoTelfon().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + requestAccepted.getClientNoTelfon()));
                    getContext().startActivity(intent);
                }
            }
        });
    }

    private void doCancelTask() {

        CancelRequestTaskToServer serviceServer = new CancelRequestTaskToServer();
        serviceServer.setIpServer(ipServer);
        serviceServer.setActivity(getActivity());
        serviceServer.setContext(getContext());
        serviceServer.setIdRequest(requestAccepted.getIdRequest());
        serviceServer.setIdUserCreate(requestAccepted.getFidUserCreate());
        serviceServer.setIdUserAccept(idUser);
        serviceServer.setReason(reasonCancel);
        serviceServer.setBtnAcceptFinish(btnAcceptFinish);
        serviceServer.setBtnIgnoreCancel(btnIgnoreCancel);
        serviceServer.execute();

        // update latitude longitude
        saveSharedPreference("latitude", "");
        saveSharedPreference("longitude", "");

        // update db local
        openDatabaseHelper();
        try {
            requestAccepted.setStatus("CANCEL1");
            requestAcceptedDao.update(requestAccepted);
        } catch (SQLException e) {
            Log.e("Insert Error", e.toString());
        } finally {
            dbh.close();
        }
    }

    private void notifMode() {
        openDatabaseHelper();
        try {
            if(msgId.equals("CANCEL2")) {
                requestAccepted.setStatus(msgId);
                requestAcceptedDao.update(requestAccepted);
                btnAcceptFinish.setVisibility(View.GONE);
                btnIgnoreCancel.setVisibility(View.GONE);
            }
            if(msgId.equals("COMMENT")) {
                btnAcceptFinish.setVisibility(View.GONE);
                btnIgnoreCancel.setVisibility(View.GONE);
            }
            if(!msgId.equals("RO")) {
                PopupNotification popupNotification = new PopupNotification();
                popupNotification.setParam(getContext(), msgTitle, msgBody, false);
                popupNotification.show(getFragmentManager(), "");
            }
        } catch (SQLException e) {
            Log.e("Error", e.toString());
        } finally {
            dbh.close();
        }
    }

    private void loadInit() {
        if(idRequest!=null && !idRequest.isEmpty() && isNewMode){
            idUser = idUserAccepted;
            getRequestAcceptedFromServer requestAcceptedFromServer = new getRequestAcceptedFromServer();
            requestAcceptedFromServer.setIpServer(ipServer);
            requestAcceptedFromServer.setActivity(getActivity());
            requestAcceptedFromServer.setIdRequest(idRequest);
            requestAcceptedFromServer.setIdUserPenyediaJasa(idUser);
            requestAcceptedFromServer.setContext(getContext());
            try {
                requestAcceptedFromServer.execute().get();
            } catch (InterruptedException e) {
                Log.e("Error", e.toString());
            } catch (ExecutionException e) {
                Log.e("Error", e.toString());
            }finally {
                if(!requestAcceptedFromServer.getStatus().equals(AsyncTask.Status.FINISHED)){
                    if(requestAcceptedFromServer.getRequestAccepted()!=null){
                        requestAccepted = requestAcceptedFromServer.getRequestAccepted();
                        saveSharedPreference("latitude", requestAccepted.getLatitude());
                        saveSharedPreference("longitude", requestAccepted.getLongitude());

                        openDatabaseHelper();
                        List<Service> listService = new ArrayList<>();
                        try {
                            listService = serviceDao.queryBuilder().where().eq(Service.clm_id_service, requestAccepted.getFidService()).query();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            dbh.close();
                        }

                        for(Service service : listService){
                            textTitle.setText(service.getServiceName());
                        }

                        textUserName.setText(requestAccepted.getClientName());
                        textNoTelp.setText("No Telp. "+requestAccepted.getClientNoTelfon());
                        if(requestAccepted.getStatus().equals("NEW")){
                            isNew = true;
                        }else{
                            isNew = false;
                        }
                    }

                    if(requestAcceptedFromServer.getRequestDetailList().size()>0){
                        data.clear();
                        data = requestAcceptedFromServer.getRequestDetailList();
                    }
                }
            }
        } else if(idRequest!=null && !idRequest.isEmpty() && isCommentMode){

                data.clear();
                RequestDetail rd1 = new RequestDetail();
                rd1.setServiceItemName("Hasil Service");
                rd1.setServiceItemValue(msgHasilService);
                rd1.setJenisInput(EnumInputService.SpinnerInput.getVal());

                RequestDetail rd2 = new RequestDetail();
                rd2.setServiceItemName("Ulasan");
                rd2.setServiceItemValue(msgComment);
                rd2.setJenisInput(EnumInputService.TextLong.getVal());

                data.add(rd1);
                data.add(rd2);
                // save data to local db
            try {
                requestAccepted = requestAcceptedDao.queryForId(idRequest);

                requestAccepted.setHasilService(msgHasilService);
                requestAccepted.setFinishCommentUser(msgComment);
                requestAcceptedDao.update(requestAccepted);

                Service service = serviceDao.queryForId(requestAccepted.getFidService());
                textTitle.setText(service.getServiceName());


                textUserName.setText(requestAccepted.getClientName());
                textNoTelp.setText(requestAccepted.getClientNoTelfon());
                if(requestAccepted.getStatus().equals("NEW")){
                    isNew = true;
                }else{
                    isNew = false;
                }

            } catch (SQLException e) {
                Log.e("Select Error", e.toString());
            }
        } else{
            openDatabaseHelper();
            try {
                List<RequestAccepted> listReqAccp = requestAcceptedDao.queryBuilder()
                                                    .where().eq(RequestAccepted.clm_status, "PROCESS").query();
                if(listReqAccp.size()>0){
                    requestAccepted = listReqAccp.get(0);

                    Service service = serviceDao.queryForId(requestAccepted.getFidService());
                    textTitle.setText(service.getServiceName());


                    textUserName.setText(requestAccepted.getClientName());
                    textNoTelp.setText(requestAccepted.getClientNoTelfon());
                    if(requestAccepted.getStatus().equals("NEW")){
                        isNew = true;
                    }else{
                        isNew = false;
                    }
                    // get list request detail
                    data = requestDetailDao.queryForEq(RequestDetail.clm_fid_request, requestAccepted.getIdRequest()+"#Accept");

                }else{
                    PopupNotification popupNotification = new PopupNotification();
                    popupNotification.setParam(getContext(), "Active Task", "Saat ini sedang tidak ada data", false);
                    popupNotification.show(getFragmentManager(), "");
                    btnAcceptFinish.setVisibility(View.INVISIBLE);
                    btnIgnoreCancel.setVisibility(View.INVISIBLE);
                }



            } catch (SQLException e) {
                Log.e("Select Error", e.toString());
            }finally {
                dbh.close();
            }
        }
//        if(data.isEmpty()){
//            // load list view
//            try {
//                // select query
//                String sql = "SELECT "
//                        +"B."+RequestDetail.clm_id_request_detail
//                        +",B."+RequestDetail.clm_fid_request
//                        +",B."+RequestDetail.clm_fid_service_item
//                        +",B."+RequestDetail.clm_service_item_name
//                        +",B."+RequestDetail.clm_service_item_value
//                        +",B."+RequestDetail.clm_jenis_input
//                        +",B."+RequestDetail.clm_satuan
//                        +",C."+Service.clm_service_name
//                        +",A."+ RequestAccepted.clm_client_name
//                        +",A."+RequestAccepted.clm_client_no_telfon
//                        +",A."+RequestAccepted.clm_status
//                        +" FROM "+ RequestAccepted.tbl_request_accepted +" A LEFT JOIN "+ RequestDetail.tbl_request_detail+" B "
//                        +" ON A."+RequestAccepted.clm_id_request+" = B."+RequestDetail.clm_fid_request
//                        +" LEFT JOIN "+ Service.tbl_service+" C ON A.FID_SERVICE = C.ID_SERVICE"
//                        +" AND A."+RequestOrder.clm_status+" IN ('NEW', 'ACCEPT') ORDER BY B."+RequestDetail.clm_id_request_detail+" ASC";
//                // get db conection
//                db = dbh.getWritableDatabase();
//                cursor = db.rawQuery(sql, null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        RequestDetail rd = new RequestDetail();
//                        rd.setIdRequestDetail(cursor.getString(0));
//                        rd.setFidRequest(cursor.getString(1));
//                        rd.setFidServiceItem(cursor.getInt(2));
//                        rd.setServiceItemName(cursor.getString(3));
//                        rd.setServiceItemValue(cursor.getString(4));
//                        rd.setJenisInput(cursor.getString(5));
//                        rd.setSatuan(cursor.getString(6));
//                        textTitle.setText(cursor.getString(7));
//                        textUserName.setText(cursor.getString(8));
//                        textNoTelp.setText(cursor.getString(9));
//                        if(cursor.getString(10).equals("NEW")){
//                            isNew = true;
//                        }else{
//                            isNew = false;
//                        }
//                        data.add(rd);
//                    } while (cursor.moveToNext());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                cursor.close();
//                db.close();
//            }
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListView = new RecycleViewListAdapterDetailRequest(getContext(), data, fragmentManager, true, getActivity());
        listview.setAdapter(adapterListView);
    }

    private void loadSpinnerCancel(){
        List<String> spinnerData = new ArrayList<>();
        CharSequence prompt = "";
        try {
            openDatabaseHelper();
            dropDownListDao = dbh.getDropDownListDao();
            List<DropDownList> listDropDown = dropDownListDao.queryForEq(DropDownList.clm_alias, "REASON_CANCEL1");

            prompt = "Pilih Alasan Cancel";
            for(DropDownList dw: listDropDown){
                spinnerData.add(dw.getDescription());
            }
        } catch (SQLException e) {
            Log.e("Error", e.toString());
        }

        spinner.setPrompt(prompt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,R.layout.spinner_not_selected,getContext()));
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }


    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getIdUserAccepted() {
        return idUserAccepted;
    }

    public void setIdUserAccepted(String idUserAccepted) {
        this.idUserAccepted = idUserAccepted;
    }

    public String getMsgHasilService() {
        return msgHasilService;
    }

    public void setMsgHasilService(String msgHasilService) {
        this.msgHasilService = msgHasilService;
    }

    public String getMsgComment() {
        return msgComment;
    }

    public void setMsgComment(String msgComment) {
        this.msgComment = msgComment;
    }
}

















