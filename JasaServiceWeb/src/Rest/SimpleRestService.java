package Rest;


import java.util.List;

import hibernate.HibernateHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import entity.User;


@Path("/jasaService")
public class SimpleRestService {

	private static final Logger logger = Logger.getLogger(SimpleRestService.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("status")
	public Response getStatus() {
		//pushNotificationToFirebase.main(null);
		boolean closeAfter = false;
		try {
	        closeAfter = HibernateHelper.beginTx();
	        Session session = HibernateHelper.getSession();
	        
	        List<Object[]> user = session.createSQLQuery("Select * from TBL_USER").list();
	        for (Object object[] : user) {
	        	System.out.println(object[0]);
	        	System.out.println(object[1]);
	        	System.out.println(object[2]);
	        	System.out.println(object[3]);
	        }
	        
	        session.beginTransaction();
	        HibernateHelper.commitTx(closeAfter);
	    } catch (Exception e) {
	        HibernateHelper.rollbackTx(closeAfter);
	        e.printStackTrace();
	    }
		return Response.ok(
				"{\"status\":\"Web Service is running...\"}").build();
	}
	
	@POST
	@Path("/postAddUser")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response postAddUser(
			User user) {
		String response = null;
		
		boolean closeAfter = false;
		try {
	        closeAfter = HibernateHelper.beginTx();
	        Session session = HibernateHelper.getSession();
	        session.save(user);
	        session.flush();
	        response = "Succes";
	        session.beginTransaction();
	        HibernateHelper.commitTx(closeAfter);
	    } catch (Exception e) {
	        HibernateHelper.rollbackTx(closeAfter);
	        e.printStackTrace();
	        response = "Error";
	    }
        return Response.status(201).entity(response).build();	
	}
	
	@POST
	@Path("/postUpdateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUpdateUser(User user) {
		String response = null;
		
		boolean closeAfter = false;
		try {
	        closeAfter = HibernateHelper.beginTx();
	        Session session = HibernateHelper.getSession();
	        
	        session.update(user);
	        session.flush();
	        
	        response = "Succes";
	        session.beginTransaction();
	        HibernateHelper.commitTx(closeAfter);
	    } catch (Exception e) {
	        HibernateHelper.rollbackTx(closeAfter);
	        e.printStackTrace();
	        response = "Error";
	    }
		return Response.status(201).entity(response).build();
	}
	
	
	@GET
	@Path("/postGetUser")
    @Produces(MediaType.APPLICATION_JSON)
	public Response postGetUser(
			@QueryParam("email") String email,
			@QueryParam("password") String password
			) {
		String response = null;
		String json = "";
		boolean closeAfter = false;
		try {
	        closeAfter = HibernateHelper.beginTx();
	        Session session = HibernateHelper.getSession();
	        
	        Criteria crt = session.createCriteria(User.class)
	        				.add(Restrictions.eq("email", email))
	        				.add(Restrictions.eq("password", password));
	        User user = (User) crt.setMaxResults(1).uniqueResult();
	        if(user != null){
	        	json = new Gson().toJson(user);
	        }
	        
	        response = "Succes";
	        session.beginTransaction();
	        HibernateHelper.commitTx(closeAfter);
	    } catch (Exception e) {
	        HibernateHelper.rollbackTx(closeAfter);
	        e.printStackTrace();
	        response = "Error";
	    }
        //return response;	
		return Response.ok("{ User : "+json+"}").build();
	}

	/*
	@GET
	@Path("/<add method name here>")
    @Produces(MediaType.TEXT_PLAIN)
	public String getSomething(@QueryParam("request") String request ,
			 @DefaultValue("1") @QueryParam("version") int version) {

		if (logger.isDebugEnabled()) {
			logger.debug("Start getSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from RESTEasy Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End getSomething");
        }
        return response;	
	}

	@POST
	@Path("/<add method name here>")
    @Produces(MediaType.TEXT_PLAIN)
	public String postSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {

		if (logger.isDebugEnabled()) {
			logger.debug("Start postSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from RESTEasy Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End postSomething");
        }
        return response;	
	}

	@PUT
	@Path("/<add method name here>")
    @Produces(MediaType.TEXT_PLAIN)
	public String putSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start putSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from RESTEasy Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End putSomething");
        }
        return response;	
	}

	@DELETE
	@Path("/<add method name here>")
	public void deleteSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Start deleteSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}


        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("End deleteSomething");
        }
	}*/
}
