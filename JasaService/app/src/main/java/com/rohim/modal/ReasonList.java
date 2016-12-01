package com.rohim.modal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asus on 28/11/2016.
 */
@DatabaseTable(tableName = ReasonList.tbl_reason_list)
public class ReasonList {
    public static final String tbl_reason_list = "TBL_REASON_LIST";
    public static final String clm_id_reason = "ID_REASON";
    public static final String clm_parameter = "PARAMETER";
    public static final String clm_desription = "DESCRIPTION";

    @DatabaseField(id=true, columnName = clm_id_reason, dataType = DataType.INTEGER)
    int idReason;
    @DatabaseField(columnName = clm_parameter)
    String parameter;
    @DatabaseField(columnName = clm_desription)
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
