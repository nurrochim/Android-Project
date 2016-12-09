package entity;

import java.io.Serializable;

/**
 * Created by Asus on 28/11/2016.
 */

public class ReasonList implements Serializable{
    public static final String tbl_reason_list = "TBL_REASON_LIST";
    public static final String clm_id_reason = "ID_REASON";
    public static final String clm_parameter = "PARAMETER";
    public static final String clm_desription = "DESCRIPTION";

    int idReason;
    String parameter;
    String description;

    public ReasonList() {
    }

    public int getIdReason() {
        return idReason;
    }

    public void setIdReason(int idReason) {
        this.idReason = idReason;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
