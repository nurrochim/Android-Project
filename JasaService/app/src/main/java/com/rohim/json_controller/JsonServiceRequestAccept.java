package com.rohim.json_controller;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonServiceRequestAccept extends BaseJsonService{
    public String getRequestAccept(String idRequest, String idUserPenyediaJasa){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserPenyediaJasa="+idUserPenyediaJasa, null);
        return json;
    }

    public String acceptRequest(String idRequest, String idUserCreate,String idUserAccept){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept+"&idUserCreate="+idUserCreate, null);
        return json;
    }

    public String ignoreRequest(String idRequest, String idUserAccept){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept, null);
        return json;
    }

    public String cancelRequest(String idRequest, String idUserAccept, String idUserCreated, String reason){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept+"&idUserCreated="+idUserCreated+"&reason"+reason, null);
        return json;
    }

    public String finishRequest(String idRequest, String idUserCreate,String idUserAccept){
        String json = jsonParser.getJSONFromUrl(getFullPathService()+getRequestAccept+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept+"&idUserCreate="+idUserCreate, null);
        return json;
    }
}
