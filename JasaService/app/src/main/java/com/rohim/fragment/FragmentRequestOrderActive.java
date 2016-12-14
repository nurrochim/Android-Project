package com.rohim.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.common.BaseFragment;
import com.rohim.common.PopupNotification;
import com.rohim.enumeration.EnumInputService;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;
import com.rohim.modal.ServiceItem;

import java.sql.SQLException;
import java.util.ArrayList;
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
    String idRequest, idUserAccepted, userNameAccepted, userNoTelp, msgId, msgTitle, msgBody;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.active_request_order, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_jasa_request_order);
        textTitle = (TextView) view.findViewById(R.id.text_title_request_order);
        textUserName = (TextView) view.findViewById(R.id.text_user_name_request_order);
        textNoTelp = (TextView) view.findViewById(R.id.text_no_telp_request_order);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel_request_order);
        btnBack = (Button) view.findViewById(R.id.btn_back_request_order);
        if(msgId!=null){
            notifMode();
        }
        loadInit();
//        textUserName.setText("Ahmad Sobirin \nProfesional "+textTitle.getText().toString());
//        textNoTelp.setText("No. Telp : 0856752318998");


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCancel.getText().toString().equalsIgnoreCase("Cancel")){
                    // do synchronize server

                    // do delete data from device
                    db = dbh.getWritableDatabase();
                    String sql = "DELETE FROM "+RequestOrder.tbl_request_order;
                    db.execSQL(sql);
                    sql = "DELETE FROM "+RequestDetail.tbl_request_detail;
                    db.execSQL(sql);
                    db.close();
                }else{
                    // do submit comment

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

            }
            if(msgId.equals("FINISH")){
                RequestOrder ro = requestOrderDao.queryForId(idRequest);
                ro.setStatus(msgId);
                requestOrderDao.update(ro);

            }
            PopupNotification popupNotification = new PopupNotification();
            popupNotification.setParam(getContext(), msgTitle, msgBody, false);
            popupNotification.show(getFragmentManager(), "");

        } catch (SQLException e) {
            Log.e("Error", e.toString());
        } finally {
            dbh.close();
        }
    }

    private void loadInit() {
        if(data.isEmpty()){
            // load list view
            try {
                // select query
                String sql = "SELECT "
                        +"B."+RequestDetail.clm_id_request_detail
                        +",B."+RequestDetail.clm_fid_request
                        +",B."+RequestDetail.clm_fid_service_item
                        +",B."+RequestDetail.clm_service_item_name
                        +",B."+RequestDetail.clm_service_item_value
                        +",B."+RequestDetail.clm_jenis_input
                        +",B."+RequestDetail.clm_satuan
                        +",C."+Service.clm_service_name
                        +",A."+RequestOrder.clm_user_name
                        +",A."+RequestOrder.clm_user_no_telfon
                        +" FROM "+ RequestOrder.tbl_request_order +" A LEFT JOIN "+ RequestDetail.tbl_request_detail+" B "
                        +" ON A."+RequestOrder.clm_id_request+" = B."+RequestDetail.clm_fid_request
                        +" LEFT JOIN "+ Service.tbl_service+" C ON A.FID_SERVICE = C.ID_SERVICE"
                        +" AND A."+RequestOrder.clm_status+" IN ('NEW', 'PROCESS', 'CANCEL1') ORDER BY B."+RequestDetail.clm_id_request_detail+" ASC";
                // get db conection
                db = dbh.getWritableDatabase();
                cursor = db.rawQuery(sql, null);


                if (cursor.moveToFirst()) {
                    do {
                        RequestDetail rd = new RequestDetail();
                        rd.setIdRequestDetail(cursor.getString(0));
                        rd.setFidRequest(cursor.getString(1));
                        rd.setFidServiceItem(cursor.getInt(2));
                        rd.setServiceItemName(cursor.getString(3));
                        rd.setServiceItemValue(cursor.getString(4));
                        rd.setJenisInput(cursor.getString(5));
                        rd.setSatuan(cursor.getString(6));
                        textTitle.setText(cursor.getString(7));
                        textUserName.setText(cursor.getString(8));
                        textNoTelp.setText(cursor.getString(9));
                        data.add(rd);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }finally {
                cursor.close();
                db.close();
            }
        }

        if(msgId!=null && msgId.equals("FINISH")){
            openDatabaseHelper();
            try {
                RequestOrder ro = requestOrderDao.queryForId(idRequest);
                Service service = serviceDao.queryForId(ro.getFidService());
                textTitle.setText(service.getServiceName());
                textUserName.setText(ro.getUserName());
                textNoTelp.setText(ro.getUserNoTelfon());

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
                adapterListView = new RecycleViewListAdapterDetailRequest(getContext(), data, fragmentManager, false);

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
        adapterListView = new RecycleViewListAdapterDetailRequest(getContext(), data, fragmentManager, true);
        listview.setAdapter(adapterListView);
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
