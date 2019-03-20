package chongxiang.utilities;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;



/**
 * 
 * @author chongxiang
 *<dependency>
	    <groupId>org.mongodb</groupId>
	    <artifactId>mongo-java-driver</artifactId>
	    <version>3.10.1</version>
  </dependency>
 */

public class MongoDBConnection {
	
	public com.mongodb.client.MongoClient mongoClient;
	public MongoDatabase db;
	
	private Authentication_Types_MongoDB Authentication_Type;
	public void setAuthentication_Type(Authentication_Types_MongoDB Authentication_Type) {
		this.Authentication_Type = Authentication_Type;
	}
	
	private String username;
	public void setUsername(String username) {
		this.username = username;	
	}
	
	private String password;
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String server;
	public void setServer(String server) {
		this.server = server;
	}
	
	private String database_name;
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
	}
	
	private int port;
	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean funcInitialization()throws Exception{
		
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE); 
		
		try {
			switch(this.Authentication_Type) {
				case DEFAULT_AUTH:{
					MongoCredential credential = MongoCredential.createCredential(this.username, this.database_name, this.password.toCharArray());
					this.mongoClient = MongoClients.create(
					        MongoClientSettings.builder()
			                .applyToClusterSettings(builder -> 
			                        builder.hosts(Arrays.asList(new ServerAddress(this.server, this.port))))
			                .credential(credential)
			                .build()); 
					//Legacy: new MongoClient(new ServerAddress(strConnectURL), asList(credential)); 
					this.db = this.mongoClient.getDatabase(this.database_name);
					
					break;
				}
				case NO:{
					this.mongoClient = MongoClients.create("mongodb://" + this.server + ":" + this.port);
					//Legacy: new MongoClient(this.connectionURL,Integer.parseInt(this.port));
					this.db = mongoClient.getDatabase(this.database_name);
					
					break;
				}
			}
			
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("=========Failed to connect to MongoDB=========");
			return false;
		}
		
			
		
		
	}

}
