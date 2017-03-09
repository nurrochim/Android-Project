package com.rohim.asyncTaskServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohim.json_controller.JsonServiceRequestAccept;

/**
 * Created by Asus on 10/12/2016.
 */

public class IgnoreRequestTaskToServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    Context context;
    String idRequest, idUserCreate, idUserAccept, respon;
    FragmentManager fragmentManager;
    Button btnAcceptFinish, btnIgnoreCancel;

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public String getIdUserCreate() {
        return idUserCreate;
    }

    public void setIdUserCreate(String idUserCreate) {
        this.idUserCreate = idUserCreate;
    }

    public String getIdUserAccept() {
        return idUserAccept;
    }

    public void setIdUserAccept(String idUserAccept) {
        this.idUserAccept = idUserAccept;
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

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Button getBtnAcceptFinish() {
        return btnAcceptFinish;
    }

    public void setBtnAcceptFinish(Button btnAcceptFinish) {
        this.btnAcceptFinish = btnAcceptFinish;
    }

    public Button getBtnIgnoreCancel() {
        return btnIgnoreCancel;
    }

    public void setBtnIgnoreCancel(Button btnIgnoreCancel) {
        this.btnIgnoreCancel = btnIgnoreCancel;
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
        JsonServiceRequestAccept jsonServiceRequestAccept = new JsonServiceRequestAccept();
        jsonServiceRequestAccept.setIpAddres(ipServer);

        respon = jsonServiceRequestAccept.ignoreRequest(idRequest, idUserAccept);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        //super.onPostExecute(result);
        CharSequence textToast = "";
        Toast toast;

        if(respon.equalsIgnoreCase("Succes")){
//            btnAcceptFinish.setVisibility(View.GONE);
//            btnIgnoreCancel.setVisibility(View.GONE);
            textToast = "Succes Synchronize";
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();

        }else{
            textToast = "Maaf... Sepertinya ada masalah \n kami akan memperbaikinya segera";
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();
        }

        mProgressDialog.dismiss();
    }
}
