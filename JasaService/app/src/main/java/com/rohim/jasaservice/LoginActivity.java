package com.rohim.jasaservice;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.rohim.asyncTaskServer.loginToServer;
import com.rohim.common.DatabaseHelper;
import com.rohim.json_controller.JsonAccount;
import com.rohim.modal.ServiceItem;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nurochim on 30/09/2016.
 */

public class LoginActivity extends Activity{
    EditText textEmail, textPassword;
    Button login;
    Boolean isValidateTrue = true;
    public Dao<User, String> userDao = null;
    public DatabaseHelper dbh ;
    public SQLiteDatabase db;
    public SharedPreferences.Editor editorSharedPreference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        textEmail = (EditText) findViewById(R.id.text_email);
        textPassword = (EditText) findViewById(R.id.text_password);
        SharedPreferences sh = getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE);
        editorSharedPreference = sh.edit();
        final String ipServer = sh.getString("IpAddress","");

        textEmail.setText("c@n.dra");
        textPassword.setText("c");

        // create conection db
        dbh = new DatabaseHelper(getApplicationContext());
        db = dbh.getWritableDatabase();
        try {
            userDao = dbh.getUserDao();
        } catch (SQLException e) {
            Log.e("Insert Error", e.toString());
        }

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateTrue = true;

                if(textEmail.getText().toString().isEmpty()||
                        textPassword.getText().toString().isEmpty()){
                    CharSequence textToast = "Tolong isi Email dan Password dengan benar";
                    Toast.makeText(getApplicationContext(),textToast, Toast.LENGTH_SHORT).show();
                    isValidateTrue = false;
                }

                if(isValidateTrue){
                    String email = textEmail.getText().toString();
                    if(!email.contains("@") && !email.contains(".") ){
                        isValidateTrue = false;

                        CharSequence textToast = "Tolong isikan alamat email dengan benar";
                        Toast.makeText(getApplicationContext(),textToast, Toast.LENGTH_SHORT).show();
                    }
                }

                if(isValidateTrue){
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    loginToServer loginUser = new loginToServer();
                    loginUser.setIpServer(ipServer);
                    loginUser.setActivity(LoginActivity.this);
                    loginUser.setEmail(textEmail.getText().toString());
                    loginUser.setPassword(textPassword.getText().toString());
                    loginUser.setContext(getApplicationContext());
                    loginUser.execute();


                }

//                if(isValidateTrue){
//                    long count = 0;
//                    SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM "+User.tbl_user+" WHERE "+User.clm_email+" = '"+ textEmail.getText().toString()
//                                +"' AND "+User.clm_password+" = '"+textPassword.getText().toString()+"'");
//                    count = statement.simpleQueryForLong();
//
//                    statement = db.compileStatement("SELECT "+User.clm_id_user+" FROM "+User.tbl_user+" WHERE "+User.clm_email+" = '"+ textEmail.getText().toString()
//                            +"' AND "+User.clm_password+" = '"+textPassword.getText().toString()+"'");
//                    String idUser = statement.simpleQueryForString();
//
//                    statement = db.compileStatement("SELECT "+User.clm_user_name+" FROM "+User.tbl_user+" WHERE "+User.clm_email+" = '"+ textEmail.getText().toString()
//                            +"' AND "+User.clm_password+" = '"+textPassword.getText().toString()+"'");
//
//                    String userName = statement.simpleQueryForString();
//
//                    statement.close();
//                    db.close();
//
//                    if(count == 1){
//                        // Save idUser di shared preference
//                        editorSharedPreference.putString("IdUser", idUser);
//                        editorSharedPreference.commit();
//
//                        // login screen
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                        CharSequence textToast = "LOGIN BERHASIL \n SELAMAT DATANG "+idUser+"\n"+userName;
//                        Toast toast = Toast.makeText(getApplicationContext(),textToast, Toast.LENGTH_SHORT);
//                        //toast.setGravity(Gravity.CENTER,0,0);
//                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
//                        if( textView != null) textView.setGravity(Gravity.CENTER);
//                        toast.show();
//
//                    }
//                }
            }
        });
    }
}
