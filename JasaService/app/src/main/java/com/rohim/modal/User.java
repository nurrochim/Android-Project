package com.rohim.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asus on 28/11/2016.
 */

@DatabaseTable(tableName = User.tbl_user)
public class User {
    public static final String tbl_user = "TBL_USER";
    public static final String clm_id_user = "ID_USER";
    public static final String clm_user_name = "USER_NAME";
    public static final String clm_no_telp = "NO_TELFON";
    public static final String clm_email = "EMAIL";
    public static final String clm_password = "PASSWORD";
    public static final String clm_jenis_user = "JENIS_USER";
    public static final String clm_foto_profil = "FOTO_PROFIL";
    public static final String clm_current_longitude = "CURRENT_LONGITUDE";
    public static final String clm_current_latitude = "CURRENT_LATITUDE";
    public static final String clm_status = "STATUS";
    public static final String clm_token = "TOKEN";

    public User(){
    }

    public User(String idUser, String userName, String noTelp, String email, String password, String jenisUser, String fotoProfil, String currentLongitude, String currentLatitude, String status, String token) {
        this.idUser = idUser;
        this.userName = userName;
        this.noTelp = noTelp;
        this.email = email;
        this.password = password;
        this.jenisUser = jenisUser;
        this.fotoProfil = fotoProfil;
        this.currentLongitude = currentLongitude;
        this.currentLatitude = currentLatitude;
        this.status = status;
        this.token = token;
    }

    @DatabaseField(id = true, columnName = clm_id_user)
    String idUser;
    @DatabaseField(columnName = clm_user_name)
    String userName;
    @DatabaseField(columnName = clm_no_telp)
    String noTelp;
    @DatabaseField(columnName = clm_email)
    String email;
    @DatabaseField(columnName = clm_password)
    String password;
    @DatabaseField(columnName = clm_jenis_user)
    String jenisUser;
    @DatabaseField(columnName = clm_foto_profil)
    String fotoProfil;
    @DatabaseField(columnName = clm_current_longitude)
    String currentLongitude;
    @DatabaseField(columnName = clm_current_latitude)
    String currentLatitude;
    @DatabaseField(columnName = clm_status)
    String status;
    @DatabaseField(columnName = clm_token)
    String token;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenisUser() {
        return jenisUser;
    }

    public void setJenisUser(String jenisUser) {
        this.jenisUser = jenisUser;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
