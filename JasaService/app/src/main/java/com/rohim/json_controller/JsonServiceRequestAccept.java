package com.rohim.json_controller;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonServiceRequestAccept extends BaseJsonService{
    public String getRequestAccept(String idRequest, String idUserPenyediaJasa){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserPenyediaJasa="+idUserPenyediaJasa, null);
        return json;
    }
}
