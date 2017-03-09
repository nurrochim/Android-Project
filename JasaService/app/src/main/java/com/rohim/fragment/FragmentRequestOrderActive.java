package com.rohim.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
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
import com.rohim.asyncTaskServer.AddCommentServiceOrder;
import com.rohim.asyncTaskServer.CancelRequestOrderToServer;
import com.rohim.common.BaseFragment;
import com.rohim.common.DatabaseHelper;
import com.rohim.common.PopupNotification;
import com.rohim.enumeration.EnumInputService;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.DropDownList;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.ServiceProvide;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentRequestOrderActive extends BaseFragment {

    private List<RequestDetail> data = new ArrayList<>();
    private RecyclerView listview;
    private RecycleViewListAdapterDetailRequest adapterListView;
    Button btnBack, btnCancel;
    TextView textTitle, textUserName, textNoTelp;
    String idRequest, idUserAccepted, userNameAccepted, userNoTelp, msgId, msgTitle, msgBody, reasonCancel;
    RequestOrder requestOrder;
    Spinner spinner;
    boolean isFinish = false;
    RelativeLayout profil;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.active_request_order, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_jasa_request_order);
        textTitle = (TextView) view.findViewById(R.id.text_title_request_order);
        textUserName = (TextView) view.findViewById(R.id.text_user_name_request_order);
        textNoTelp = (TextView) view.findViewById(R.id.text_no_telp_request_order);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel_request_order);
        btnBack = (Button) view.findViewById(R.id.btn_back_request_order);
        btnBack.setVisibility(View.INVISIBLE);
        spinner = (Spinner) view.findViewById(R.id.spinner_item);
        profil = (RelativeLayout) view.findViewById(R.id.layout_profil_penyedia_jasa);
        textNoTelp.setPaintFlags(textNoTelp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loadSpinnerCancel();

        if(msgId!=null){
            notifMode();
        }
        loadInit();
//        textUserName.setText("Ahmad Sobirin \nProfesional "+textTitle.getText().toString());
//        textNoTelp.setText("No. Telp : 0856752318998");

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
                                    doCancelOrder();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCancel.getText().toString().equalsIgnoreCase("Cancel")){
                    spinner.performClick();
                }else{
                    // save to db local
                    for(RequestDetail rd : data){
                        if(rd.getServiceItemName().equals("Hasil Service")){
                            requestOrder.setHasilService(rd.getServiceItemValue());
                        }
                        if(rd.getServiceItemName().equals("Ulasan")){
                            requestOrder.setFinishCommentUser(rd.getServiceItemValue());
                        }
                    }
                    openDatabaseHelper();
                    try {
                        requestOrderDao.update(requestOrder);
                    } catch (SQLException e) {
                        Log.e("Error", e.toString());
                    } finally {
                        dbh.close();
                    }

                    // do submit to server
                    AddCommentServiceOrder serviceServer = new AddCommentServiceOrder();
                    serviceServer.setIpServer(ipServer);
                    serviceServer.setActivity(getActivity());
                    serviceServer.setContext(getContext());
                    serviceServer.setRequestOrder(requestOrder);
                    serviceServer.execute();
                }

            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestOrder!=null && requestOrder.getUserNoTelfon()!=null && !requestOrder.getUserNoTelfon().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + requestOrder.getUserNoTelfon()));
                    getContext().startActivity(intent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void notifMode() {
        openDatabaseHelper();
        try {
            if(msgId.equals("PROCESS") || msgId.equals("CANCEL1")) {
                RequestOrder ro = requestOrderDao.queryForId(idRequest);
                ro.setStatus(msgId);
                ro.setUserName(userNameAccepted);
                ro.setUserNoTelfon(userNoTelp);
                requestOrderDao.update(ro);
                PopupNotification popupNotification = new PopupNotification();
                popupNotification.setParam(getContext(), msgTitle, msgBody, false);
                popupNotification.show(getFragmentManager(), "");

            }
            if(msgId.equals("FINISH")){
                RequestOrder ro = requestOrderDao.queryForId(idRequest);
                ro.setStatus(msgId);
                requestOrderDao.update(ro);
                PopupNotification popupNotification = new PopupNotification();
                popupNotification.setParam(getContext(), msgTitle, msgBody, false);
                popupNotification.show(getFragmentManager(), "");
                isFinish = true;
            }


        } catch (SQLException e) {
            Log.e("Error", e.toString());
        } finally {
            dbh.close();
        }
    }

    private void loadInit() {
        if(data.isEmpty() && !isFinish){
            // load list view
            try {
                openDatabaseHelper();
                List<String> statusIn = new ArrayList<>();
                statusIn.add("NEW");
                statusIn.add("PROCESS");
                statusIn.add("CANCEL1");
                List<RequestOrder> listRo = requestOrderDao.queryBuilder().orderBy(RequestOrder.clm_id_request, false).where().in(RequestOrder.clm_status, statusIn).query();
                if(listRo.size()>0){
                    requestOrder = listRo.get(0);
                    textUserName.setText(requestOrder.getUserName());
                    textNoTelp.setText("No. Telp : "+requestOrder.getUserNoTelfon());
                }

                if(requestOrder!=null){
                    Service service = serviceDao.queryForId(requestOrder.getFidService());
                    textTitle.setText(service.getServiceName());

                    data = requestDetailDao.queryBuilder().where().eq(RequestDetail.clm_fid_request, requestOrder.getIdRequest()).query();
                }else{
                    PopupNotification popupNotification = new PopupNotification();
                    popupNotification.setParam(getContext(), "Active Order", "Saat ini sedang tidak ada data", false);
                    popupNotification.show(getFragmentManager(), "");
                    btnBack.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }finally {
                dbh.close();
            }
        }

        if(msgId!=null && msgId.equals("FINISH")){
            openDatabaseHelper();
            try {
                requestOrder = requestOrderDao.queryForId(idRequest);
                Service service = serviceDao.queryForId(requestOrder.getFidService());
                textTitle.setText(service.getServiceName());
                textUserName.setText(requestOrder.getUserName());
                textNoTelp.setText("No. Telp : "+requestOrder.getUserNoTelfon());

                data.clear();
                RequestDetail rd1 = new RequestDetail();
                rd1.setServiceItemName("Hasil Service");
                rd1.setSatuan("REASON_COMENT");
                rd1.setJenisInput(EnumInputService.SpinnerInput.getVal());

                RequestDetail rd2 = new RequestDetail();
                rd2.setServiceItemName("Ulasan");
                rd2.setJenisInput(EnumInputService.TextLong.getVal());

                data.add(rd1);
                data.add(rd2);

                btnCancel.setText("SUBMIT");
                btnBack.setVisibility(View.INVISIBLE);
            } catch (SQLException e) {
                Log.e("Error", e.toString());
            } finally {
                dbh.close();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListView = new RecycleViewListAdapterDetailRequest(getContext(), data, fragmentManager, !isFinish, getActivity());
        listview.setAdapter(adapterListView);
    }

    private void loadSpinnerCancel(){
        List<String> spinnerData = new ArrayList<>();
        CharSequence prompt = "";
        try {
            openDatabaseHelper();
            dropDownListDao = dbh.getDropDownListDao();
            List<DropDownList> listDropDown = dropDownListDao.queryForEq(DropDownList.clm_alias, "REASON_CANCEL2");

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

    private void doCancelOrder(){
        // do synchronize server
        CancelRequestOrderToServer serviceServer = new CancelRequestOrderToServer();
        serviceServer.setIpServer(ipServer);
        serviceServer.setActivity(getActivity());
        serviceServer.setContext(getContext());
        serviceServer.setIdRequest(requestOrder.getIdRequest());
        serviceServer.setIdUserCreate(requestOrder.getFidUserCreate());
        serviceServer.setReason(reasonCancel);
        serviceServer.execute();

        openDatabaseHelper();
        try {
            requestOrder.setStatus("CANCEL2");
            requestOrderDao.update(requestOrder);
        } catch (SQLException e) {
            Log.e("Error", e.toString());
        } finally {
            dbh.close();
        }
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public String getIdUserAccepted() {
        return idUserAccepted;
    }

    public void setIdUserAccepted(String idUserAccepted) {
        this.idUserAccepted = idUserAccepted;
    }

    public String getUserNameAccepted() {
        return userNameAccepted;
    }

    public void setUserNameAccepted(String userNameAccepted) {
        this.userNameAccepted = userNameAccepted;
    }

    public String getUserNoTelp() {
        return userNoTelp;
    }

    public void setUserNoTelp(String userNoTelp) {
        this.userNoTelp = userNoTelp;
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
}
