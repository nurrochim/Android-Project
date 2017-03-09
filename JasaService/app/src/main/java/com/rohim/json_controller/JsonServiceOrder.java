package com.rohim.json_controller;

import com.rohim.modal.Request;
import com.rohim.modal.RequestOrder;

/**
 * Created by Asus on 08/12/2016.
 */

public class JsonServiceOrder extends BaseJsonService{
    public String addServiceOrder(String listDataJson){
        String respon = jsonParser.setPost(getFullPathService()+addRequestOrder, listDataJson);
        return respon;
    }
    public String cancelServiceOrder(String idRequest, String idUserCreate, String reason){
        String respon = jsonParser.postFromUrl(getFullPathService()+postCancelRequestOrder+"?idRequest="+idRequest+"&idUserCreated="+idUserCreate+"&reason="+reason);
        return respon;
    }
    public String addCommentService(Request request){
        String respon = jsonParser.setPost(getFullPathService()+postComentarClient, request);
        return respon;
    }
}
