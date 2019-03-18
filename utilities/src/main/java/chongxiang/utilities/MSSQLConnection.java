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
	    <groupId>com.microsoft.sqlserver</groupId>
	    <artifactId>mssql-jdbc</artifactId>
	    <version>7.2.1.jre8</version>
	    <scope>test</scope>
	</dependency>
 */
public class MSSQLConnection {
	
	private String connectionURL ="jdbc:sqlserver://";
	
	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	
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
	
	private static enum Authentication_Types{Windows_Auth, SQL_Auth};
	
	private Authentication_Types Authentication_Type;
	public void setAuthentication_Type(Authentication_Types Authentication_Type) {
		this.Authentication_Type = Authentication_Type;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean funcInitializeDataaseConnection(){
		this.conn = null;
		this.stmt = null;
		this.rs = null;
		
		try{
			switch(this.Authentication_Type) {
				case Windows_Auth:{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					this.connectionURL = this.connectionURL + this.server + ";database=" + this.database_name + ";integratedSecurity=true";
					this.conn = DriverManager.getConnection(this.connectionURL);
					this.stmt = this.conn.createStatement();
					
					break;
				}
				case SQL_Auth: {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					this.connectionURL = this.connectionURL + this.server + ";database=" + this.database_name 
						+ ";user=" + this.username + ";password=" + this.password;
					this.conn = DriverManager.getConnection(this.connectionURL);
					this.stmt = this.conn.createStatement();
					break;
				}
			}
			return true;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Database Connection Initialization Failed.");
			return false;
		}
		
	}
	
	public void funcCloseDataaseConnection(){
		try{if(this.conn != null)this.conn.close();}catch(SQLException e){e.printStackTrace();}
		try{if(this.stmt != null)this.stmt.close();}catch(SQLException e){e.printStackTrace();}
		try{if(this.rs != null)this.rs.close();}catch(SQLException e){e.printStackTrace();}
	}

}
