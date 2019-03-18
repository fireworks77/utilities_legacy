package chongxiang.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author chongxiang
 * <dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.15</version>
	</dependency>
 */

public class MySQLConnection {
	
	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	
	private String username = "";
	public void setUsername(String username) {
		this.username = username;	
	}
	
	private String password = "";
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String databaseInfo = "";
	public void setDatabaseInfo(String databaseInfo) {
		this.databaseInfo = databaseInfo;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean funcInitializeDataaseConnection(String strServer){
		this.conn = null;
		this.stmt = null;
		this.rs = null;
		boolean boolResult = true;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionURL = "jdbc:mysql://" + this.databaseInfo + "?&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
			this.conn = DriverManager.getConnection(connectionURL, this.username, this.password);
			this.stmt = this.conn.createStatement();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Database Connection Initialization Failed.");
			boolResult = false;
		}
		return boolResult;
	}
	
	public void funcCloseDataaseConnection(){
		try{if(this.conn != null)this.conn.close();}catch(SQLException e){e.printStackTrace();}
		try{if(this.stmt != null)this.stmt.close();}catch(SQLException e){e.printStackTrace();}
		try{if(this.rs != null)this.rs.close();}catch(SQLException e){e.printStackTrace();}
		
	}
	
}
