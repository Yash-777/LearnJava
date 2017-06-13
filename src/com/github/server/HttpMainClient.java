package com.github.server;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * Ref : https://github.com/eugenp/tutorials/blob/master/httpclient/src/test/java/org/baeldung/httpclient/sec/HttpClientCookieLiveTest.java

 * @author yashwanth.m
 *
 */
public class HttpMainClient {
	
	static String targetURL = "http://localhost:8080/SteamingServlet/HttpConsumer";
	static String urlParameters = "param1=value11&param2=value12";
	public static void main(String[] args) throws IOException {

		String response = "";
		//java.awt.Desktop.getDesktop().browse(java.net.URI.create( targetURL+"?"+urlParameters ));
		System.out.println("GET Response: "+ accessResource_AppacheGET( targetURL ));
		
		response = accessResource_AppachePOST( targetURL );
		System.out.println("POST Response: "+ response);
		
		System.out.println("GET Response: "+ accessResource_AppacheDELETE( targetURL ));
	}
	
	public static String accessResource_AppacheGET(String url) throws ClientProtocolException, IOException{
		try {
			//CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url )
									.addParameter("param1", "appache1")
									.addParameter("param2", "appache2");

			final BasicCookieStore cookieStore = new BasicCookieStore();
			final BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
			cookie.setDomain(".github.com");
			cookie.setPath("/");
			cookieStore.addCookie(cookie);
			CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

			final HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

			HttpGet method = new HttpGet( builder.build() );
			method.setHeader("Cookie", "JSESSIONID=1234");
			
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse( final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status <= 500) {
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
	
	public static String accessResource_AppachePOST(String url) throws ClientProtocolException, IOException{
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url )
									.addParameter("param1", "appache1")
									.addParameter("param2", "appache2");

			//HttpGet method = new HttpGet( builder.build() );
			HttpPost method = new HttpPost( builder.build() );
			
			String json = "{'username':'yashwanth.merugu@gmail.com','password':'Yash@777'}";
			method.setHeader("Content-Type", "application/json");
			StringEntity xmlEntity = new StringEntity(json);
			method.setEntity(xmlEntity);
			
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse( final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status <= 500) {
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
	
	public static String accessResource_AppacheDELETE(String url) throws ClientProtocolException, IOException{
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url )
									.addParameter("param1", "appache1")
									.addParameter("param2", "appache2");

			HttpDelete method = new HttpDelete( builder.build() );
			
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse( final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status <= 500) {
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
}
