package com.rohim.modal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Asus on 28/11/2016.
 */

@DatabaseTable(tableName = HistoryRequest.tbl_history_request)
public class HistoryRequest {
    public static final String tbl_history_request = "TBL_HISTORY_REQUEST";
    public static final String clm_id_history_request = "ID_HISTORY_REQUEST";
    public static final String clm_fid_request = "FID_REQUEST";
    public static final String clm_fid_reason = "ID_REASON";
    public static final String clm_status = "STATUS";
    public static final String clm_reason = "REASON";
    public static final String clm_create_by = "CREATE_BY";
    public static final String clm_create_date = "CREATE_DATE";

    @DatabaseField(id = true, columnName = clm_id_history_request)
    String idHistoryRequest;
    @DatabaseField(columnName = clm_fid_request)
    String fidRequest;
    @DatabaseField(columnName = clm_fid_reason)
    String fidReason;
    @DatabaseField(columnName = clm_status)
    String status;
    @DatabaseField(columnName = clm_reason)
    String reason;
    @DatabaseField(columnName = clm_create_by)
    String createBy;
    @DatabaseField(columnName = clm_create_date, dataType = DataType.DATE)
    Date createDate;

    public HistoryRequest() {
    }

    public String getIdHistoryRequest() {
        return idHistoryRequest;
    }

    public void setIdHistoryRequest(String idHistoryRequest) {
        this.idHistoryRequest = idHistoryRequest;
    }

    public String getFidRequest() {
        return fidRequest;
    }

    public void setFidRequest(String fidRequest) {
        this.fidRequest = fidRequest;
    }

    public String getFidReason() {
        return fidReason;
    }

    public void setFidReason(String fidReason) {
        this.fidReason = fidReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
