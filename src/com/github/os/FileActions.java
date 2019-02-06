package com.github.os;

/**
 * Files : https://docs.oracle.com/javase/tutorial/essential/io/file.html
 * 
 * <P>Stream Copy is the best way to copy File in Java.
 * <a href="http://www.journaldev.com/861/java-copy-file"> Reference URL </a></p>
 * @author yashwanth.m
 *
 */
public class FileActions implements Runnable {
	PojoData obj;
	FileActions( PojoData obj ) {
		this.obj = obj;
	}
	
	@Override
	public void run() {
		try {
			ChangeResolution.isFileReadStarted = true;
			System.out.println("File Read Start Status : "+ChangeResolution.isFileReadStarted);
			
			com.github.files.FilesActions.copyFileUsingStream(obj.inputFile, obj.outputFile);
			
			System.out.println("File Read Operation Completed.");
		} catch (Exception x) {
			System.err.println(x);
		}
	}
	
	

}