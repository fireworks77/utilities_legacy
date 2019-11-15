package chongxiang.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getProperty(String s_Property_Name)throws Exception{
		Properties property = new Properties();
		InputStream inp_Properties = null;
		String result = "";
		
		try{
			inp_Properties = new FileInputStream("config.properties");
			property.load(inp_Properties);
			result = property.getProperty(s_Property_Name);
			
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
	
	public String funcTrimLastComma(String s_input){
		if(s_input.length() > 0){
			if(s_input.substring(s_input.length()-1,s_input.length()).equalsIgnoreCase(",")){
				s_input = s_input.substring(0,s_input.length()-1);
			}
		}
		return s_input;
	}
	
}
