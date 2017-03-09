package com.rohim.asyncTaskServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rohim.json_controller.JsonAccount;
import com.rohim.modal.User;

/**
 * Created by Asus on 10/12/2016.
 */

public class updateUserToServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    User user;
    Context context;
    String respon = "";
    String update= "ACCOUNT";

    public User getUser() {
        return user;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setUser(User user) {
        this.user = user;
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

        //setUserRespon(jsonAccount.getUserLogin(email,password));
        respon = jsonAccount.updateUserService(user);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        mProgressDialog.dismiss();
        CharSequence textToast = "";
        Toast toast;

        if(!update.equals("TOKEN")) {
            if (respon.equalsIgnoreCase("Succes")) {
                textToast = "UPDATE " + update + " BERHASIL";
                toast = Toast.makeText(context, textToast, Toast.LENGTH_SHORT);
                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                if (textView != null) textView.setGravity(Gravity.CENTER);
                toast.show();
            } else {
                textToast = "MAAF.. UPDATE " + update + " GAGAL...  \n SERVER SEDANG SIBUK";
                toast = Toast.makeText(context, textToast, Toast.LENGTH_SHORT);
                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                if (textView != null) textView.setGravity(Gravity.CENTER);
                toast.show();
            }
        }

    }
}

