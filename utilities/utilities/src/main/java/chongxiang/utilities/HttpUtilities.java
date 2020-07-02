package chongxiang.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtilities {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    @SuppressWarnings("deprecation")
    /**
     * 
     * @param s_URL
     * @param keepPathSlash
     * 		true: keep '\' unchanged
     * @return
     */
    public static String URLEncoder(String s_URL, boolean keepPathSlash){
    	String s_Encode = "";
    	try {
    		s_Encode =  URLEncoder.encode(s_URL, "UTF-8");
        } catch (Exception e) {
        	s_Encode = URLEncoder.encode(s_URL);
        	throw new RuntimeException("UTF-8 encoding is not supported.", e);
        }
    	if(keepPathSlash) {
    		s_Encode = s_Encode.replace("%2F", "/");
        }
        return s_Encode.replace("%25", "%")
        		       .replace("+", "%20")
        		       .replace("%3D","=")
        		       .replace("%26","&");
        				
    }
    
    /**
     * 
     * @param s_URL
     * @return 
     *  the host of URL. 
     */
    public static String getURL_HOST(String s_URL) {
    	String s_HOST = "";
    	//s_URL.replace("http://", "").replace("https://", "")
    	try {
    		URL url = new URL(s_URL);
    		s_HOST = url.getHost();
    	}catch(Exception e) {
    		throw new RuntimeException("Cannot get URL Host", e);
    	}
    	
    	return s_HOST;
    	//return s_Process_URL.substring(0, s_Process_URL.indexOf("/"));
    }
    
    /**
     * 
     * @param s_URL
     * @return
     * 	the QueryString of URL.
     */
    public static String getURL_QUERYString(String s_URL) {
    	String s_QueryString = "";
    	try {
    		URL url = new URL(s_URL);
    		if(url.getQuery() != null) {
    			s_QueryString = url.getQuery();
    		}
    	}catch(Exception e) {
    		throw new RuntimeException("Cannot get URL QueryString", e);
    	}
    	return s_QueryString;
    }
    
    /**
     * 
     * @param s_URL
     * @return
     *   the Path of the URL
     */
    public static String getURL_Path(String s_URL) {
    	try {
    		URL url = new URL(s_URL);
    		return url.getPath();
    	}catch(Exception  e) {
    		throw new RuntimeException("Cannot get URL path", e);
    	}
    }
    
    /**
     * 
     * @param s_QueryStry
     * @return 
     *   Map of query parameters
     */
    public static TreeMap<String, String> getQueryParameters(String s_QueryString){
    	TreeMap<String, String> queryParametes = new TreeMap<String, String>();
    	try {
    		String[] arr_QueryParameters = s_QueryString.split("&");
    		for(String s_QueryParameter: arr_QueryParameters) {
    			String[] arr_Parameters = s_QueryParameter.split("=");
    			queryParametes.put(arr_Parameters[0], arr_Parameters[1]);
    		}
    		
    	}catch(Exception e) {
    		throw new RuntimeException("Cannot get Query Parameters", e);
    	}
    	return queryParametes;
    }
    
    /**
     * Makes a http request to the specified endpoint
     */
    public static String invokeHttpRequest(URL endpointUrl,
                                         String httpMethod,
                                         Map<String, String> headers,
                                         String requestBody) {
        HttpURLConnection connection = createHttpConnection(endpointUrl, httpMethod, headers);
        try {
            if ( requestBody != null ) {
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(requestBody);
                wr.flush();
                wr.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Request failed. " + e.getMessage(), e);
        }
        return executeHttpRequest(connection);
    }
    
    public static String executeHttpRequest(HttpURLConnection connection) {
        try {
            // Get Response
            InputStream is;
            try {
                is = connection.getInputStream();
            } catch (IOException e) {
                is = connection.getErrorStream();
            }
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            throw new RuntimeException("Request failed. " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    public static HttpURLConnection createHttpConnection(URL endpointUrl,
                                                         String httpMethod,
                                                         Map<String, String> headers) {
        try {
        	String s_Protocol = endpointUrl.getProtocol();
        	
        	HttpURLConnection connection = null;	
        	switch(s_Protocol) {
	        	case("http"):{
	        		connection = (HttpURLConnection) endpointUrl.openConnection();
	        		break;
	        	}
	        	case("https"):{
	        		connection = (HttpsURLConnection) endpointUrl.openConnection();
	        		break;
	        	}
	        	default:{
					connection = (HttpURLConnection) endpointUrl.openConnection();
					break;
				}
        	}
            
            connection.setRequestMethod(httpMethod);
            
            if ( headers != null ) {
                //System.out.println("--------- Request headers ---------");
                for ( String headerKey : headers.keySet() ) {
                    //System.out.println(headerKey + ": " + headers.get(headerKey));
                    connection.setRequestProperty(headerKey, headers.get(headerKey));
                }
            }

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setAllowUserInteraction(true);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create connection. " + e.getMessage(), e);
        }
    }
}