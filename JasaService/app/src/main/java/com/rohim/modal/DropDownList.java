package com.rohim.modal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Nurochim on 19/10/2016.
 */

@DatabaseTable(tableName = DropDownList.tbl_dropDown_list)
public class DropDownList extends BaseDaoEnabled implements Serializable {
    public static final String tbl_dropDown_list = "TBL_DROPDOWN_LIST";
    public static final String clm_id_dropwodn = "ID_DROPDOWN";
    public static final String clm_fid_service_item = "FID_SERVICE_ITEM";
    public static final String clm_description = "DESCRIPTION";
    public static final String clm_alias = "ALIAS";

    @DatabaseField(generatedId = true, columnName = clm_id_dropwodn)
    Integer idDropDown;
    @DatabaseField(columnName = clm_fid_service_item, dataType = DataType.INTEGER)
    int fidServiceItem;
    @DatabaseField(columnName = clm_description)
    String description = "";
    @DatabaseField(columnName = clm_alias)
    String alias = "";

    public DropDownList(){

    }
    public DropDownList(int fidServiceItem, String description) {
        this.fidServiceItem = fidServiceItem;
        this.description = description;
    }

    public Integer getIdDropDown() {
        return idDropDown;
    }

    public void setIdDropDown(Integer idDropDown) {
        this.idDropDown = idDropDown;
    }

    public int getFidServiceItem() {
        return fidServiceItem;
    }

    public void setFidServiceItem(int fidServiceItem) {
        this.fidServiceItem = fidServiceItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
