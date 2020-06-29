package chongxiang.utilities;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseConnection_PostgreSQL {
	
	private String connectionUrl ="";
	public String getconnectionUrl(){
		return this.connectionUrl;
	}
	
	private String connectionUser="";
	public String getconnectionUser(){
		return this.connectionUser;
	}
	
	private String connectionPassword="";
	public String getconnectionPassword(){
		return this.connectionPassword;
	}
	
	private ArrayList<String> arrUserName = new ArrayList<String>();
	private ArrayList<String> arrPassword = new ArrayList<String>();
	private ArrayList<String> arrConnectionURL = new ArrayList<String>();
	
	public Connection conn;
	public Statement stmt;
	public ResultSet rs;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean funcInitializeDataaseConnection(String strServer){
		this.conn = null;
		this.stmt = null;
		this.rs = null;
		boolean boolResult = true;
		try{
			Class.forName("org.postgresql.Driver");
			if(this.funcGetDatabaseInfo(strServer)){
				this.connectionUrl = "jdbc:postgresql://" 
						+ this.arrConnectionURL.get(0) 
						+ "?user="+this.arrUserName.get(0)
						+ "&password="+this.arrPassword.get(0)
						+ "&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
				this.conn = DriverManager.getConnection(this.connectionUrl);
				this.stmt = this.conn.createStatement();
				
			}else{
				System.out.println("============Cannot Get Database Connection Info============");
				boolResult = false;
			}

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
		this.arrConnectionURL.clear();
		this.arrUserName.clear();
		this.arrPassword.clear();
		
	}
	
	private boolean funcGetDatabaseInfo(String strServer)throws Exception{
		File fXmlFile = new File(System.getProperty("user.dir") + "/files/PostgreSQLDBConnection.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		NodeList nList = doc.getElementsByTagName("Users");
		int intElementFound = 0;
		try{
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					 
					Element eElement = (Element) nNode;
					if(strServer.equalsIgnoreCase(eElement.getElementsByTagName("Server").item(0).getTextContent())){
						this.arrUserName.add(eElement.getElementsByTagName("Username").item(0).getTextContent());
						this.arrPassword.add(eElement.getElementsByTagName("Password").item(0).getTextContent());
						this.arrConnectionURL.add(eElement.getElementsByTagName("ConnectionURL").item(0).getTextContent());
						intElementFound = 1;
						return true;
					}
				}
						
			}
			
		}catch(Error e){
			System.out.println("the error is:" + e.toString());
			return false;
		}
		if(intElementFound == 1){
			return true;
		}else{
			return false;
		}
		
	}
}
