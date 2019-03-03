package chongxiang.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getProperty(String strProperty_Name)throws Exception{
		Properties property = new Properties();
		InputStream inp_Properties = null;
		String result = "";
		
		try{
			inp_Properties = new FileInputStream("config.properties");
			property.load(inp_Properties);
			result = property.getProperty(strProperty_Name);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(inp_Properties != null){
				try{
					inp_Properties.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
		
	}
	
}
