package chongxiang.utilities;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;



class Test_JSON {	


	
	@Test
	public void test()throws Exception {
		JSON oJSON = new JSON();
		String s_test = "{\"data\": [{\"hono_level\": {\"status\":true,\"100\": {\"5min\": 15,\"10min\": 20},\"200\": {\"5min\": 20, \"10min\": 25}}},{\"hono_level\": {\"status\":true,\"100\": {\"5min\": 25,\"10min\": 30},\"200\": {\"5min\": 30,\"10min\": 35}}}]"
				+ ",\"states\":[\"Alberta\",\"Quebec\"]"
				+ ",\"status\":true"
				+ "}";
		//System.out.println(oJSON.funcGetValue_JSONNODE_TWO_LAYERS(s_test, "data","hono_level"));
		String s_terms = "data.hono_level.200.10min";
		ArrayList<String> arr_results = oJSON.funcGetValue_JSON(s_test, s_terms);
		for(String s_result: arr_results) {
			System.out.println(s_result);
		}
	}


	
}
