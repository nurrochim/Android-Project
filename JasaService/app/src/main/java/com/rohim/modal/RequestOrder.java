package com.rohim.modal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Asus on 28/11/2016.
 */
@DatabaseTable(tableName = RequestOrder.tbl_request_order)
public class RequestOrder {
    public static final String tbl_request_order = "TBL_REQUEST_ORDER";
    public static final String clm_id_request = "ID_REQUEST";
    public static final String clm_fid_service = "FID_SERVICE";
    public static final String clm_fid_user = "FID_USER";
    public static final String clm_user_name = "USER_NAME";
    public static final String clm_user_foto_profil = "USER_FOTO_PROFIL";
    public static final String clm_user_no_telfon = "USER_NO_TELFON";
    public static final String clm_status = "STATUS";
    public static final String clm_start_time = "START_TIME";
    public static final String clm_finish_time = "FINISH_TIME";
    public static final String clm_create_date = "CREATE_DATE";

    @DatabaseField(id = true, columnName = clm_id_request)
    String idRequest;
    @DatabaseField(columnName = clm_fid_user)
    String fidUserCreate;
    @DatabaseField(columnName = clm_fid_service)
    String fidService;
    @DatabaseField(columnName = clm_user_name)
    String userName;
    @DatabaseField(columnName = clm_user_foto_profil)
    String userFotoProfil;
    @DatabaseField(columnName = clm_user_no_telfon)
    String userNoTelfon;
    @DatabaseField(columnName = clm_status)
    String status;
    @DatabaseField(columnName = clm_start_time)
    String startTime;
    @DatabaseField(columnName = clm_finish_time)
    String finishTime;
    @DatabaseField(columnName = clm_create_date, dataType = DataType.DATE)
    Date createDate;

    public RequestOrder() {
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

    public String getFidService() {
        return fidService;
    }

    public void setFidService(String fidService) {
        this.fidService = fidService;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFotoProfil() {
        return userFotoProfil;
    }

    public void setUserFotoProfil(String userFotoProfil) {
        this.userFotoProfil = userFotoProfil;
    }

    public String getUserNoTelfon() {
        return userNoTelfon;
    }

    public void setUserNoTelfon(String userNoTelfon) {
        this.userNoTelfon = userNoTelfon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
