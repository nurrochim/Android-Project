package com.rohim.json_controller;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonServerTest extends BaseJsonService{
    public String testConection(String listDataJson){
        String respon = jsonParser.getJSONFromUrl(getFullPathService()+getConectionTest, null);
        return respon;
    }
}
