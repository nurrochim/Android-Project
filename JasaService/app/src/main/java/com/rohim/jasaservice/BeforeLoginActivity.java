package com.rohim.jasaservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nurochim on 04/10/2016.
 */

public class BeforeLoginActivity extends Fragment {
    public LayoutInflater inflater;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.inflater = inflater;
        view = inflater.inflate(R.layout.before_login, container, false);
        return view;
    }
}
