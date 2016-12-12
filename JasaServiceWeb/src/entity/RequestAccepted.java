package entity;

import java.util.Date;

/**
 * Created by Asus on 28/11/2016.
 */
public class RequestAccepted {
    public static final String tbl_request_accepted = "TBL_REQUEST_ACCEPTED";
    public static final String clm_id_request = "ID_REQUEST";
    public static final String clm_fid_service = "FID_SERVICE";
    public static final String clm_fid_service_provide = "FID_SERVICE_PROVIDE";
    public static final String clm_fid_user = "FID_USER";
    public static final String clm_client_name = "USER_NAME";
    public static final String clm_client_foto_profil = "USER_FOTO_PROFIL";
    public static final String clm_client_no_telfon = "USER_NO_TELFON";
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

    String idRequest;
    String fidService;
    String fidServiceProvide;
    String fidUserCreate;
    String clientName;
    String clientFotoProfil;
    String clientNoTelfon;
    String status;
    String gpsImage;
    String longitude;
    String latitude;
    String addres;
    String addresDescrition;
    String startTime;
    String finishTime;
    Date createDate;
    String finishCommentUser;
    String hasilService;


    public RequestAccepted() {
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
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

    public void setFidServiceProvide(String fidServiceProvice) {
        this.fidServiceProvide = fidServiceProvice;
    }

    public String getFidUserCreate() {
        return fidUserCreate;
    }

    public void setFidUserCreate(String fidUserCreate) {
        this.fidUserCreate = fidUserCreate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientFotoProfil() {
        return clientFotoProfil;
    }

    public void setClientFotoProfil(String clientFotoProfil) {
        this.clientFotoProfil = clientFotoProfil;
    }

    public String getClientNoTelfon() {
        return clientNoTelfon;
    }

    public void setClientNoTelfon(String clientNoTelfon) {
        this.clientNoTelfon = clientNoTelfon;
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
