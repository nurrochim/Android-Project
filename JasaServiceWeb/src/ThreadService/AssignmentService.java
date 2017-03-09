package ThreadService;

import java.util.List;

import org.hibernate.Session;

import Rest.PushNotificationToFirebase;
import hibernate.HibernateHelper;

public class AssignmentService implements Runnable{
	
	String idRequest;
	
	

	public String getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		doAssignmentTask();
	}
	
	private void doAssignmentTask(){
		boolean closeAfter = false;
		try {
	        closeAfter = HibernateHelper.beginTx();
	        Session session = HibernateHelper.getSession();
	        session.beginTransaction();
	        
	        String SQL = "EXECUTE DBO.ASSIGNMENT_TASK '"+idRequest+"'";
	        List<Object[]> listObject = session.createSQLQuery(SQL).list();
	        if(listObject!=null && listObject.size()>0){
	        	Object[] obj = listObject.get(0);
	        	String token = obj[0].toString();
	        	String idRequestOrder = obj[4].toString();
	        	String serviceName = obj[5].toString();
	        	String idPenyediaJasa = obj[6].toString();
	        	
	        	PushNotificationToFirebase pushNotif = new PushNotificationToFirebase();
		        pushNotif.pushNotificationToClient(token, "RO#"+idRequestOrder+"#"+idPenyediaJasa, serviceName);
	        }
	        
	        
	        HibernateHelper.commitTx(closeAfter);
	    } catch (Exception e) {
	        HibernateHelper.rollbackTx(closeAfter);
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		AssignmentService assignmentService = new AssignmentService();
		assignmentService.setIdRequest("PENCARI_1481738533286/1_1481747434744");
		new Thread(assignmentService).start();
	}

}
