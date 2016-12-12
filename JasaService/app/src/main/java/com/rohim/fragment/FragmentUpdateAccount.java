package com.rohim.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rohim.adapter.RecycleViewListAdapterDetailRequest;
import com.rohim.asyncTaskServer.addUserToServer;
import com.rohim.asyncTaskServer.updateUserToServer;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.MainActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.Service;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurochim on 07/10/2016.
 */

public class FragmentUpdateAccount extends BaseFragment {

    private List<RequestDetail> data = new ArrayList<>();
    Button btnSave, btnUpdatePassword;
    EditText textUserName, textNoTelp, textEmail, textCurrentPassword, textNewPassword, textConfirmPassword;
    User user = new User();

    @Override
    public void initView() {
        view = inflater.inflate(R.layout.account_update, container, false);
        textUserName = (EditText) view.findViewById(R.id.txt_user_account_update);
        textNoTelp = (EditText) view.findViewById(R.id.txt_no_telpon_account_update);
        textEmail = (EditText) view.findViewById(R.id.txt_email_account_update);
        textCurrentPassword = (EditText) view.findViewById(R.id.text_current_password_acccount_insert);
        textNewPassword = (EditText) view.findViewById(R.id.text_new_password_acccount_insert);
        textConfirmPassword = (EditText) view.findViewById(R.id.text_confirm_password_acccount_insert);

        loadInit();

        btnSave = (Button) view.findViewById(R.id.btn_save_account_update);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isErrorValidate = false;
                // save
                if(textUserName.getText().toString().isEmpty()){
                    isErrorValidate = true;
                    createToast("Tolong isi User Name dengan benar");
                }
                if(textNoTelp.getText().toString().isEmpty()){
                    isErrorValidate = true;
                    createToast("Tolong isi No Telepon dengan benar");
                }
                String email = textEmail.getText().toString();
                if(!email.contains("@") && !email.contains(".") ){
                    isErrorValidate = true;
                    createToast("Tolong isikan alamat email dengan benar");
                }
                if(!isErrorValidate){
                    user.setUserName(textUserName.getText().toString());
                    user.setNoTelp(textNoTelp.getText().toString());
                    user.setEmail(textEmail.getText().toString());
                    // save
                    try {
                        openDatabaseHelper();
                        userDao.update(user);
                        // refresh data di panel left menu
                        MainActivity.textUserName.setText(user.getUserName());
                        MainActivity.textEmail.setText(user.getEmail());
                        // Close DB Conection
                        dbh.close();

                        updateUserToServer updateUser = new updateUserToServer();
                        updateUser.setIpServer(ipServer);
                        updateUser.setActivity(getActivity());
                        updateUser.setUser(user);
                        updateUser.setContext(getContext());
                        updateUser.execute();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                        createToast("Ups...Maaf, pendaftaran gagal");
                    } finally {
                        dbh.close();
                    }
                }

            }
        });
        btnUpdatePassword = (Button) view.findViewById(R.id.btn_update_password_account_update);
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isErrorValidate = false;
                // cek current password
                if(!textCurrentPassword.getText().toString().equals(user.getPassword())){
                    isErrorValidate = true;
                    createToast("Current Password yang anda isi tidak sesuai");
                }
                // cek new password dan confirm password
                if(!textNewPassword.getText().toString().equals(textConfirmPassword.getText().toString())){
                    isErrorValidate = true;
                    createToast("New Password dan Confirm password tidak sama dengan ");
                }
                // update password
                if(!isErrorValidate){
                    user.setPassword(textNewPassword.getText().toString());
                    // save
                    try {
                        openDatabaseHelper();
                        userDao.update(user);
                        // refresh data di panel left menu
                        MainActivity.textUserName.setText(user.getUserName());
                        MainActivity.textEmail.setText(user.getEmail());
                        // Close DB Conection
                        dbh.close();

                        updateUserToServer updateUser = new updateUserToServer();
                        updateUser.setIpServer(ipServer);
                        updateUser.setActivity(getActivity());
                        updateUser.setUser(user);
                        updateUser.setContext(getContext());
                        updateUser.setUpdate("PASSWORD");
                        updateUser.execute();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.e("Update Erros", e.toString());
                        createToast("Ups...Maaf, pendaftaran gagal");
                    } finally {
                        dbh.close();
                    }
                }
            }
        });
    }

    private void loadInit() {
        // load list view
        try {
            openDatabaseHelper();
            List<User> listUser = userDao.queryForEq(User.clm_id_user, idUser);
            if(listUser != null && listUser.size()>0){
                user = listUser.get(0);
                textUserName.setText(user.getUserName());
                textNoTelp.setText(user.getNoTelp());
                textEmail.setText(user.getEmail());
            }
            // Close DB Conection
            dbh.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbh.close();
        }

    }


}
