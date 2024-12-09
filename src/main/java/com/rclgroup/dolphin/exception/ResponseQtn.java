package com.rclgroup.dolphin.exception;
import org.json.JSONObject;

import com.rclgroup.dolphin.utils.Constants;


public class ResponseQtn {
public static String messageString(String resultCode, String resultContent, String resultMessage, String resultStatus) {
		
    	JSONObject json = new JSONObject();
    	json.put("resultCode", resultCode);
    	json.put("resultContent", resultContent);
    	json.put("resultMessage", resultMessage+" ERROR");
    	json.put("resultStatus", resultStatus);
    	
		return json.toString();
	}
	
	public static JSONObject messageJson(String resultCode, String resultContent, String resultMessage, String resultStatus) {
		
    	JSONObject json = new JSONObject();
    	json.put("resultCode", resultCode);
    	json.put("resultContent", resultContent);
    	json.put("resultMessage", resultMessage+" ERROR");
    	json.put("resultStatus", resultStatus);
    	
		return json;
	}
	
	public static JSONObject messageJson(String strJson) {		
    	JSONObject json = new JSONObject(strJson);
	
		  if(json.get("resultStatus").equals(Constants.FAILED)) {
		  json.put("resultCode", "401"); }else
		  {
        	json.put("resultCode", "200");
    	}
return json;
}

	public ResponseQtn() {
		super();
		// TODO Auto-generated constructor stub
	}

    	
		
	
}
