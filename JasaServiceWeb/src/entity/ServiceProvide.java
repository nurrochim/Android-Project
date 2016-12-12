package entity;


/**
 * Created by Nurochim on 08/10/2016.
 */

public class ServiceProvide {
    public static final String tbl_service_provice = "TBL_SERVICE_PROVIDE";
    public static final String clm_id_service_provide = "ID_SERVICE_PROVIDE";
    public static final String clm_fid_sercive = "FID_SERVICE";
    public static final String clm_service_name = "SERVICE_NAME";
    public static final String clm_status = "STATUS";
    public static final String clm_description = "DESCRIPTION";
    public static final String clm_pengalaman = "PENGALAMAN";

    String idServiceProvide;
    String fidService;
    String serviceName;
    String description;
    String status;
    String pengalaman;
    String fidUser;

    public ServiceProvide(){

    }

    public ServiceProvide(String idServiceProvide, String fidService, String serviceName, String description, String status, String pengalaman) {
        this.idServiceProvide = idServiceProvide;
        this.fidService = fidService;
        this.serviceName = serviceName;
        this.description = description;
        this.status = status;
        this.pengalaman = pengalaman;
    }

    public String getIdServiceProvide() {
        return idServiceProvide;
    }

    public void setIdServiceProvide(String idServiceProvide) {
        this.idServiceProvide = idServiceProvide;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFidService() {
        return fidService;
    }

    public void setFidService(String fidService) {
        this.fidService = fidService;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
    }

	public String getFidUser() {
		return fidUser;
	}

	public void setFidUser(String fidUser) {
		this.fidUser = fidUser;
	}
    
    
}
