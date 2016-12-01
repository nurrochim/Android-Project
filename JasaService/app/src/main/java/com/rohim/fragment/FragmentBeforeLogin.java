package com.rohim.fragment;

import android.opengl.Visibility;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentBeforeLogin extends BaseFragment {
    Button btnPenyediaJasa, btnPencariJasa;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.before_login, container, false);


        btnPencariJasa = (Button) view.findViewById(R.id.btn_pencari_jasa);
        btnPenyediaJasa = (Button) view.findViewById(R.id.btn_penyedia_jasa);

        btnPencariJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebuildMenuView(false);
                openFragment(new FragmentMenuJasaService(), "Jasa Service", false);
            }
        });

        btnPenyediaJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebuildMenuView(true);
                openFragment(new AddTriggerJasaService(), "Jasa Service", false);
                //openFragment(new FragmentMapLocationCapture(), "Map Lokasi", false);
            }
        });
    }

    public void rebuildMenuView(Boolean isPenyediaJasa){
        MainActivity.navigationView.getMenu().getItem(1).setVisible(isPenyediaJasa);
        MainActivity.navigationView.getMenu().getItem(2).setVisible(isPenyediaJasa);
        MainActivity.navigationView.getMenu().getItem(3).setVisible(!isPenyediaJasa);
        MainActivity.navigationView.getMenu().getItem(4).setVisible(!isPenyediaJasa);
    }
}
