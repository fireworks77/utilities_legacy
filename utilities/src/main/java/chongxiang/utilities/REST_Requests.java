package chongxiang.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class provides methods to send requests to a REST service by either
 * POST or GET, with or without authentications
 * Default payload format is JSON
 * 
 * @author chongxiang
 * Time: 2018-01-30
 */

public class REST_Requests {
	
	public enum requestTypes {GET, POST};
	
	public enum requestAuthMethods{NoAuth, Basic_Auth, Basic_Auth_X_Token, Bearer};
	
	public enum requestConnectionTypes {HTTP, HTTPS};
	
	private requestTypes requestType;
	public void setRequesType(requestTypes requestType) {
		this.requestType = requestType;
	}
	
	private requestAuthMethods requestAuthMethod;
	public void setRequestAuthMethod(requestAuthMethods requestAuthMethod) {
		this.requestAuthMethod = requestAuthMethod;
	}
	
	private requestConnectionTypes requestConnectionType;
	public void setRequestConnectionType(requestConnectionTypes requestConnectionType) {
		this.requestConnectionType = requestConnectionType;
	}
	
	
	private String username;
	public void setUsername(String username) {
		this.username = username;
	}
	
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String token;
	public void setToken(String token) {
		this.token = token;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/*
	  * Send REST HTTPS requests to a REST POST/GET service 
	  * Payload format: JSON
	  * Returns response data
	  * requestTypes: 1. POST; 2. GET
	  * requestAuthMethods: 1. NoAuth, 2. Basic_Auth, 3. Basic_Auth_X_Token, 4. Bearer
	  * requestConnectionTypes: 1. HTTP, 2. HTTPS
	  *  
	  */
	 public String sendRequest(String requestUrl, 
			 					   String payload
			 						)throws Exception {
			
		StringBuffer jsonString = new StringBuffer();
		
	    try {
	        URL url = new URL(requestUrl);
	        
	        HttpURLConnection connection;
	        
	        switch(this.requestConnectionType) {
		        case HTTP:{
		        	connection = (HttpURLConnection) url.openConnection();
		        	break;
		        }
		        case HTTPS:{
		        	connection = (HttpsURLConnection) url.openConnection();
		        	break;
		        }
		        default:{
		        	connection = (HttpURLConnection) url.openConnection();
		        	break;
		        }
	        }
	        
	        
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod(this.requestType.toString());
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        
	        switch(this.requestAuthMethod) {
		        case NoAuth:{
		        	break;
		        }
		        case Basic_Auth:{
		        	String authentication = this.username + ":" + this.password;
			        String encoding = Base64.getEncoder().encodeToString((authentication).getBytes("UTF-8"));
			        connection.setRequestProperty("Authorization", "Basic " + encoding);
		        	break;
		        }
		        case Basic_Auth_X_Token:{
		        	String authentication = this.username + ":" + this.password;
			        String encoding = Base64.getEncoder().encodeToString((authentication).getBytes("UTF-8"));
			        connection.setRequestProperty("Authorization", "Basic " + encoding);
			        connection.setRequestProperty("X-AUTH-TOKEN", this.token);
		        	break;
		        }
		        case Bearer:{
		        	connection.setRequestProperty("Authorization", "Bearer " + this.token);
		        	break;
		        }
		        
		        default:{
		        	break;
		        }
	        
	        }
	       
	        
	        switch(this.requestType){
		        case POST:{
		        	OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		 	        writer.write(payload);
		 	        writer.close();
		 	        break;
		        }
		        case GET:{
		        	break;
		        }
	        
	        }
	       
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	            e.printStackTrace();
	    }
	    return jsonString.toString();
	}
}
