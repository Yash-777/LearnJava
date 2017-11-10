package com.github.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class RStudio_Requests {
	public static void main(String[] args) {

		String host = "http://172.16.20.93:5656", path = "/ocpu/library/performance/R/Perforemnce",
				queryParams = "userId=1&scenarioId=4&version=0.1";
		
		try {
			TreeMap<String, Object> mapParams = new TreeMap<String, Object>();
			mapParams.put("userId", 1);
			mapParams.put("scenarioId", 4);
			mapParams.put("version", 0.1);
			
			String url = host+path;
			String responseMessage = accessResource_Appache(url, true, queryParams, mapParams, true, true);
			System.out.println("PPOST Request Message : \n"+ responseMessage);
			
			/**
			 * /ocpu/tmp/x02d59e5a0b/stdout
			 * /ocpu/tmp/x026da05ece/stdout
			 * 
			 * PPOST Request Message : 
				/ocpu/tmp/x0c1908e793/R/.val
				/ocpu/tmp/x0c1908e793/R/Perforemnce
				/ocpu/tmp/x0c1908e793/messages
				/ocpu/tmp/x0c1908e793/stdout
				/ocpu/tmp/x0c1908e793/warnings
				/ocpu/tmp/x0c1908e793/source
				/ocpu/tmp/x0c1908e793/console
				/ocpu/tmp/x0c1908e793/info
				/ocpu/tmp/x0c1908e793/files/DESCRIPTION
			 */
			String[] split = responseMessage.replaceAll("/ocpu/tmp/", "").split("/");
			for (String string : split) {
				String url2 = host+"/ocpu/tmp/"+string+"/stdout";
				System.out.println( string );
				String accessResource_Appache = accessResource_Appache(url2, false, queryParams, mapParams, false, false);
				System.out.println("Response : "+ accessResource_Appache);
				break;
				
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String accessResource_Appache(String url, Boolean isPost, String queryParams,
			TreeMap<String, Object> mapParams, boolean useMapParams, boolean useAnyParams)
			throws ClientProtocolException, IOException {
		
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url );
			if (isPost) {
				
				HttpPost method = new HttpPost( builder.build() );
				method.setHeader("Content-Type", "application/json");
				StringEntity xmlEntity = new StringEntity( Map2JSONString(mapParams) );
				method.setEntity(xmlEntity);
				
				return httpclient.execute( method, getResponseHandler() );
			} else {
				if (useAnyParams) {
					
					if (useMapParams) {
						Set<Entry<String, Object>> entrySet = mapParams.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							builder.addParameter( entry.getKey(), entry.getValue().toString() );
						}
					} else {
						String[] params = queryParams.split("&");
						for (String param : params) {
							String[] keyVlaue = param.split("=");
							builder.addParameter( keyVlaue[0] , keyVlaue[1] );
						}
					}
				}
				
				HttpGet method = new HttpGet( builder.build() );
				return httpclient.execute( method, getResponseHandler() );
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String Map2JSONString( TreeMap<String, Object> mapParams ) {
		JSONObject jsonObject = new JSONObject();
		
		Set<Entry<String, Object>> entrySet = mapParams.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			try {
				jsonObject.append( entry.getKey(), entry.getValue().toString() );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonObject.toString();
	}
	public static ResponseHandler<String> getResponseHandler() {
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse( final HttpResponse response) throws IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
				return "";
			}
		};
		return responseHandler;
	}
}
