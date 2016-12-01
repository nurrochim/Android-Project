package com.rohim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rohim.adapter.RecycleViewListAdapterMenu;
import com.rohim.common.BaseFragment;
import com.rohim.common.GridSpacingItemDecoration;
import com.rohim.jasaservice.R;
import com.rohim.modal.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 17/10/2016.
 */

public class FragmentMenuJasaService extends BaseFragment{
    RecyclerView listview;
    RecycleViewListAdapterMenu adapterView;
    List<Service> data = new ArrayList<>();

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.menu_jasa_service, container, false);
        listview = (RecyclerView) view.findViewById(R.id.view_menu_jasa_service);
        if(data.isEmpty()) {
            loadInit();
        }
    }

    private void loadInit() {
        try {
            data = serviceDao.queryForAll();
            dbh.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //set up equal space between grid columns
        int spanCount = 2; // 3 columns
        int spacing = 20; // 20px
        boolean includeEdge = true;
        listview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        listview.setAdapter(new RecycleViewListAdapterMenu(getActivity(),data, fragmentManager));
    }
}
