package com.rohim.json_controller;

import org.json.JSONObject;

/**
 * Created by Asus on 08/12/2016.
 */

public class WebServiceStatus extends BaseJsonService{
    public JSONObject getStatusService(){
        JSONObject json = jsonParser.getJSONFromUrl(ipAddres+URLWebService+"status", null);
        return json;
    }
}
