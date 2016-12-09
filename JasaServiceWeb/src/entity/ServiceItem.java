package entity;

import java.io.Serializable;

/**
 * Created by Nurochim on 19/10/2016.
 */

public class ServiceItem implements Serializable{
    public static final String tbl_service_item = "TBL_SERVICE_ITEM";
    public static final String clm_id_service_item = "ID_SERVICE_ITEM";
    public static final String clm_fid_service = "FID_SERVICE";
    public static final String clm_item_name = "ITEM_NAME";
    public static final String clm_jenis_input = "JENIS_INPUT";

    Integer idServiceItem;
    String fidService;
    String itemName;
    String jenisInput;

    public ServiceItem(String fidService, String itemName, String jenisInput) {
        this.fidService = fidService;
        this.itemName = itemName;
        this.jenisInput = jenisInput;
    }

    public ServiceItem(){

    }

    public Integer getIdServiceItem() {
        return idServiceItem;
    }

    public void setIdServiceItem(Integer idServiceItem) {
        this.idServiceItem = idServiceItem;
    }

    public String getFidService() {
        return fidService;
    }

    public void setFidService(String fidService) {
        this.fidService = fidService;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getJenisInput() {
        return jenisInput;
    }

    public void setJenisInput(String jenisInput) {
        this.jenisInput = jenisInput;
    }
}
