package com.github.server;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RemoteClient {
	static String userName = "yash", password ="Yash777",
			targetURL = "http://localhost:8080/SteamingServlet/wd/hub/session";
	
	public static void main(String[] args) {
		try { // 401 : This request requires HTTP authentication.
			String response = accessSecuredResource_AppachePOST(targetURL);
			System.out.println("Remote Server Response : "+response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static String accessSecuredResource_AppachePOST(String SECURED_URL) throws ClientProtocolException, IOException{
		
		HttpPost method = new HttpPost( SECURED_URL );
		
		String json = "{'desiredCapabilities':{'browserName':'firefox','version':'','platform':'ANY'}}";
		method.setHeader("Content-Type", "application/json");
		StringEntity xmlEntity = new StringEntity(json);
		method.setEntity(xmlEntity);
		
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		final CredentialsProvider CREDS_PROVIDER = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
		CREDS_PROVIDER.setCredentials(AuthScope.ANY, credentials);
		clientBuilder.setDefaultCredentialsProvider(CREDS_PROVIDER);
		HttpClient client = clientBuilder.build();
			
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse( final HttpResponse response) throws IOException {
				int status = response.getStatusLine().getStatusCode();
				if ( (status >= 200 && status <= 500) || status == 401 ){
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
				return "";
			}
		};
		return client.execute( method, responseHandler );
	}
}
