package com.rohim.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.R;

/**
 * Created by Asus on 08/12/2016.
 */

public class FragmentSetting extends BaseFragment {

    EditText ipAddresText;
    Button save;
    @Override
    public void initView() {
        view = inflater.inflate(R.layout.setting_factory, container, false);
        ipAddresText = (EditText) view.findViewById(R.id.ipAddress);
        save = (Button) view.findViewById(R.id.btn_save_setting);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ipAddresText.getText().toString().isEmpty()){
                    saveSharedPreference(keyIpAddress, ipAddresText.getText().toString());
                    createToast("Yeay... save ip berhasil");
                }

            }
        });
    }
}
