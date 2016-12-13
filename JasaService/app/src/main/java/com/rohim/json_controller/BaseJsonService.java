package com.rohim.json_controller;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Asus on 08/12/2016.
 */

public class BaseJsonService {
    public JsonParser jsonParser = new JsonParser();
    public String ipAddres = "";
    public static String URLWebService = "/JasaServiceWeb/rs/jasaService/";
    public static String getUserLogin = "getUser";
    public static String addUser = "postAddUser";
    public static String updateUser = "postUpdateUser";
    public static String addServiceProvides = "postAddServiceProvides";
    public static String deleteServiceProvides = "postDeleteServiceProvides";
    public static String addRequestOrder = "postAddRequestOrder";
    public static String getRequestAccept = "getRequestAccept";
    public static String postAcceptedRequestTask = "postAcceptedRequestTask";
    public static String postIgnoreRequestTask = "postIgnoreRequestTask";
    public static String postFinishRequestTask = "postFinishRequestTask";


    public Gson gson = new Gson();
    String output;
    HashMap<String, Object> mapOjectReturn;
    public String fullPathService;

    public String getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(String ipAddres) {
        this.ipAddres = ipAddres;
    }

    public String getFullPathService() {
        return "http://"+getIpAddres()+":8080"+URLWebService;
    }

    public String getStatusService(){
        String respons = jsonParser.getJSONFromUrl(getFullPathService()+"status", null);
        return respons;
    }

}
