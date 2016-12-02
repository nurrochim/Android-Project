package com.rohim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentRequestActiveTask extends BaseFragment {

    private List<RequestDetail> data = new ArrayList<>();
    private RecyclerView listview;
    private RecycleViewListAdapterDetailRequest adapterListView;
    Button btnCancel, btnAcceptFinish;
    TextView textTitle, textUserName, textNoTelp;
    boolean isNew = false;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.active_request_task, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_jasa_request_task);
        textTitle = (TextView) view.findViewById(R.id.text_title_request_task);
        textUserName = (TextView) view.findViewById(R.id.text_user_name_request_task);
        textNoTelp = (TextView) view.findViewById(R.id.text_no_telp_request_task);
        loadInit();

        btnCancel = (Button) view.findViewById(R.id.btn_cancel_request_task);
        btnAcceptFinish = (Button) view.findViewById(R.id.btn_accept_finish_request_task);
        if(isNew){
            btnAcceptFinish.setText("Accept");
        }else{
            btnAcceptFinish.setText("Finish");
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
                        +",A."+ RequestAccepted.clm_client_name
                        +",A."+RequestAccepted.clm_client_no_telfon
                        +",A."+RequestAccepted.clm_status
                        +" FROM "+ RequestAccepted.tbl_request_accepted +" A LEFT JOIN "+ RequestDetail.tbl_request_detail+" B "
                        +" ON A."+RequestAccepted.clm_id_request+" = B."+RequestDetail.clm_fid_request
                        +" LEFT JOIN "+ Service.tbl_service+" C ON A.FID_SERVICE = C.ID_SERVICE"
                        +" AND A."+RequestOrder.clm_status+" IN ('NEW', 'ACCEPT') ORDER BY B."+RequestDetail.clm_id_request_detail+" ASC";
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
                        if(cursor.getString(10).equals("NEW")){
                            isNew = true;
                        }esle{
                            isNew = false;
                        }
                        data.add(rd);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                cursor.close();
                db.close();
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
}
