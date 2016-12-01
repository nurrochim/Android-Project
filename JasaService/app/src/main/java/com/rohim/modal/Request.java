package com.rohim.modal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Asus on 28/11/2016.
 */

@DatabaseTable(tableName = Request.tbl_request)
public class Request {
    public static final String tbl_request = "TBL_REQUEST";
    public static final String clm_id_request = "ID_REQUEST";
    public static final String clm_fid_user_create = "FID_USER_CREATE";
    public static final String clm_fid_user_accept = "FID_USER_ACCEPT";
    public static final String clm_fid_service = "FID_SERVICE";
    public static final String clm_fid_service_provide = "FID_SERVICE_PROVIDE";
    public static final String clm_status = "STATUS";
    public static final String clm_gps_image = "GPS_IMAGE";
    public static final String clm_longitude = "LONGITUDE";
    public static final String clm_latitude = "LATITUDE";
    public static final String clm_addres = "ADDRES";
    public static final String clm_addres_description = "ADDRES_DESCRIPTION";
    public static final String clm_start_time = "START_TIME";
    public static final String clm_finish_time = "FINISH_TIME";
    public static final String clm_create_date = "CREATE_DATE";
    public static final String clm_finish_comment_user = "FINISH_COMMENT_USER";
    public static final String clm_hasil_service = "HASIL_SERVICE";

    @DatabaseField(id = true, columnName = clm_id_request)
    String idRequest;
    @DatabaseField(columnName = clm_fid_user_create)
    String fidUserCreate;
    @DatabaseField(columnName = clm_fid_user_accept)
    String fidUserAccept;
    @DatabaseField(columnName = clm_fid_service)
    String fidService;
    @DatabaseField(columnName = clm_fid_service_provide)
    String fidServiceProvide;
    @DatabaseField(columnName = clm_status)
    String status;
    @DatabaseField(columnName = clm_gps_image)
    String gpsImage;
    @DatabaseField(columnName = clm_longitude)
    String longitude;
    @DatabaseField(columnName = clm_latitude)
    String latitude;
    @DatabaseField(columnName = clm_addres)
    String addres;
    @DatabaseField(columnName = clm_addres_description)
    String addresDescrition;
    @DatabaseField(columnName = clm_start_time)
    String startTime;
    @DatabaseField(columnName = clm_finish_time)
    String finishTime;
    @DatabaseField(columnName = clm_create_date, dataType = DataType.DATE)
    Date createDate;
    @DatabaseField(columnName = clm_finish_comment_user)
    String finishCommentUser;
    @DatabaseField(columnName = clm_hasil_service)
    String hasilService;

    public Request() {
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public String getFidUserCreate() {
        return fidUserCreate;
    }

    public void setFidUserCreate(String fidUserCreate) {
        this.fidUserCreate = fidUserCreate;
    }

    public String getFidUserAccept() {
        return fidUserAccept;
    }

    public void setFidUserAccept(String fidUserAccept) {
        this.fidUserAccept = fidUserAccept;
    }

    public String getFidService() {
        return fidService;
    }

    public void setFidService(String fidService) {
        this.fidService = fidService;
    }

    public String getFidServiceProvide() {
        return fidServiceProvide;
    }

    public void setFidServiceProvide(String fidServiceProvide) {
        this.fidServiceProvide = fidServiceProvide;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGpsImage() {
        return gpsImage;
    }

    public void setGpsImage(String gpsImage) {
        this.gpsImage = gpsImage;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getAddresDescrition() {
        return addresDescrition;
    }

    public void setAddresDescrition(String addresDescrition) {
        this.addresDescrition = addresDescrition;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFinishCommentUser() {
        return finishCommentUser;
    }

    public void setFinishCommentUser(String finishCommentUser) {
        this.finishCommentUser = finishCommentUser;
    }

    public String getHasilService() {
        return hasilService;
    }

    public void setHasilService(String hasilService) {
        this.hasilService = hasilService;
    }
}
