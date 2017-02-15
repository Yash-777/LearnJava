package com.github.jars;

import java.io.InputStream;
import java.util.Scanner;

public class SubProcessRunnable implements Runnable {

	private InputStream inputStream;
	
	public SubProcessRunnable(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void run() {
		Scanner scanner = new Scanner( inputStream );
		scanner.useDelimiter("\r\n");
		while (scanner.hasNext()) {
			System.out.println(scanner.next());
		}
		scanner.close();
		
		/*byte input[] = new byte[ inputStream.available() ];
		inputStream.read( input, 0, input.length );
		System.out.println("Input Stream « "+ new String( input ) );*/
	}
}