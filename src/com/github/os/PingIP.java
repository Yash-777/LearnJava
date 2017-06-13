package com.github.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingIP {
	public static void main(String[] args) throws IOException, InterruptedException {
		String command = 
				"ping 127.0.0.1";
				//"ping www.google.com";
	
		Process proc = Runtime.getRuntime().exec(command);
		// Read the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	
		String line = "";
		while((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
		}
		proc.waitFor();
	}
}
