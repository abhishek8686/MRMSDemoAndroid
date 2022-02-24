package com.example.demoapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.Settings.Secure;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.UUID;

public class MRMSAndroid {
	
	private String apiURL;
	private String deviceAPIURL;
	private static final String TAG = "MRMSAndroid";
	
	MRMSAndroid() {
		apiURL = "https://v5tp-api.testrmsid.com/api/txn/";
        deviceAPIURL = "https://dfp.testrmsid.com/api/assets/android.png";
	}
	
	MRMSAndroid(boolean demo) {
		if (demo) {
            apiURL = "https://v5tp-api.testrmsid.com/api/txn/";
            deviceAPIURL = "http://dfp.testrmsid.com/api/assets/android.png";
        }
        else {
            apiURL = "https://v5tp-api.testrmsid.com/api/txn/";
            deviceAPIURL = "https://dfp.testrmsid.com/api/assets/android.png";
        }	
	}
	
	String createSession() {
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		String hashText = "";
		
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(uuidString.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashText = bigInt.toString(16);
			while(hashText.length() < 32 ){
				hashText = "0"+hashText;
			}			
		} catch (Exception e) {
			hashText = "ERROR";
		}
		
		return hashText;
	}
	
	JSONObject callAPIendpoint(String endPoint, JSONObject params, String method) throws Exception {
		
		JSONObject result = new JSONObject();
		
		try {
			Iterator<?> keys = params.keys();
			String parameterString = "",s;
			int i = 0;
	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            if (i == 0) s = key+"="+URLEncoder.encode(params.getString(key));
	            else s = "&"+key+"="+URLEncoder.encode(params.getString(key));
	            parameterString = parameterString + s;        
	            i++;
	        }
	        //parameterString = URLEncoder.encode(parameterString);
	        //Log.d("MRMS",parameterString);
	        
	        String u = "";
	        if (method.equalsIgnoreCase("GET")) {
	        	u = apiURL+endPoint+".xml?"+parameterString;
	        } else {
	        	u = apiURL+endPoint+".xml";
	        }
	        Log.d("MRMS","URL :"+u);	 
    		URL url = new URL(u);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestProperty("Accept", "application/json");
    		
    		if (method.equalsIgnoreCase("GET")) conn.setRequestMethod("GET");
    		else {
    			conn.setRequestMethod("POST");
    			conn.setDoOutput(true);    			 
    			OutputStream os = conn.getOutputStream();
    			os.write(parameterString.getBytes());
    			os.flush();
    		}
    		
     
    		result.put("statusCode",new Integer(conn.getResponseCode()));
     
    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));
     
    		String output = "",o;
    		while ((o = br.readLine()) != null) {
    			output = output + o;
    		}
    		result.put("response",output);
     
    		conn.disconnect();
	     
	     	        
	        
		} catch (Exception e) {
			Log.e("MRMS",e.toString());
			result.put("statusCode",new Integer(0));
			result.put("error",e.getMessage());
		}
      
        
		return result;
	}
	
	JSONObject callDeviceAPI(JSONObject params,Context context) throws Exception {
		JSONObject result = new JSONObject();
		
		String android_id = Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID); 
		
		try {
			
			params.put("device_id",android_id);
			
			Iterator<?> keys = params.keys();
			String parameterString = "",s;
			int i = 0;
	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            if (i == 0) s = key+"="+URLEncoder.encode(params.getString(key));
	            else s = "&"+key+"="+URLEncoder.encode(params.getString(key));
	            parameterString = parameterString + s;        
	            i++;
	        }
	        
    		URL url = new URL(deviceAPIURL);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestProperty("Accept", "application/json");
    		
    		
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);    			 
			OutputStream os = conn.getOutputStream();
			os.write(parameterString.getBytes());
			os.flush();
		
    		
     
    		result.put("statusCode",new Integer(conn.getResponseCode()));
     
    		BufferedReader br = new BufferedReader(new InputStreamReader(
    			(conn.getInputStream())));
     
    		String output = "",o;
    		while ((o = br.readLine()) != null) {
    			output = output + o;
    		}
    		result.put("response",output);
     
    		conn.disconnect();
	        
		} catch (Exception e) {
			Log.e("MRMS",e.toString());
			result.put("statusCode",new Integer(0));
			result.put("error",e.getMessage());
		}
		
		//Log.d("MRMS",android_id);
		Log.d(TAG, "callDeviceAPI: "+result);
		return result;
	}
	
}
