package entity;



import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by Nurochim on 17/10/2016.
 */

public class Service implements Serializable{
    public static final String tbl_service = "TBL_SERVICE";
    public static final String clm_id_service = "ID_SERVICE";
    public static final String clm_service_name = "SERVICE_NAME";
    public static final String clm_service_icon = "SERVICE_ICON";

    String idService;
    String serviceName;
    String icon = "";

    public Service(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
