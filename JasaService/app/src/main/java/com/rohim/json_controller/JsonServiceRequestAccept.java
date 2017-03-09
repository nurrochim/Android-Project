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
        String json = jsonParser.postFromUrl(getFullPathService()+postAcceptedRequestTask+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept+"&idUserCreate="+idUserCreate);
        return json;
    }

    public String ignoreRequest(String idRequest, String idUserAccept){
        String json = jsonParser.postFromUrl(getFullPathService()+postIgnoreRequestTask+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept);
        return json;
    }

    public String cancelRequest(String idRequest, String idUserAccept, String idUserCreated, String reason){
        String json = jsonParser.postFromUrl(getFullPathService()+postCancelRequestTask+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept+"&idUserCreated="+idUserCreated+"&reason="+reason);
        return json;
    }

    public String finishRequest(String idRequest,String idUserAccept){
        String json = jsonParser.postFromUrl(getFullPathService()+postFinishRequestTask+"?idRequest="+idRequest+"&idUserAccepted="+idUserAccept);
        return json;
    }
}
