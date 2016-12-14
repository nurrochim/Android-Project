package com.rohim.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rohim.asyncTaskServer.TestConectionToServer;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.R;

/**
 * Created by Asus on 08/12/2016.
 */

public class FragmentSetting extends BaseFragment {

    EditText ipAddresText;
    Button save, testConection;
    @Override
    public void initView() {
        view = inflater.inflate(R.layout.setting_factory, container, false);
        ipAddresText = (EditText) view.findViewById(R.id.ipAddress);
        ipAddresText.setText("192.168.0.");
        save = (Button) view.findViewById(R.id.btn_save_setting);
        testConection = (Button) view.findViewById(R.id.btn_test_conection);

        if(!ipServer.isEmpty()){
            ipAddresText.setText(ipServer);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ipAddresText.getText().toString().isEmpty()){
                    saveSharedPreference(keyIpAddress, ipAddresText.getText().toString());
                    createToast("Yeay... save ip adress berhasil");
                }

            }
        });
        testConection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestConectionToServer service = new TestConectionToServer();
                service.setIpServer(ipAddresText.getText().toString());
                service.setActivity(getActivity());
                service.setContext(getContext());
                service.execute();
             }
        });
    }
}
