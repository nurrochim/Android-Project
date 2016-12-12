package com.rohim.json_controller;

import com.rohim.modal.ServiceProvide;

/**
 * Created by Asus on 12/12/2016.
 */

public class JsonServiceProvide extends BaseJsonService{
    public String addServiceProvide(String listServiceProvides){
        String respon = jsonParser.setPost(getFullPathService()+addServiceProvides, listServiceProvides);
        return respon;
    }
    public String deleteServiceProvide(ServiceProvide serviceProvide){
        String respon = jsonParser.setPost(getFullPathService()+deleteServiceProvides, serviceProvide);
        return respon;
    }
}
