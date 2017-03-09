package com.rohim.asyncTaskServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rohim.json_controller.JsonAccount;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.ServiceProvide;
import com.rohim.modal.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 10/12/2016.
 */

public class getServiceProvideFromServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    Context context;
    String respon = "";
    String idUser;
    List<ServiceProvide> listServiceProvide = new ArrayList<>();

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

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

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ServiceProvide> getListServiceProvide() {
        return listServiceProvide;
    }

    public void setListServiceProvide(List<ServiceProvide> listServiceProvide) {
        this.listServiceProvide = listServiceProvide;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(activity);
        // Set progressdialog title
        mProgressDialog.setTitle("Synchronize process");
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // get from JSON
        JsonAccount jsonAccount = new JsonAccount();
        jsonAccount.setIpAddres(ipServer);
        String json = jsonAccount.getServiceProvide(idUser);
        if(json != null && !json.isEmpty()){
            String splitJson[] = json.split("#");
            respon = splitJson[0];
            if(respon.equalsIgnoreCase("Succes")){
                Gson gson = new Gson();
                JsonParser jp = new JsonParser();
                JsonObject jo = (JsonObject) jp.parse(splitJson[1]);
                JsonArray jaRequestDetail = jo.getAsJsonArray("serviceProvide");

                for (JsonElement jsonElement : jaRequestDetail) {
                    ServiceProvide rd = gson.fromJson(jsonElement, ServiceProvide.class);
                    listServiceProvide.add(rd);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        mProgressDialog.dismiss();
        CharSequence textToast = "";
        Toast toast;

        if(respon.equalsIgnoreCase("Succes")){
            textToast = "Synchronise Succes";
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();
        } else if (respon.equalsIgnoreCase("Error")){
            textToast = "Synchronize failed";
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();
        }

    }
}
















