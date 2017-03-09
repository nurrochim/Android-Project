package com.rohim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rohim.adapter.RecycleViewListAdapterHistory;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentHistory extends BaseFragment {

    private List<RequestOrder> dataOrder = new ArrayList<>();
    private List<RequestAccepted> dataAccept = new ArrayList<>();
    private RecyclerView listview;
    private RecycleViewListAdapterHistory adapterListView;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.form_history, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_history);
        loadInit();

    }

    private void loadInit() {
        dataAccept.clear();
        dataOrder.clear();
        if(idUser.contains("PENCARI_")){
            openDatabaseHelper();
            try {
                dataOrder = requestOrderDao.queryBuilder().where().eq(RequestOrder.clm_status,"FINISH").query();
                dbh.close();
            } catch (SQLException e) {
                Log.e("Insert Error", e.toString());
                dbh.close();
            }

        }else{
            openDatabaseHelper();
            try {
                dataAccept = requestAcceptedDao.queryBuilder().where().eq(RequestAccepted.clm_status,"FINISH").query();
                for(RequestAccepted requestAccepted : dataAccept){
                    RequestOrder ro = new RequestOrder();
                    ro.setFidService(requestAccepted.getFidService());
                    ro.setUserName(requestAccepted.getClientName());
                    ro.setHasilService(requestAccepted.getHasilService());
                    ro.setFinishCommentUser(requestAccepted.getFinishCommentUser());
                    dataOrder.add(ro);
                }
                dbh.close();
            } catch (SQLException e) {
                Log.e("Insert Error", e.toString());
                dbh.close();
            }
        }

        for (int i = 0; i < 2; i++) {
            RequestOrder ro = new RequestOrder();
            ro.setFidService("1");
            ro.setUserName("Candra Yudhatama");
            ro.setHasilService("Sangat Bagus");
            ro.setFinishCommentUser("Terimakasih... Service AC nya sangat rapi");
            dataOrder.add(ro);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListView = new RecycleViewListAdapterHistory(getContext(), dataOrder, fragmentManager, true);
        listview.setAdapter(adapterListView);
    }
}
