package com.github.jars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The interaction between reading from the sysout of the subprocess and writing
 * sysout to current process which leads to slow in performance.
 * <P><a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=166119">Reading Performance</a></P>
 * 
 * @author yashwanth.m
 *
 */
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
			/*Reads the next byte of data from the input stream. The value byte is returned as an int in the range 0 to 255.
			 * If no byte is available because the end of the stream has been reached, the value -1 is returned.
			 * This method blocks until input data is available, the end of the stream is detected, or an exception is thrown. */
			// while( ( inputStream.read() ) != -1) {   }
			/*Reads a line of text. A line is considered to be terminated by any one of these '\n', '\r', '\r\n'*/
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