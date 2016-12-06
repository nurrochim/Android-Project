package com.rohim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.rohim.adapter.RecycleViewListAdapterHistory;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentLogout extends BaseFragment {
    Button btnLogout;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.form_logout, container, false);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openDatabaseHelper();
                    db = dbh.getWritableDatabase();
                    db.execSQL("DELETE FROM "+ User.tbl_user);
                    db.execSQL("DELETE FROM "+ RequestOrder.tbl_request_order);
                    db.execSQL("DELETE FROM "+ RequestAccepted.tbl_request_accepted);
                    db.execSQL("DELETE FROM "+ RequestDetail.tbl_request_detail);

                    // open fragmant before login
                    openFragment(new FragmentBeforeLogin(), "Jasa Service", false);
                    //
                    editorSharedPreference.putString("IdUser", "");
                    editorSharedPreference.commit();
                    rebuildMenuBeforeLogin();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    db.close();
                }
            }
        });
    }

    public void rebuildMenuBeforeLogin(){
        MainActivity.navigationView.getMenu().getItem(0).setVisible(true);
        MainActivity.navigationView.getMenu().getItem(1).setVisible(true);
        MainActivity.navigationView.getMenu().getItem(2).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(3).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(4).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(5).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(6).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(7).setVisible(true);
        MainActivity.navigationView.getMenu().getItem(8).setVisible(false);
    }

}
