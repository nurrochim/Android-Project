package com.rohim.asyncTaskServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.rohim.common.DatabaseHelper;
import com.rohim.jasaservice.MainActivity;
import com.rohim.json_controller.JsonAccount;
import com.rohim.json_controller.JsonServerTest;
import com.rohim.json_controller.NetworkCheck;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Asus on 10/12/2016.
 */

public class TestConectionToServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    HashMap<String, Object> userRespon = new HashMap<>();
    Context context;
    String respon;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public HashMap<String, Object> getUserRespon() {
        return userRespon;
    }

    public void setUserRespon(HashMap<String, Object> userRespon) {
        this.userRespon = userRespon;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(activity);
        // Set progressdialog title
        mProgressDialog.setTitle("Login process");
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // get from JSON
        JsonServerTest conection = new JsonServerTest();
        conection.setIpAddres(ipServer);
        respon = conection.getStatusService();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        //super.onPostExecute(result);
        CharSequence textToast = "";
        Toast toast;


        textToast = respon;
        toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        if( textView != null) textView.setGravity(Gravity.CENTER);
        toast.show();

        mProgressDialog.dismiss();
    }
}
