package com.github.server;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The class {@link ParseURL} provides methods to break URL into several parts.
 * <p>EX: http://www.example.com:1080/docs/resource1.html
 * @author yashwanth.m
 *
 */
public class ParseURL {
	public static void main(String[] args) {
		String[] urls = 
			{"http://www.google.co.in/",
			"http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING",
			"https://stackoverflow.com/a/15076736/5081877",
			"http://localhsot:8080/applicationname/resourcepath"};
		//stringURLParse( urls[3] );
		paramsHTTPHost( urls[0] );
	}
	
	public static void stringURLParse(String url) {
		try {
			URL aURL = new URL( url );
			
			System.out.println("protocol = " + aURL.getProtocol());
			System.out.println("authority = " + aURL.getAuthority());
			System.out.println("host = " + aURL.getHost());
			System.out.println("port = " + aURL.getPort());
			System.out.println("path = " + aURL.getPath());
			System.out.println("query = " + aURL.getQuery());
			System.out.println("filename = " + aURL.getFile());
			System.out.println("ref = " + aURL.getRef());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] paramsHTTPHost(String url) {
		try {
			URL aURL = new URL( url );
			String protocol = aURL.getProtocol();
			String host = aURL.getHost();
			int port = aURL.getPort();
			
			if(port == -1) {
				if(protocol.equals("https")) port = 443;
				else if(protocol.equals("http")) port = 80;
			}
			
			String[] paramValues = {protocol, host, Integer.toString(port)};
			System.out.format("URL {protocol:[%s], host:[%s], port:[%s]}\n",
					paramValues[0], paramValues[1], paramValues[2]);
			return paramValues;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
