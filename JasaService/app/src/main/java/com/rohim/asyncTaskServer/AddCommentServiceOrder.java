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
import com.rohim.json_controller.JsonServiceOrder;
import com.rohim.modal.Request;
import com.rohim.modal.RequestOrder;
import com.rohim.modal.User;

/**
 * Created by Asus on 10/12/2016.
 */

public class AddCommentServiceOrder extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    RequestOrder requestOrder;
    Context context;
    String respon = "";

    public RequestOrder getRequestOrder() {
        return requestOrder;
    }

    public void setRequestOrder(RequestOrder requestOrder) {
        this.requestOrder = requestOrder;
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
        JsonServiceOrder jsonServiceOrder = new JsonServiceOrder();
        jsonServiceOrder.setIpAddres(ipServer);

        Request request = new Request();
        request.setIdRequest(requestOrder.getIdRequest());
        request.setFinishCommentUser(requestOrder.getFinishCommentUser());
        request.setHasilService(requestOrder.getHasilService());
        respon = jsonServiceOrder.addCommentService(request);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        mProgressDialog.dismiss();
        CharSequence textToast = "";
        Toast toast;

        if(respon.equalsIgnoreCase("Succes")){
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

    }
}
















