package entity;
import java.io.Serializable;

/**
 * Created by Nurochim on 19/10/2016.
 */

public class DropDownList implements Serializable {
    public static final String tbl_dropDown_list = "TBL_DROPDOWN_LIST";
    public static final String clm_id_dropwodn = "ID_DROPDOWN";
    public static final String clm_fid_service_item = "FID_SERVICE_ITEM";
    public static final String clm_description = "DESCRIPTION";
    public static final String clm_alias = "ALIAS";

    Integer idDropDown;
    int fidServiceItem;
    String description = "";
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
