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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.dao.Dao;
import com.rohim.common.DatabaseHelper;
import com.rohim.fragment.FragmentRequestActiveTask;
import com.rohim.jasaservice.MainActivity;
import com.rohim.json_controller.JsonAccount;
import com.rohim.json_controller.JsonServiceRequestAccept;
import com.rohim.modal.RequestAccepted;
import com.rohim.modal.RequestDetail;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Asus on 10/12/2016.
 */

public class getRequestAcceptedFromServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    String idRequest, idUserPenyediaJasa;
    Context context;
    RequestAccepted requestAccepted;
    List<RequestDetail> requestDetailList = new ArrayList<>();

    public String getIdUserPenyediaJasa() {
        return idUserPenyediaJasa;
    }

    public void setIdUserPenyediaJasa(String idUserPenyediaJasa) {
        this.idUserPenyediaJasa = idUserPenyediaJasa;
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
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

    public RequestAccepted getRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(RequestAccepted requestAccepted) {
        this.requestAccepted = requestAccepted;
    }

    public List<RequestDetail> getRequestDetailList() {
        return requestDetailList;
    }

    public void setRequestDetailList(List<RequestDetail> requestDetailList) {
        this.requestDetailList = requestDetailList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(activity);
        // Set progressdialog title
        mProgressDialog.setTitle("Please wait");
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // get from JSON
        JsonServiceRequestAccept jsonRequestAccept = new JsonServiceRequestAccept();
        jsonRequestAccept.setIpAddres(ipServer);

        //setUserRespon(jsonAccount.getUserLogin(email,password));
        String json = jsonRequestAccept.getRequestAccept(idRequest,idUserPenyediaJasa);
        if(json!=null){
            if(!json.equals("Error")){
                String split[] = json.split("#");
                String requestAccept = split[0];
                String listRequestDet = split[1];


                Gson gson = new Gson();
                requestAccepted = gson.fromJson(requestAccept, RequestAccepted.class);


                JsonParser jp = new JsonParser();
                JsonObject jo = (JsonObject) jp.parse(listRequestDet);
                JsonArray jaRequestDetail = jo.getAsJsonArray("requestDetail");

                for (JsonElement jsonElement : jaRequestDetail) {
                    RequestDetail rd = gson.fromJson(jsonElement, RequestDetail.class);
                    getRequestDetailList().add(rd);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        mProgressDialog.dismiss();
    }
}
