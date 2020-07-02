package chongxiang.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.mail.smtp.SMTPTransport;

import net.lingala.zip4j.core.ZipFile;

public class Utilities {
	
	private String s_SMTP_TRANSPORT_ID = "";
	public String get_SMTP_TRANSPORT_ID(){
		return this.s_SMTP_TRANSPORT_ID;
	}
	
	private String s_SMTP_SERVER = "";
	public void setSMTP_SERVER(String s_SMTP_SERVER){
		this.s_SMTP_SERVER = s_SMTP_SERVER;
	}
	
	private String s_SMTP_USER = "";
	public void setSMTP_USER(String s_SMTP_USER){
		this.s_SMTP_USER = s_SMTP_USER;
	}
	
	private String s_SMTP_PASSWORD = "";
	public void setSMTP_PASSWORD(String s_SMTP_PASSWORD){
		this.s_SMTP_PASSWORD = s_SMTP_PASSWORD;
	}
	
	private boolean bool_PRINT_OUT_REQUEST_ERROR = true;
	public void setPRINT_OUT_REQUEST_ERROR(boolean bool_PRINT_OUT_REQUEST_ERROR){
		this.bool_PRINT_OUT_REQUEST_ERROR = bool_PRINT_OUT_REQUEST_ERROR;
	}
	
	private String s_AWS_KEY = "";
	public void setAWS_KEY(String s_AWS_KEY){
		this.s_AWS_KEY = s_AWS_KEY;
	}
	
	private String s_AWS_SECRET = "";
	public void setAWS_SECRET(String s_AWS_SECRET){
		this.s_AWS_SECRET = s_AWS_SECRET;
	}
	
	private String s_AWS_REGION = "";
	public void setAWS_REGION(String s_AWS_REGION){
		this.s_AWS_REGION = s_AWS_REGION;
	}
	
	private String s_AWS_SERVICE = "";
	public void setAWS_SERVICE(String s_AWS_SERVICE){
		this.s_AWS_SERVICE = s_AWS_SERVICE;
	}
	
	
	private String s_Bearer_TOKEN = "";
	public void setBearer_TOKEN(String s_Bearer_TOKEN){
		this.s_Bearer_TOKEN = s_Bearer_TOKEN;
	}
	
	private String s_X_API_KEY_VALUE = "";
	public void setX_API_KEY_VALUE(String s_X_API_KEY_VALUE){
		this.s_X_API_KEY_VALUE = s_X_API_KEY_VALUE;
	}
	
	private boolean b_DEBUG = false;
	public void setDEBUG(boolean b_DEBUG) {
		this.b_DEBUG = b_DEBUG;
	}
	
	private String s_POST_Username = "";   
	public void setPOST_Username(String s_POST_Username){
		this.s_POST_Username = s_POST_Username;
	}
	
	private String s_POST_Password = "";   
	public void setPOST_Password(String s_POST_Password){
		this.s_POST_Password = s_POST_Password;
	}
	
	private String s_X_AUTH_TOKEN = "";
	public void setX_AUTH_TOKEN(String s_X_AUTH_TOKEN){
		this.s_X_AUTH_TOKEN = s_X_AUTH_TOKEN;
	}
	
	private String s_RESPONSE_CODE = "";
	public String getRESPONSE_CODE() {
		return this.s_RESPONSE_CODE;
	}
	
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
	
	public String TrimLastComma(String s_input, String s_character){
		if(s_input.length() > 0){
			if(s_input.substring(s_input.length()-1,s_input.length()).equalsIgnoreCase(s_character)){
				s_input = s_input.substring(0,s_input.length()-1);
			}
		}
		return s_input;
	}
	
	public String funcReadDataFromFiles(String s_FilePath)throws Exception{
		StringBuffer s_Results = new StringBuffer("");
		BufferedReader br_File = new BufferedReader(new FileReader(s_FilePath));
		try {
		    String s_Line = br_File.readLine();
		    while (s_Line != null) {
		    	s_Results.append(s_Line);
		    	s_Line = br_File.readLine();
		    }
		} finally {
			br_File.close();
		}
		return s_Results.toString();
	}

