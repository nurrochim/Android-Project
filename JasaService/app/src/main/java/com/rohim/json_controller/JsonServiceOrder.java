package com.rohim.json_controller;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonServiceOrder extends BaseJsonService{
    public String addServiceOrder(String listDataJson){
        String respon = jsonParser.setPost(getFullPathService()+addRequestOrder, listDataJson);
        return respon;
    }
}
