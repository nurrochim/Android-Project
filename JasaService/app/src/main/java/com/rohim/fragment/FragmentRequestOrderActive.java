package com.rohim.fragment;

import android.view.View;
import android.widget.Button;

import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentRequestOrderActive extends BaseFragment {

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.active_request_order, container, false);
    }


}
