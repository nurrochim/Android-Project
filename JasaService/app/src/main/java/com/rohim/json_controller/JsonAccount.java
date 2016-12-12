package com.rohim.json_controller;

import android.util.Log;

import com.google.gson.Gson;
import com.rohim.modal.User;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonAccount extends BaseJsonService{

//    public HashMap<String, Object> getUserLogin(String email, String password){
//        String respon = "";
//        User user = new User();
//
//        try {
//
//            ClientRequest request = new ClientRequest(getFullPathService()+getUserLogin+"?email="+email+"&password="+password);
//            request.accept("application/json");
//            ClientResponse<String> response = request.get(String.class);
//
//            if (response.getStatus() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
//
//            output = "";
//            while ((output = br.readLine()) != null) {
//                respon = respon+output;
//            }
//
//            if(!respon.isEmpty()){
//                user = gson.fromJson(respon, User.class);
//            }
//
//            mapOjectReturn.put("Succes", user);
//        } catch (MalformedURLException e) {
//           e.printStackTrace();
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//            mapOjectReturn.put("Succes", respon);
//        } catch (IOException e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//            respon = "Maaf... Server sedang sibuk";
//            mapOjectReturn.put("Succes", respon);
//        } catch (Exception e) {
//            Log.e("Buffer Error", "Error converting result " + e.toString());
//            respon = "Maaf... Server sedang sibuk";
//            mapOjectReturn.put("Succes", respon);
//        }
//
//        return mapOjectReturn;
//    }
//

    public String getUserService(String email, String password){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getUserLogin+"?email="+email+"&password="+password, null);
        return json;
    }

    public String addUserService(User user){
        String respon = jsonParser.setPost(getFullPathService()+addUser, user);
        return respon;
    }

    public String updateUserService(User user){
        String respon = jsonParser.setPost(getFullPathService()+updateUser, user);
        return respon;
    }
}
