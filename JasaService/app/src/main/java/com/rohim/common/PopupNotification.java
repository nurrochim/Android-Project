package com.rohim.common;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rohim.jasaservice.R;

/**
 * Created by Nurochim on 11/10/2016.
 */

public class PopupNotification extends DialogFragment{
    public Utils.OnSubmitListener mListener;
    Context context;
    String title = "";
    String notification = "";

    public void setParam (Context context, String title, String notification) {
        this.context = context;
        this.title = title;
        this.notification = notification;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.popup_notification);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView textTitle= (TextView) dialog.findViewById(R.id.text_title);
        textTitle.setText(title);
        TextView notif = (TextView) dialog.findViewById(R.id.text_notification);
        notif.setText(notification);

        return dialog;
    }


}
