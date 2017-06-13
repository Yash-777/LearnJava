package com.github.os;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddress {
	public static void main(String[] args) throws UnknownHostException {
		System.out.println("IP : "+InetAddress.getLocalHost()); // IP : SD-35/192.168.1.57
		System.out.println("Info : "+getSystemInformation());
	}
	public static String getSystemInformation() {
		String host = "N/A";
		String ip   = "N/A";

		try{
		host = InetAddress.getLocalHost().getHostName();
		ip   = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException throw_away) {}

		return String.format("System info: host: '%s', ip: '%s', os.name: '%s', os.arch: '%s',"
				+ " os.version: '%s', java.version: '%s'",
		host,
		ip,
		System.getProperty("os.name"),
		System.getProperty("os.arch"),
		System.getProperty("os.version"),
		System.getProperty("java.version"));
	}
}
