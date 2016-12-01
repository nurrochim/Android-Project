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

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.content_main, container, false);
        popupNotification = new PopupNotification();
        popupNotification.setParam(getContext(), title, "Buatlah Account, untuk bisa menggunakan fitur dengan baik");
        popupNotification.show(getFragmentManager(),"");

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopupTitle() {
        return popupTitle;
    }
}
