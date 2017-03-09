package com.rohim.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nurochim on 08/10/2016.
 */

@DatabaseTable(tableName = ServiceProvide.tbl_service_provice)
public class ServiceProvide {
    public static final String tbl_service_provice = "TBL_SERVICE_PROVIDE";
    public static final String clm_id_service_provide = "ID_SERVICE_PROVIDE";
    public static final String clm_fid_sercive = "FID_SERVICE";
    public static final String clm_service_name = "SERVICE_NAME";
    public static final String clm_status = "STATUS";
    public static final String clm_description = "DESCRIPTION";
    public static final String clm_pengalaman = "PENGALAMAN";
    public static final String clm_fid_user = "FID_USER";

    @DatabaseField(id = true, columnName = clm_id_service_provide)
    String idServiceProvide;
    @DatabaseField(columnName = clm_fid_sercive)
    String fidService;
    @DatabaseField(columnName = clm_service_name)
    String serviceName;
    @DatabaseField(columnName = clm_description)
    String description;
    @DatabaseField(columnName = clm_status)
    String status;
    @DatabaseField(columnName = clm_pengalaman)
    String pengalaman;
    @DatabaseField(columnName = clm_fid_user)
    String fidUser;

    public ServiceProvide(){

    }

    public ServiceProvide(String idServiceProvide, String fidService, String serviceName, String description, String status, String pengalaman, String fidUser) {
        this.idServiceProvide = idServiceProvide;
        this.fidService = fidService;
        this.serviceName = serviceName;
        this.description = description;
        this.status = status;
        this.pengalaman = pengalaman;
        this.fidUser = fidUser;
    }

    public String getIdServiceProvide() {
        return idServiceProvide;
    }

    public void setIdServiceProvide(String idServiceProvide) {
        this.idServiceProvide = idServiceProvide;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFidService() {
        return fidService;
    }

    public void setFidService(String fidService) {
        this.fidService = fidService;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
    }

    public String getFidUser() {
        return fidUser;
    }

    public void setFidUser(String fidUser) {
        this.fidUser = fidUser;
    }
}
