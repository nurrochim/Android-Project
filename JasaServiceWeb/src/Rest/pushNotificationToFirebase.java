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

public class pushNotificationToFirebase {
	public static void main(String[] args) {
		  try {

			ClientRequest request = new ClientRequest("https://gcm-http.googleapis.com/gcm/send");
			request.accept("application/json");
			request.header("Authorization", "key=AIzaSyCncqRmDrP_rrhA1HlNZodxjOabvCKePkM");
			
			FirebaseNotification notification = new FirebaseNotification();
			notification.setBody("Coba Jasa Service");
			notification.setTitle("Send From Web Service");
			
			FirebaseBody firebaseBody = new FirebaseBody();
			firebaseBody.setTo("fr-86GzFWwM:APA91bEWS0lBFcULvaeKyWDzmXn5BRY0nYwCckKcdw3esr2JUC7c027YOdNuP-0ByhDC8NPxpmN7m0jNITNgzXQg8ZrEmxwWjjHnL9C6_qzfSN1GOUuF1JOuVxfEzz26j02-KuTfwkTG");;
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
}