	public void funcLogInfo(String s_Message, Logger Log, boolean bool_Print) {
		Log.info(s_Message);
		if(bool_Print) {
			System.out.println(s_Message);
		}
	}
	
	public Boolean funcGenerateOutputFile(String s_FileName, String s_Message) throws Exception{
		
		String currentUsersHomeDir = System.getProperty("user.dir");
		String strOutputFileName = "";

		strOutputFileName = currentUsersHomeDir + File.separator + s_FileName;
		
		try{
		    FileWriter oFile = new FileWriter(strOutputFileName);
		    oFile.append(s_Message);				
		    oFile.flush();
		    oFile.close();
		}
		catch(IOException e){
		     e.printStackTrace();
		     return false;
		} 
		return true;
	}
	
	public static void UnZipFile(String s_File, 
							     String s_Destionation_Path, 
							     String s_Password)throws Exception {
		try {
	         ZipFile zipFile = new ZipFile(s_File);
	         if (zipFile.isEncrypted()) {
	            zipFile.setPassword(s_Password);
	         }
	         zipFile.extractAll(s_Destionation_Path);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void funcSetBegingTimeStamp(){
		Date dateStart= new Date();
		System.out.println(new Timestamp(dateStart.getTime()));
	}
	
	public void funcSetEndTimeStamp(){
		Date dateEnd= new Date();
		System.out.println(new Timestamp(dateEnd.getTime()));
	}
	
	public String funcGetTimeStamp_StringFormat(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStart= new Date();
		return dateFormat.format(dateStart).toString();
	}
	
	public String funcGet_Registration_Email(String s_Domain){
		return "test" + funcGetTimeStamp_StringFormat()
				.replace(":", "").replace("-","").replace("_","").replace(" ","")
				+ "@" + s_Domain;
	}
	
	/**
     * Return the primary text content of the message.
	 * @throws IOException 
     */
	public String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            //textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
	
	public boolean funcSendEmail(String strFromAddress, String strFromPassword, String strRecipient, String strBody, String strSubject){
		boolean boolResult = true;
		
		String strToEmailAddresses = strRecipient;
		final String strFromEmailAddress = strFromAddress;
		final String strPassword = strFromPassword;
		
	     //Create email sending properties
	     Properties props = new Properties();
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtp.host", "smtp.gmail.com");
	     props.put("mail.smtp.port", "587");
		
	     Session session = Session.getInstance(props,
		    new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(strFromEmailAddress, strPassword);
		   }
	     });
	     
	     try{

	    		Message message = new MimeMessage(session);
	    		message.setFrom(new InternetAddress(strFromEmailAddress)); //Set from address of the email
	    		message.setContent(strBody,"text/html"); //set content type of the email

	    		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strToEmailAddresses)); //Set email recipient

	    		message.setSubject(strSubject); //Set email message subject
	    		Transport.send(message); //Send email message

