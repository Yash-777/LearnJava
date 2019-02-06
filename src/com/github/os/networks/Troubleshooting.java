package com.github.os.networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.os.threads.ThreadsFlow;
import com.github.os.threads.ThreadsUtil;

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
		
		ThreadsUtil.getUserAcceptence("Close Command Prompt.");
		
		process.waitFor();
	}
	
	static String getProcessOutPut( Process proc ) throws IOException, InterruptedException {
		// Read the output
		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	
		StringBuffer buffer = new StringBuffer();
		String line = "";
		boolean isFirstLine = true;
		while((line = reader.readLine()) != null) {
			
			if( !isFirstLine ) {
				buffer.append("\n");
			} else {
				isFirstLine = false;
			}
			System.out.print(line + "\n");
			buffer.append(line);
			//ThreadsFlow.sleepThread(2);
		}
		return buffer.toString();
	}
}
