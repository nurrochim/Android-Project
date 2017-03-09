package com.rohim.asyncTaskServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.rohim.common.DatabaseHelper;
import com.rohim.jasaservice.LoginActivity;
import com.rohim.jasaservice.MainActivity;
import com.rohim.json_controller.JsonAccount;
import com.rohim.modal.User;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Asus on 10/12/2016.
 */

public class loginToServer extends AsyncTask<Void, Void, Void>{
    private View view;
    ProgressDialog mProgressDialog;
    Activity activity;
    String ipServer;
    HashMap<String, Object> userRespon = new HashMap<>();
    String email, password;
    Context context;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        JsonAccount jsonAccount = new JsonAccount();
        jsonAccount.setIpAddres(ipServer);

        //setUserRespon(jsonAccount.getUserLogin(email,password));
        String json = jsonAccount.getUserService(email,password);
        if(json!=null && !json.isEmpty()){
            String split[] = json.split("/");
            userRespon.put(split[0],split[1]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        //super.onPostExecute(result);
        CharSequence textToast = "";
        Toast toast;

        HashMap<String, Object> userRespon = getUserRespon();
        if(userRespon.get("Succes")!=null){
            Gson gson = new Gson();
            User user = gson.fromJson(userRespon.get("Succes").toString(), User.class);

            textToast = "LOGIN BERHASIL \n SELAMAT DATANG "+user.getUserName();
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();

            DatabaseHelper dbh = new DatabaseHelper(getActivity());

            try {
                // get token for Firebase notification
                String token = FirebaseInstanceId.getInstance().getToken();
                user.setToken(token);

                Dao<User, String> userDao = dbh.getUserDao();
                userDao.create(user);
                dbh.close();

                SharedPreferences.Editor editorSharedPreference = context.getSharedPreferences("ReUse_Variable", Context.MODE_PRIVATE).edit();;
                editorSharedPreference.putString("IdUser", user.getIdUser());
                editorSharedPreference.commit();

                updateUserToServer updateUser = new updateUserToServer();
                updateUser.setIpServer(ipServer);
                updateUser.setActivity(getActivity());

                updateUser.setUser(user);
                updateUser.setContext(context);
                updateUser.setUpdate("TOKEN");
                updateUser.execute();

                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            } catch (SQLException e) {
                e.printStackTrace();
                textToast = "SYSTEM ERROR... \n LOGIN GAGAL";
                toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
                TextView textViews = (TextView) toast.getView().findViewById(android.R.id.message);
                if( textViews != null) textView.setGravity(Gravity.CENTER);
                toast.show();
            }
        }else{
            textToast = "LOGIN GAGAL \n EMAIL ATAU PASSWORD SALAH...  ";
            toast = Toast.makeText(context,textToast, Toast.LENGTH_SHORT);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            if( textView != null) textView.setGravity(Gravity.CENTER);
            toast.show();
        }
        mProgressDialog.dismiss();
    }
}
