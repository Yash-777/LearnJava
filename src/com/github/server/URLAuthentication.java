package com.github.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * The class URLAuthenticationt provides some methods to authenticate the HTTP requests.
 * By creating the client with a credentials provider to set it up with Basic Authentication.
 * 
 * <a href="http://www.baeldung.com/httpclient-4-basic-authentication">Basic Authentication Link</a>
 * @author yashwanth.m
 *
 */
public class URLAuthentication {
	static String userName = "yash", password ="Yash777",
			targetURL = "http://localhost:8080/SteamingServlet/auth/BasicAuthentication";
	
	enum AuthenticationType {
		PRE_CACHED_SCHEME, POST_RESPONSE_SCHEME;
	};
	
	public static void main(String[] args) {
		//clientServerBasicAuthentication(targetURL, AuthenticationType.POST_RESPONSE_SCHEME);
		basicURLAuthentication(targetURL);
	}
	
	/**
	 * Out of the box, the HttpClient doesn’t do preemptive authentication 
	 * – this has to be an explicit decision made by the client.
	 * <ul>
	 * <li> <b>Post-Authentication</b>The standard way of configuring Basic Authentication on the
	 * HttpClient – via a CredentialsProvider {(OR) Enter URL in your browser for server challenge.}
	 * <ul>The entire Client-Server communication is now clear:
	 * <li>the Client sends the HTTP Request with no credentials</li>
	 * <li><b>the Server sends back a challenge to select right authentication scheme</b></li>
	 * <li>then Client negotiates and identifies the right authentication scheme</li>
	 * <li>the Client sends a second Request, this time with credentials</li></ul></li>
	 * 
	 * <li> <b>Pre-Authentication</b> An explicit decision made by the client.First, 
	 * we need to create the HttpContext – pre-populating it with an authentication cache 
	 * with the right type of authentication scheme pre-selected.
	 * <p><b>The “Basic Authentication” scheme is pre-selected and clients sends a single request with 
	 * Authentication details in header.</b></li>
	 * </ul>
	 * @param SECURED_URL
	 */
	@SuppressWarnings("static-access")
	public static void clientServerBasicAuthentication(String SECURED_URL, AuthenticationType type) {
		final CredentialsProvider CREDS_PROVIDER = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
		CREDS_PROVIDER.setCredentials(AuthScope.ANY, credentials);
		
		try {
			HttpClientBuilder clientBuilder = HttpClientBuilder.create();
			HttpGet httpGet = new HttpGet(SECURED_URL);
			HttpResponse response = null;
			if( type.equals( type.PRE_CACHED_SCHEME ) ) {
				System.out.println("CACHE - Add AuthCache to the execution context");
				String[] host = ParseURL.paramsHTTPHost(SECURED_URL);
				HttpHost targetHost = new HttpHost(host[1], Integer.valueOf(host[2]), host[0]);
				AuthCache authCache = new BasicAuthCache();
				authCache.put(targetHost, new BasicScheme());
				
				final HttpClientContext context = HttpClientContext.create();
				context.setCredentialsProvider(CREDS_PROVIDER);
				context.setAuthCache(authCache);
				
				HttpClient client = clientBuilder.build();
				response = client.execute( httpGet, context );
			} else if( type.equals( type.POST_RESPONSE_SCHEME ) ) {
				System.out.println("DEFAULT - Assign default CREDS_PROVIDER instance to Client ");
				clientBuilder.setDefaultCredentialsProvider(CREDS_PROVIDER);
				
				HttpClient client = clientBuilder.build();
				response = client.execute( httpGet );
			}
			
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("HttpClient Status Code : "+statusCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void basicURLAuthentication(String targetURL) {
		try {
			URL url = new URL( targetURL );
			//URLConnection uc = url.openConnection();
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			uc.setRequestProperty ("Authorization", getBasicAuthenticationEncoding(userName, password));
			//InputStream in = uc.getInputStream();
			uc.connect();
			int statusCode = uc.getResponseCode();
			System.out.println("HttpURLConnection Status Code : "+statusCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static String getBasicAuthenticationEncoding(String userName, String password) {
		String userPassword = userName + ":" + password;
		byte[] encodeBase64 = Base64.encodeBase64(userPassword.getBytes());
		// Java 7	Base64.encodeBase64(userPassword.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String( encodeBase64 );
		return authHeader;
	}
}