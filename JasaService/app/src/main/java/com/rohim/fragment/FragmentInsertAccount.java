package com.rohim.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rohim.common.BaseFragment;
import com.rohim.jasaservice.LoginActivity;
import com.rohim.jasaservice.R;
import com.rohim.modal.User;

import java.sql.SQLException;

/**
 * Created by Nurochim on 11/10/2016.
 */

public class FragmentInsertAccount extends BaseFragment {
    RadioButton rbPenyediaJasa, rbPencariJasa;
    Button btnSubmit;
    EditText textUserName, textNoTelfon, textEmail, textPassword, textConfirmPassword;
    Boolean isValidateError = false;
    String jenisUser = "Pencari Jasa";
    String idUser = "PENCARI_";
    @Override
    public void initView() {
        view = inflater.inflate(R.layout.account_insert, container, false);
        textUserName = (EditText) view.findViewById(R.id.text_insert_user_name);
        textNoTelfon = (EditText) view.findViewById(R.id.text_insert_phone_number);
        textEmail = (EditText) view.findViewById(R.id.text_insert_email);
        textPassword = (EditText) view.findViewById(R.id.text_insert_password);
        textConfirmPassword = (EditText) view.findViewById(R.id.text_insert_confirm_password);
        rbPencariJasa = (RadioButton) view.findViewById(R.id.rb_pencari_jasa);
        rbPenyediaJasa = (RadioButton) view.findViewById(R.id.rb_penyedia_jasa);

        rbPenyediaJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenisUser = "Penyedia Jasa";
                idUser = "PENYEDIA_";
            }
        });

        rbPencariJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenisUser = "Pencari Jasa";
                idUser = "PENCARI_";
            }
        });

        btnSubmit = (Button) view.findViewById(R.id.btn_save_insert_account);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateError = false;
                // validasi input
                if(textEmail.getText().toString().isEmpty() ||
                        textUserName.getText().toString().isEmpty() ||
                        textNoTelfon.getText().toString().isEmpty() ||
                        textPassword.getText().toString().isEmpty() ||
                        textConfirmPassword.getText().toString().isEmpty() ){
                    isValidateError = true;
                    createToast("Tolong isikan semua field yang ada");
                }
                // validasi email
                if(!isValidateError){
                    String email = textEmail.getText().toString();
                    if(!email.contains("@") && !email.contains(".") ){
                        isValidateError = true;
                        createToast("Tolong isikan alamat email dengan benar");
                    }
                }

                // validasi password
                if(!isValidateError){
                    if(!textPassword.getText().toString().equals(textConfirmPassword.getText().toString())){
                        isValidateError = true;
                        createToast("Password tidak sama...");
                    }
                }

                if(!isValidateError){
                    User user = new User();
                    user.setIdUser(idUser+System.currentTimeMillis());
                    user.setEmail(textEmail.getText().toString());
                    user.setUserName(textUserName.getText().toString());
                    user.setNoTelp(textNoTelfon.getText().toString());
                    user.setPassword(textPassword.getText().toString());
                    user.setJenisUser(jenisUser);

                    // get token for Firebase notification
                    String token = "";//FirebaseInstanceId.getInstance().getId();
                    user.setToken(token);
                    try {
                        userDao.create(user);
                        createToast("Terimakasih... Pendaftaran account berhasil");
                        dbh.close();

                        // login screen
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent, 0);
                        ActivityCompat.finishAffinity(getActivity());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        createToast("Ups...Maaf, pendaftaran gagal");
                    }
                }
            }
        });
    }
}
