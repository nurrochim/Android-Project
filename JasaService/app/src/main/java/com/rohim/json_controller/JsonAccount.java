package com.rohim.json_controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonAccount extends BaseJsonService{
    public JSONArray getUserService(String idUser, String password){
        JSONObject json = jsonParser.getJSONFromUrl(ipAddres+URLWebService+getUserService+"idUser="+idUser+"&password="+password, null);
        JSONArray jsonArray = null;
        JSONObject dataUser = null;
        try {
            jsonArray = json.getJSONArray("userBase");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonArray;
    }
}
