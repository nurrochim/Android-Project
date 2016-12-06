package com.rohim.fragment;

import com.rohim.common.BaseFragment;
import com.rohim.common.PopupNotification;
import com.rohim.jasaservice.R;

/**
 * Created by Nurochim on 11/10/2016.
 */

public class BeforeLoginNotification extends BaseFragment{
    public PopupNotification popupNotification;
    String title = "";
    String popupTitle = "";
    boolean isLogout = false;

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.content_main, container, false);
        popupNotification = new PopupNotification();
        if(isLogout){
            popupNotification.setParam(getContext(), title, "Semua data history anda akan terhapus \n Anda yakin akan keluar? ", isLogout);
        }else{
            popupNotification.setParam(getContext(), title, "Buatlah Account, untuk bisa menggunakan fitur dengan baik", isLogout);
        }

        popupNotification.show(getFragmentManager(),"");

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }
}
