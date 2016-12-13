package Rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;

import model.FirebaseBody;
import model.FirebaseNotification;

public class PushNotificationToFirebase implements Runnable{
	public void pushNotificationToClient(String token, String msgTitle, String msgBody) {
		  try {

			ClientRequest request = new ClientRequest("https://gcm-http.googleapis.com/gcm/send");
			request.accept("application/json");
			request.header("Authorization", "key=AAAA1oUTkvM:APA91bGJ5gJ2xJYm9dYvUkJ8NmMq4v-AlFp-R-eBkP71UvgKwc_9aZOko9rLORYsCb0uD7Y56hBijk-otwnwz7ylW9ujFlVMlcAZjbfzinS-DRG7Fs-4acnwstPgeHXYvLMWI_wlNuy3SIuNExRxAt-nUjPu1tpdhg");
			
			FirebaseNotification notification = new FirebaseNotification();
			notification.setBody(msgBody);
			notification.setTitle(msgTitle);
			
			FirebaseBody firebaseBody = new FirebaseBody();
			firebaseBody.setTo(token);;
			firebaseBody.setNotification(notification);
			
			Gson json = new Gson();
			
			request.body("application/json", json.toJson(firebaseBody));
			
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		}
	
	String token = "";
	String msgTitle = "";
	String msgBody = "";
	public void pushNotificationToClient() {
		  try {

			ClientRequest request = new ClientRequest("https://gcm-http.googleapis.com/gcm/send");
			request.accept("application/json");
			request.header("Authorization", "key=AAAA1oUTkvM:APA91bGJ5gJ2xJYm9dYvUkJ8NmMq4v-AlFp-R-eBkP71UvgKwc_9aZOko9rLORYsCb0uD7Y56hBijk-otwnwz7ylW9ujFlVMlcAZjbfzinS-DRG7Fs-4acnwstPgeHXYvLMWI_wlNuy3SIuNExRxAt-nUjPu1tpdhg");
			
			FirebaseNotification notification = new FirebaseNotification();
			notification.setBody(msgBody);
			notification.setTitle(msgTitle);
			
			FirebaseBody firebaseBody = new FirebaseBody();
			firebaseBody.setTo(token);;
			firebaseBody.setNotification(notification);
			
			Gson json = new Gson();
			
			request.body("application/json", json.toJson(firebaseBody));
			
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		}
	
	
	
	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getMsgTitle() {
		return msgTitle;
	}



	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}



	public String getMsgBody() {
		return msgBody;
	}



	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}



	@Override
	public void run() {
		pushNotificationToClient();
		
	}
}
