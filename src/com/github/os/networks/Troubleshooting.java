package com.github.os.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Troubleshooting {
	public static void main(String[] args) throws IOException, InterruptedException {
		commandPromptConsole( "ping", "stackoverflow.com" ); // cmd /C
		//commandPromptConsole( "tracert", "stackoverflow.com" );
		
		commandPromptWindow( "ping", "stackoverflow.com" );
	}
	
	static void commandPromptConsole( String command, String params ) throws IOException, InterruptedException {
		Process proc = Runtime.getRuntime().exec("cmd.exe /C "+ command +" "+ params);
		
		getProcessOutPut(proc);
		
		proc.waitFor();
	}
	
	static void commandPromptWindow(  String command, String params ) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec( "cmd.exe /c start "+ command +" "+ params );
		
		getUserAcceptence();
		
		process.waitFor();
	}
	static void getUserAcceptence() {
		System.out.println("Enter Some thing to quit the command prompt.");
		try {
			System.in.read();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Thanks for your confromation>");
	}
	static void getProcessOutPut( Process proc ) throws IOException, InterruptedException {
		// Read the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	
		String line = "";
		while((line = reader.readLine()) != null) {
			System.out.print(line + "\n");
		}
	}
}
