package Rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;

import entity.User;

public class Test {

	public static void main(String[] args) {

		  try {
			  
//			  ClientRequest request = new ClientRequest(
//						"http://localhost:8080/JasaServiceWeb/rs/jasaService/postUpdateUser");
//					request.accept("application/json");
//
//					//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
//					User user = new User("COBA-3", "CANDRA YUDHATAMA", "08831029831", "CANDRA@YMAIL.COM", "CANDRA", "Penyedia", null, null, null, null, "kbskdaisuhjkw");
//					String json = new Gson().toJson(user);
//					request.body("application/json", json);
			  
			  
	
				//String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
//				User user = new User("COBA-3", "CANDRA YUDHATAMA", "08831029831", "CANDRA@YMAIL.COM", "CANDRA", "Penyedia", null, null, null, null, "kbskdaisuhjkw");
//				String json = new Gson().toJson(user);
//				request.body("application/json", json);
			  
			  ClientRequest request = new ClientRequest("http://localhost:8080/JasaServiceWeb/rs/jasaService/getUser?email=nur@ymail.com&password=brebes");
				request.accept("application/json");
	
	
				ClientResponse<String> response = request.get(String.class);
	
//				if (response.getStatus() != 201) {
//					throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//				}
	
				BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
	
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
					
		  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  } catch (Exception e) {

				e.printStackTrace();

			  }

			}

}
