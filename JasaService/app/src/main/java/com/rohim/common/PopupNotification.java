package com.rohim.common;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rohim.fragment.FragmentBeforeLogin;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

/**
 * Created by Nurochim on 11/10/2016.
 */

public class PopupNotification extends DialogFragment{
    public Utils.OnSubmitListener mListener;
    Context context;
    String title = "";
    String notification = "";
    boolean isLogout = false;
    public DatabaseHelper dbh ;
    public SQLiteDatabase db;
    public SharedPreferences.Editor editorSharedPreference ;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;

    public void setParam (Context context, String title, String notification, boolean isLogout) {
        this.context = context;
        this.title = title;
        this.notification = notification;
        this.isLogout = isLogout;
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
        LinearLayout layoutButtonLogout = (LinearLayout) dialog.findViewById(R.id.layout_button_logout);
        if(!isLogout){
            layoutButtonLogout.setVisibility(View.GONE);
        }
        Button btnLogout = (Button) dialog.findViewById(R.id.btn_logout_popup);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
                rebuildMenuBeforeLogin();
                dismiss();
            }
        });

        Button btnBack = (Button) dialog.findViewById(R.id.btn_back_popup);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

    private void doLogout(){
        dbh = new DatabaseHelper(context);
        db = dbh.getWritableDatabase();
        try {

            db.execSQL("DELETE FROM "+ User.tbl_user);
//            db.execSQL("DELETE FROM "+ RequestOrder.tbl_request_order);
//            db.execSQL("DELETE FROM "+ RequestAccepted.tbl_request_accepted);
//            db.execSQL("DELETE FROM "+ RequestDetail.tbl_request_detail);
            db.execSQL("DELETE FROM "+ ServiceProvide.tbl_service_provice);

            // open fragmant before login
            openFragment(new FragmentBeforeLogin(), "Jasa Service", false);

            // update shared preference
            editorSharedPreference = getContext().getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE).edit();
            editorSharedPreference.putString("IdUser", "");
            editorSharedPreference.commit();
            rebuildMenuBeforeLogin();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
            dbh.close();
        }
    }


    public void rebuildMenuBeforeLogin(){
        MainActivity.navigationView.getMenu().getItem(0).setVisible(true);
        MainActivity.navigationView.getMenu().getItem(1).setVisible(true);
        MainActivity.navigationView.getMenu().getItem(2).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(3).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(4).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(5).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(6).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(7).setVisible(false);
        MainActivity.navigationView.getMenu().getItem(8).setVisible(false);
    }

    public void openFragment(Fragment fragment, String Title, Boolean isAnimationBack){
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(isAnimationBack){
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }else{
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null).commit();
        if(Title!=null && !Title.isEmpty()){
            MainActivity.textTitle.setText(Title);
        }
    }

}
