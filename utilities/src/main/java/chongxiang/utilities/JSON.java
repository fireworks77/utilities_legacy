package chongxiang.utilities;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	
	private static Utilities oUtilities = new Utilities();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		JSON oJSON = new JSON();
		String s_test = "{\"data\": [{\"hono_level\": {\"status\":true,\"100\": {\"5min\": 15,\"10min\": 20},\"200\": {\"5min\": 20, \"10min\": 25}}},{\"hono_level\": {\"status\":true,\"100\": {\"5min\": 25,\"10min\": 30},\"200\": {\"5min\": 30,\"10min\": 35}}}]"
				+ ",\"states\":[\"Alberta\",\"Quebec\"]"
				+ ",\"status\":true"
				+ "}";
		//System.out.println(oJSON.funcGetValue_JSONNODE_TWO_LAYERS(s_test, "data","hono_level"));
		String[] arr_terms = {"data","hono_level","100","10min"};
		String[] arr_result = oJSON.funcGetValue_JSON(s_test, arr_terms).split(",");
		for(String s_result: arr_result) {
			System.out.println(s_result.replace("#_#", ":").replace(";", ",").replace("##",";"));
		}
		*/
	}
	
	/*
	 * General function to get value from JSON
	 */
	public String funcGetValue_JSON(String s_JSON_doc, String[] arr_terms)throws Exception {
		String s_result = "";
		
		try {
			if(arr_terms.length > 0) {
				String s_top_term = arr_terms[0];
				if(arr_terms.length == 1) {
					/*
					 * last layer, get value directly
					 */
					s_result = s_result + this.funcGetValue_JSONNODE_ONE_LAYER(s_JSON_doc, s_top_term) + ",";
				}else {
					ArrayList<Object> arr_objs = this.funcGetObjects(s_JSON_doc, s_top_term);
					String[] arr_sub_terms = Arrays.copyOfRange(arr_terms, 1, arr_terms.length);
					if(arr_objs.size() > 0) {
						for(int i=0; i<arr_objs.size(); i++) {
							Object obj_JSON = arr_objs.get(i);
							s_result = s_result + this.funcGetValue_JSON(obj_JSON.toString(), arr_sub_terms)+ ",";
						}
					}else {
						/*
						 * When there isn't any object returned, 
						 * it means the keyword doesn't exist in the JSON doc  
						 * or has empty data
						 */
					}
					
				}
				
			}else {
				/*
				 * If the term array is empty, do nothing. 
				 */
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			s_result = "null";
		}
		
		s_result = oUtilities.funcTrimLastComma(s_result).trim();
		return (s_result.length() >0)?s_result:"null"; 
	}
	
	/*
	 * Get node value from JSON: one layer
	 */
	
	private String funcGetValue_JSONNODE_ONE_LAYER(String s_JSON_doc, String s_term_name) {
		String s_result = "";
		
		JSONObject obj_JSON = new JSONObject(s_JSON_doc);
		try{
			if(this.funcIsNodeExist(obj_JSON, s_term_name)){
				Object obj_temp = obj_JSON.get(s_term_name);
				/*
				 * If it is an JSON Array, parse it as an array
				 * If not, parse it as a straight object value
				 */
				if(obj_temp instanceof JSONArray) {
					JSONArray arr_JSON_obj = (JSONArray)obj_temp;
					if(arr_JSON_obj.length() > 0) {
						for(int i=0; i<arr_JSON_obj.length(); i++) {
							Object obj_target_node = arr_JSON_obj.get(i);
							s_result = s_result + obj_target_node.toString().
									replace(";","##").replace(",", ";").replace(":", "#_#").trim() + ",";
						}
					}else{
						
					}
				}else{
					/*
					 * If it is not an array, since it is only one layer, just fetch value.
					 */
					s_result = this.funcGetValueFromJSONNODE(obj_JSON, s_term_name);
				}
				
			}else {
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		s_result = oUtilities.funcTrimLastComma(s_result).trim();
		return (s_result.length() >0)?s_result:"null"; 
	}
	
	
	/*
	 * Get node value from JSON
	 */
	private String funcGetValueFromJSONNODE(JSONObject obj_input, String s_term_name){
		String s_result = "";
		
		try{
			if(this.funcIsNodeExist(obj_input, s_term_name)){
				
				int int_type = this.funcGetJSONKeyValueType(obj_input,s_term_name);

				switch(int_type){
					case(1):{
						s_result = obj_input.getString(s_term_name);
						break;
					}
					case(2):{
						s_result = String.valueOf(obj_input.getInt(s_term_name));
						break;
					}
					case(3):{
						s_result = String.valueOf(obj_input.getDouble(s_term_name));
						break;
					}
					case(4):{
						s_result = String.valueOf(obj_input.getBoolean(s_term_name));
						break;
					}
					case(5):{
						s_result = String.valueOf(obj_input.getLong(s_term_name));
						break;
					}
					case(6):{
						s_result = obj_input.getJSONObject(s_term_name).toString();
						break;
					}
					default:{
						s_result = "null";
						break;
					}
				}
			}else{
				s_result = "null";
			}
			
		}catch(JSONException je){
			System.out.println(je.toString());
		}
		s_result = s_result.replace(";","##").replace(",", ";").replace(":", "#_#").trim();
		
		if(s_result.length() <= 0){
			s_result = "null";
		}
		return s_result.trim();
	}

	private ArrayList<Object> funcGetObjects(String s_JSON_doc, String s_term){
		ArrayList<Object> arr_obj_results = new ArrayList<Object>();
		
		JSONObject obj_JSON = new JSONObject(s_JSON_doc);
		try {
			if(this.funcIsNodeExist(obj_JSON, s_term)){
				Object obj_temp = obj_JSON.get(s_term);
				/*
				 * If the object is a JOSNArray
				 */
				if(obj_temp instanceof JSONArray) {
					JSONArray arr_JSON_obj = (JSONArray)obj_temp;
					if(arr_JSON_obj.length() > 0){
						for(int i=0; i<arr_JSON_obj.length();i++) {
							Object obj_2nd_layer = arr_JSON_obj.get(i);
							arr_obj_results.add(obj_2nd_layer);
						}
					}
				}
				/*
				 * If the object is not a JSONArray
				 */
				else{
					arr_obj_results.add(obj_temp);
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return arr_obj_results;
	}
	
	/*
	 * Get the key value type: String = 1; Integer = 2; Double = 3;
	 */
	private int funcGetJSONKeyValueType(JSONObject obj_input, String s_key_name){
		int int_result = 0;
		Object oKeyNode = obj_input.get(s_key_name);
		if(oKeyNode instanceof String){
			int_result = 1;
		}else if(oKeyNode instanceof Integer){
			int_result = 2;
		}else if(oKeyNode instanceof Double){
			int_result = 3;
		}else if(oKeyNode instanceof Boolean){
			int_result = 4;
		}else if(oKeyNode instanceof Long){
			int_result = 5;
		}else if(oKeyNode instanceof JSONObject){
			int_result = 6;
		}
		return int_result;
	}
	
	private boolean funcIsNodeExist(JSONObject obj_input, String s_term_name){
		boolean bool_result = true;
		if(!obj_input.has(s_term_name)){
			bool_result = false;
		}
		return bool_result;
	}
}