	    		System.out.println("sent email successfully!");

	    	}catch (MessagingException e) {
	    		boolResult = false;
	    		throw new RuntimeException(e);	
	    	}
	     
		return boolResult;
	}
	
	/**
	 * 
	 * @param s_FROM
	 * 	from address
	 * @param s_TO
	 * 	to addresses
	 * @param s_CC
	 *  cc addresses
	 * @param s_BCC
	 *  bcc addresses
	 * @param s_SUBJECT
	 *  email subject
	 * @param s_BODY
	 *  email body
	 * @param b_AUTH
	 *  if it is true, then please use setSMTP_USER and setSMTP_PASSWORD to set credentials of SMTP
	 * @param s_SECURITY_SETTING
	 *  TLS, SSL
	 * @param s_PORT
	 *  SMTP port
	 * @param Log
	 *  Logger
	 * @throws Exception
	 */
	public void func_SEND_EMAIL_SMTPTRANSPORT(	String s_FROM, 
												String s_TO,
												String s_CC,
												String s_BCC,
												String s_SUBJECT,
												String s_BODY,
												boolean b_AUTH,
												String s_SECURITY_SETTING,
												String s_PORT,
												Logger Log) throws Exception{
			
		try{
			
			this.s_SMTP_TRANSPORT_ID = "0";
			
	        Properties p_SEND_EMAIL = System.getProperties();
	        p_SEND_EMAIL.put("mail.smtps.host",this.s_SMTP_SERVER);
	        p_SEND_EMAIL.put("mail.smtp.port", s_PORT);
	        
	        String s_SMTP_USER = "";
	        String s_SMTP_PASSWORD = "";
	        
	        if(b_AUTH){
		    	 p_SEND_EMAIL.put("mail.smtp.auth", "true");
		    	 s_SMTP_USER = this.s_SMTP_USER;
			     s_SMTP_PASSWORD = this.s_SMTP_PASSWORD;
	        }else{
	        	p_SEND_EMAIL.put("mail.smtps.auth","false");
	        }
	        
		    switch(s_SECURITY_SETTING.toUpperCase()){
		     	default:{
		     		break;
		     	}
		     	case("TLS"):{
			   	     p_SEND_EMAIL.put("mail.smtp.starttls.enable", "true");
			   	     break;
		     	}
		     	case("SSL"):{
			   	     p_SEND_EMAIL.put("mail.smtp.socketFactory.class", 
			                 "javax.net.ssl.SSLSocketFactory");
			   	     break;
		     	}
		     }
	        
	        Session sess_SEND_EMAIL = Session.getInstance(p_SEND_EMAIL, null);
	        
	        Message msg_SEND_EMAIL = new MimeMessage(sess_SEND_EMAIL);
	        msg_SEND_EMAIL.setFrom(new InternetAddress(s_FROM));

	        msg_SEND_EMAIL.addRecipients(Message.RecipientType.TO, InternetAddress.parse(s_TO));
	        if(s_CC.indexOf("@") > 0) {
	        	msg_SEND_EMAIL.addRecipients(Message.RecipientType.CC, InternetAddress.parse(s_CC));
	        }
	        if(s_BCC.indexOf("@") > 0) {
	        	msg_SEND_EMAIL.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(s_BCC));
	        }
	        msg_SEND_EMAIL.setSubject(s_SUBJECT);
	        msg_SEND_EMAIL.setContent(s_BODY, "text/html");
	        msg_SEND_EMAIL.setHeader("X-Mailer", "");
	        msg_SEND_EMAIL.setSentDate(new Date());
	        
	        SMTPTransport smtp_T_SEND_EMAIL =
	            (SMTPTransport)sess_SEND_EMAIL.getTransport("smtp");
	        smtp_T_SEND_EMAIL.connect(this.s_SMTP_SERVER, s_SMTP_USER, s_SMTP_PASSWORD);
	        smtp_T_SEND_EMAIL.sendMessage(msg_SEND_EMAIL, msg_SEND_EMAIL.getAllRecipients());
	        this.funcLogInfo("Response: " + smtp_T_SEND_EMAIL.getLastServerResponse(), Log, true);
	        
	        this.s_SMTP_TRANSPORT_ID = smtp_T_SEND_EMAIL.getLastServerResponse().substring(
	        		smtp_T_SEND_EMAIL.getLastServerResponse().indexOf("id=")+3,
	        		smtp_T_SEND_EMAIL.getLastServerResponse().length()
	        							);
	        smtp_T_SEND_EMAIL.close();
	        
	        
		}catch(Exception e){
			this.funcLogInfo("Cannot send emails. Please check logs", Log, true);
			this.funcLogInfo(e.toString(), Log, false);
		}

	}
	
	public boolean func_SEND_EMAIL(
								String s_FROM, 
								String s_TO, 
								String s_SUBJECT,
								String s_BODY,
								boolean b_AUTH,
								String s_SECURITY_SETTING,
								String s_PORT,
								Logger Log
								)throws Exception{
		boolean boolResult = true;
		
	     //Create email sending properties
	     Properties p_SEND_EMAIL = new Properties();

	     p_SEND_EMAIL.put("mail.smtp.host", this.s_SMTP_SERVER);
	     p_SEND_EMAIL.put("mail.smtp.port", s_PORT);
		
	     Session sess_SEND_EMAIL = Session.getInstance(p_SEND_EMAIL, null);
	     
	     
	     if(b_AUTH){
	    	 p_SEND_EMAIL.put("mail.smtp.auth", "true");
	    	 final String s_SMTP_USER = this.s_SMTP_USER;
		     final String s_SMTP_PASSWORD = this.s_SMTP_PASSWORD;
	    	 sess_SEND_EMAIL = Session.getInstance(p_SEND_EMAIL,
		 		    new javax.mail.Authenticator() {
		 		    protected PasswordAuthentication getPasswordAuthentication() {
		 		    return new PasswordAuthentication(s_SMTP_USER, s_SMTP_PASSWORD);
		 		   }
		 	     });
	     }else{
	    	 p_SEND_EMAIL.put("mail.smtp.auth", "false");
	     }

	     switch(s_SECURITY_SETTING.toUpperCase()){
	     	default:{
	     		break;
	     	}
	     	case("TLS"):{
		   	     p_SEND_EMAIL.put("mail.smtp.starttls.enable", "true");
		   	     break;
	     	}
	     	case("SSL"):{
		   	     p_SEND_EMAIL.put("mail.smtp.socketFactory.class", 
		                 "javax.net.ssl.SSLSocketFactory");
		   	     break;
	     	}
	     }
	     
	     try{

	    		Message msg_SEND_EMAIL = new MimeMessage(sess_SEND_EMAIL);
	    		msg_SEND_EMAIL.setFrom(new InternetAddress(s_FROM)); //Set from address of the email
	    		msg_SEND_EMAIL.setContent(s_BODY,"text/html"); //set content type of the email
	    		msg_SEND_EMAIL.setHeader("X-Mailer", "");
	    		msg_SEND_EMAIL.setRecipients(Message.RecipientType.TO,InternetAddress.parse(s_TO)); //Set email recipient

	    		msg_SEND_EMAIL.setSubject(s_SUBJECT); //Set email message subject
	    		Transport.send(msg_SEND_EMAIL); //Send email message

	    		this.funcLogInfo("Sent email successfully!", Log, true);
	    	
		        
	    	}catch (MessagingException e) {
	    		boolResult = false;
	    		this.funcLogInfo("Cannot send emails. Please check logs", Log, true);
				this.funcLogInfo(e.toString(), Log, false);
	    	}
	     
		return boolResult;
	}
	
	public String funcGetDateValue(String strDate, String strType){
		String strResult = "0";
		
		String[] arrValues = strDate.split("-");
		if(arrValues.length == 3){
			switch(strType){
				case("Year"):{
					strResult = arrValues[0];
					break;
				}
				case("Month"):{
					strResult = arrValues[1];
					break;
				}
				case("Day"):{
					strResult = arrValues[2];
					break;
				}
			}
		}

		return strResult;
		
	}
	
	public Integer funcCompareDates(String strDateOne, String strDateTwo, int intDateType) throws Exception{
		int intResult = 0;
		try{
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			/*
			 * intDateType
			 * 0: date only
			 * 1: date + time
			 */
			switch(intDateType){
				case(0):{
					break;
				}
				case(1):{
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					break;
				}
			}
			
			Date date1 = format.parse(strDateOne);
			Date date2 = format.parse(strDateTwo);

			intResult = date1.compareTo(date2);

		}catch(Exception e){
			
		}

		return intResult;
	}
	
	public String funcGetProperDayToDigital(String strInput){
		String strResult = "0";
		if(strInput.equalsIgnoreCase("null")){
			strResult = "1";
		}else{
			strResult = strInput;
		}
		return strResult;
	}
	
	public String funcConvertMonthToDigital(String strInput){
		String strResult = "1";
		
			switch(strInput){
				case("Jan"):{
					strResult = "1";
					break;
				}
	
				case("Feb"):{
					strResult = "2";
					break;
				}
				case("Mar"):{
					strResult = "3";
					break;
				}
				case("Apr"):{
					strResult = "4";
					break;
				}
				case("May"):{
					strResult = "5";
					break;
				}			
				case("Jun"):{
					strResult = "6";
					break;
				}
				case("Jul"):{
					strResult = "7";
					break;
				}
				case("Aug"):{
					strResult = "8";
					break;
				}
				case("Sep"):{
					strResult = "9";
					break;
				}
				case("Oct"):{
					strResult = "10";
					break;
				}
				case("Nov"):{
					strResult = "11";
					break;
				}
				case("Dec"):{
					strResult = "12";
					break;
				}
			}

		return strResult;
	}
	
    public String getTimeStamp_UTC() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));//server timezone
        return dateFormat.format(new Date());
    }
    
    public boolean func_FIX_HTTPS_SSL_CERTIFICATES_VALIDATION_FAILURE() throws Exception{
    	/*
         *  fix for
         *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
         *       sun.security.validator.ValidatorException:
         *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
         *               unable to find valid certification path to requested target
         */
    	boolean b_result = false;
    	try{
    		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

					public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

				}
	        };
	
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	        	public boolean verify(String hostname, SSLSession session) {
	        		return true;
	            }
	        };
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        /*
	         * end of the fix
	         */
    		b_result = true;
    	}catch(Exception e){
    		e.printStackTrace();
    		b_result = false;
    	}
        return b_result;
    }
    
    /**
	  * @author chong.xiang
	  * updated: 2020-02-18
	  * @param CONNECTION_TYPE
	  * 	Connection Type: HTTP/HTTPS
	  * @param REQUEST_TYPE
	  * 	Request Type: GET, POST, PUT, DELETE
	  * @param REQUEST_URL
	  * 	The URL to send request (without http or https)
	  * @param PAYLOAD
	  * 	Payload will be ignored if CONNECTION_TYPE = GET
	  * @param AUTH_TYPE
	  * 	BasicAuth, X-API-KEY, BearerToken, X-Token, AWS, NOAUTH
	  * 	When it is BasicAuth, please call setPOST_Username and setPOST_Password to set auth value
	  * 	When it is AWS, please call setAWS methods to set auth value
	  * @param CONTENT_TYPE
	  * @param BYPASS_CERTIFICATION_VALIDATION
	  * 	if it is true, it will bypass the SSL certificate validation
	  * @return
	  * 	response data
	  * @throws Exception
	  */
	 @SuppressWarnings("null")
	public String funcSendRequest(
								   String CONNECTION_TYPE,
								   String REQUEST_TYPE,
								   String REQUEST_URL, 
								   String PAYLOAD,
								   String AUTH_TYPE,
								   String CONTENT_TYPE,
								   boolean BYPASS_CERTIFICATION_VALIDATION
								   )throws Exception {
	
		StringBuffer jsonString = new StringBuffer();
		/*
		* func_FIX_HTTPS_SSL_CERTIFICATES_VALIDATION_FAILURE 
		* is going to bypass the validation of SSL Certificate Validation
		*/
	
		boolean b_continue_to_process = false;

		if(BYPASS_CERTIFICATION_VALIDATION){
			if(this.func_FIX_HTTPS_SSL_CERTIFICATES_VALIDATION_FAILURE()){
				b_continue_to_process = true;
			}
		}else{
			b_continue_to_process = true;
		}
	
	
		if(b_continue_to_process){
				
			String s_REQUEST_URL = REQUEST_URL;
			
			String s_HOST = HttpUtilities.getURL_HOST(s_REQUEST_URL);
			String s_Path = HttpUtilities.getURL_Path(s_REQUEST_URL);
			String s_QueryString = HttpUtilities.getURL_QUERYString(s_REQUEST_URL);

			URL url = null;	
			String s_Encoded_URL = s_HOST 
								//+ HttpUtilities.URLEncoder(s_Path,true) 
								+ s_Path
								+ "?" 
								+ HttpUtilities.URLEncoder(s_QueryString,true);
				
			switch(CONNECTION_TYPE){
				case("HTTP"):{
					url = new URL("http://" + s_Encoded_URL);
					break;
				}
				case("HTTPS"):{
					url = new URL("https://" + s_Encoded_URL);
					break;
				}
				default:{
					url = new URL("http://" + s_Encoded_URL);
					break;
				}
			}
				
			/*
			 * If the auth_type = AWS, it will going to get auth by AWSV4
			 * If the auth_type is other than AWS, it will go through regular auth methods
			 * and invoke connections
			 */
			if(AUTH_TYPE.equalsIgnoreCase("aws")){
				if(REQUEST_TYPE.equalsIgnoreCase("get")) {
					Map<String, String> awsHeaders = new HashMap<String, String>();
					Map<String, String> queryParametes = new HashMap<String, String>();
					
					awsHeaders.put("Content-Type".toLowerCase(),CONTENT_TYPE.toLowerCase());
					awsHeaders.put("x-amz-content-sha256",AWS4SignerBase.EMPTY_BODY_SHA256);
					
					queryParametes = HttpUtilities.getQueryParameters(s_QueryString);
					
					AWS4SignerForAuthorizationHeader signer = new AWS4SignerForAuthorizationHeader(
							url, REQUEST_TYPE, s_AWS_SERVICE, s_AWS_REGION);
					
					String authorization = signer.computeSignature(awsHeaders, 
							queryParametes,
			                AWS4SignerBase.EMPTY_BODY_SHA256, 
			                s_AWS_KEY, 
			                s_AWS_SECRET);
					
					awsHeaders.put("Authorization", authorization);
					
					jsonString.append(HttpUtilities.invokeHttpRequest(url, REQUEST_TYPE, awsHeaders, null));
				}else{
					// pre-compute hash of the body content
			        byte[] contentHash = AWS4SignerBase.hash(PAYLOAD);
			        String contentHashString = BinaryUtils.toHex(contentHash);
			        
			        Map<String, String> headers = new HashMap<String, String>();
			        headers.put("x-amz-content-sha256", contentHashString);
			        headers.put("content-length", "" + PAYLOAD.length());
			        headers.put("x-amz-storage-class", "REDUCED_REDUNDANCY");
			        headers.put("content-type", CONTENT_TYPE.toLowerCase());
			        
			        AWS4SignerForAuthorizationHeader signer = new AWS4SignerForAuthorizationHeader(
			        		url, REQUEST_TYPE, s_AWS_SERVICE, s_AWS_REGION);
			        
			        String authorization = signer.computeSignature(headers, 
							null,
							contentHashString, 
							s_AWS_KEY, 
							s_AWS_SECRET);
					
					headers.put("Authorization", authorization);
					
					jsonString.append(HttpUtilities.invokeHttpRequest(url, REQUEST_TYPE, headers, PAYLOAD));
					
					if(b_DEBUG) {
						System.out.println("s_REQUEST_URL: " + s_REQUEST_URL);
						System.out.println("Host: " + s_HOST);
						System.out.println("Path: " + s_Path);
						System.out.println("QueryString: " + s_QueryString);	
						System.out.println("url: " + url);
						System.out.println("payload: " + PAYLOAD);
					}
				}
			}else{
				HttpURLConnection connection = null;	
				
				try {
					switch(CONNECTION_TYPE){
						case("HTTP"):{
							connection = (HttpURLConnection)url.openConnection();
							break;
						}
						case("HTTPS"):{
							connection = (HttpsURLConnection) url.openConnection();
							break;
						}
						default:{
							connection = (HttpsURLConnection) url.openConnection();
							break;
						}
					}
					connection.setAllowUserInteraction(true);
					
					
					if(b_DEBUG) {
						System.out.println("s_REQUEST_URL: " + s_REQUEST_URL);
						System.out.println("Host: " + s_HOST);
						System.out.println("Path: " + s_Path);
						System.out.println("QueryString: " + s_QueryString);
						System.out.println("Payload: " + PAYLOAD);
						System.out.println("url: " + url);
					}
					
					connection.setUseCaches(false);
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestMethod(REQUEST_TYPE);
					connection.setRequestProperty("Accept".toLowerCase(), "application/json");
					connection.setRequestProperty("Content-Type".toLowerCase(), CONTENT_TYPE.toLowerCase());
					
					
					switch(AUTH_TYPE){
						case("BasicAuth"):{
							String strAuthentication = this.s_POST_Username + ":" + this.s_POST_Password;
							String strEncoding = Base64.getEncoder().encodeToString((strAuthentication).getBytes("UTF-8"));
							connection.setRequestProperty("Authorization", "Basic " + strEncoding);
							break;
						}
						case("X-API-KEY"):{
							connection.setRequestProperty("x-api-key", this.s_X_API_KEY_VALUE);
							break;
						}
						case("BearerToken"):{
							connection.setRequestProperty("Authorization", "Bearer " + this.s_Bearer_TOKEN);
							break;
						}
						case("X-Token"):{
							connection.setRequestProperty("X-AUTH-TOKEN", this.s_X_AUTH_TOKEN);
							break;
						}
						case("NOAUTH"):{
							break;
						}
					}
					
					switch(REQUEST_TYPE){
						case("POST"):{
							OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
							writer.write(PAYLOAD);
							writer.close();
							break;
						}
						case("PUT"):{
							OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
							writer.write(PAYLOAD);
							writer.close();
							break;
						}
						 case("DELETE"):{
							OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
							writer.write(PAYLOAD);
							writer.close();
							break;
						 }
						 case("GET"):{
							 break;
						 }
					}

					this.s_RESPONSE_CODE = String.valueOf(connection.getResponseCode());
					
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					
					String line;
					while ((line = br.readLine()) != null) {
					     jsonString.append(line);
					}
					br.close();
					
					connection.disconnect();
					
				}catch (Exception e) {

					InputStream is_data;
					is_data = connection.getErrorStream();
					if(is_data != null) {
						BufferedReader br = new BufferedReader(new InputStreamReader(is_data));
						String line;
						while ((line = br.readLine()) != null) {
						     jsonString.append(line);
						}
						br.close();
					}
					connection.disconnect();
					if(bool_PRINT_OUT_REQUEST_ERROR){
						e.printStackTrace();
						System.out.println("Error: " + e.toString());
					}
					
				}
			}
		}
		if(b_DEBUG) {
			System.out.println("response_data: " + jsonString.toString());
		}
		return jsonString.toString();
	}	
	 
	/**
	 * 
	 * @param s_File
	 * @param s_Destination
	 * @param s_Password
	 * @return
	 * @throws Exception
	 */
	public boolean funcUnZipFile(String s_File, 
			 				  String s_Destination, 
			 				  String s_Password)throws Exception{
		boolean bool_UnZipFile = false;
		
		try {
			ZipFile zipFile = new ZipFile(s_File);
	         if (zipFile.isEncrypted()) {
	            zipFile.setPassword(s_Password);
	         }
	         zipFile.extractAll(s_Destination);
	         bool_UnZipFile = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bool_UnZipFile;
	}
}
