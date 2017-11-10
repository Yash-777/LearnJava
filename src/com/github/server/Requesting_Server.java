package com.github.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * https://stackoverflow.com/questions/18148932/how-can-i-send-http-request-to-another-servlet
 * 
 * @author yashwanth.m
 *
 */
public class Requesting_Server {
	
	public static void main(String[] args) throws IOException {

		String targetURL = "http://localhost:8080/ServletApplication/sample";
		String urlParameters = "param1=value11&param2=value12";

		String response = "";
		// java.awt.Desktop.getDesktop().browse(java.net.URI.create( targetURL+"?"+urlParameters ));
		// response = accessResource_JAVA_IO( "POST", targetURL, urlParameters );
		// response = accessResource_Appache_commons( targetURL );
		// response = accessResource_Appache( targetURL );
		
		response = accessResource_JERSY( targetURL+"?"+urlParameters );
		System.out.println("Response:"+response);
	}
	
	public static String accessResource_JAVA_IO(String httpMethod, String targetURL, String urlParameters) {
		HttpURLConnection con = null; 
		BufferedReader responseStream = null;
		try {
			if (httpMethod.equalsIgnoreCase("GET")) {
				URL url = new URL( targetURL+"?"+urlParameters ); 
				responseStream = new BufferedReader(new InputStreamReader( url.openStream() ));
			} else if (httpMethod.equalsIgnoreCase("POST")) {
				con = (HttpURLConnection) new URL(targetURL).openConnection();
				// inform the connection that we will send output and accept input
				con.setDoInput(true);   con.setDoOutput(true);  con.setRequestMethod("POST");
				con.setUseCaches(false); // Don't use a cached version of URL connection.
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
				con.setRequestProperty("Content-Language", "en-US");

				DataOutputStream requestStream = new DataOutputStream ( con.getOutputStream() );
				requestStream.writeBytes(urlParameters);
				requestStream.close();

				responseStream = new BufferedReader(new InputStreamReader( con.getInputStream(), "UTF-8" ));
			}

			StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			String line;
			while((line = responseStream.readLine()) != null) {
				response.append(line).append('\r');
			}
			responseStream.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(con != null) con.disconnect();
		}
	}
	
	public static void http_IO_String_GET( String servletURL ) {
		try {
			String query = String.format("param1=%s&param2=%s", 
						URLEncoder.encode("param1Value", "UTF-8"), 
						URLEncoder.encode("param1Value", "UTF-8"));

			URL url = new URL(servletURL + "?" + query);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			Map<String, List<String>> header = conn.getHeaderFields();
			int responseCode = conn.getResponseCode();
			System.out.println("Headers : "+header);
			System.out.println("Response Code "+responseCode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String accessResource_Appache_commons(String url){
		String response_String = null;

		HttpClient client = new HttpClient();

		GetMethod method = new GetMethod( url ); 
		// PostMethod method = new PostMethod( url );
		method.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");
		method.setQueryString(new NameValuePair[] { 
			new NameValuePair("param1","value1"),
			new NameValuePair("param2","value2")
		}); //The pairs are encoded as UTF-8 characters. 
		try{
			int statusCode = client.executeMethod(method);
			System.out.println("Status Code = "+statusCode);

			//Get data as a String OR BYTE array method.getResponseBody()
			response_String = method.getResponseBodyAsString();
			method.releaseConnection();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return response_String;
	}
	
	public static String accessResource_Appache(String url) throws ClientProtocolException, IOException{
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url )
									.addParameter("param1", "appache1")
									.addParameter("param2", "appache2");

			HttpGet method = new HttpGet( builder.build() );
			// HttpPost method = new HttpPost( builder.build() );

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
			return httpclient.execute( method, responseHandler );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// JERSY using JARS {client, core, server} - 	artifact/com.sun.jersey/jersey-client
	public static String accessResource_JERSY( String url ){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource( url );
		ClientResponse response = service.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			System.out.println("GET request failed >> "+ response.getStatus());
		}else{
			String str = response.getEntity(String.class);
			if(str != null && !str.equalsIgnoreCase("null") && !"".equals(str)){
				return str;
			}
		}
		return "";
	}
}
