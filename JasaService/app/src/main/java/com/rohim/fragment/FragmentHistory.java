package com.rohim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.adapter.RecycleViewListAdapterHistory;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentHistory extends BaseFragment {

    private List<RequestOrder> data = new ArrayList<>();
    private RecyclerView listview;
    private RecycleViewListAdapterHistory adapterListView;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.form_history, container, false);
        listview = (RecyclerView) view.findViewById(R.id.list_view_history);
        loadInit();

    }

    private void loadInit() {
        for (int i = 0; i < 6; i++) {
            RequestOrder ro = new RequestOrder();
            ro.setFidService("1");
            ro.setUserName("Candra Yudhatama");
            ro.setHasilService("Sangat Bagus");
            ro.setFinishCommentUser("Terimakasih... Service AC nya sangat rapi");
            data.add(ro);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterListView = new RecycleViewListAdapterHistory(getContext(), data, fragmentManager, true);
        listview.setAdapter(adapterListView);
    }
}
