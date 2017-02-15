package com.github.jars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubProcessThread extends Thread {
	private InputStream inputStream;
	private boolean sysout;
	public StringBuilder output = new StringBuilder();

	public SubProcessThread (InputStream inputStream, boolean sysout) {
		this.inputStream = inputStream;
		this.sysout = sysout;
	}

	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader( inputStream ));
		
		String line = "";
		try {
			while( (line = reader.readLine()) != null ) {
				if ( sysout ) {
					System.out.println( line );
				}
				output.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getOutput() {
		return output.toString();
	} 
}