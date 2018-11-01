package com.github.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Download the file and maintain locally.
 * 
 * @author yashwanth.m
 *
 */
public class FilesActions {
	
	public static void main(String[] args) throws IOException {
		
		String userDir = System.getProperty("user.dir");
		String folderName = userDir+File.separator+"MyReports"+System.currentTimeMillis();
		System.out.println("Folder Path : "+ folderName );
		createFolder(folderName);
	}
	public static void createFolder(String folderPath) { // Java < 7
		File file = new File( folderPath );
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}
	public static void createFolderFlow(String foldersPath) {
		File file = new File( foldersPath );
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to multiple directories!");
			}
		}
	}

	public static void createDirectory( String folderName ) { // Java 7
		File folder = new File( folderName );
		if ( !folder.exists()) {
			try {
				Files.createDirectories(Paths.get( folderName ));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Created Directory Sucess fully : "+folderName);
		} else {
			System.out.println("Directory available locally.");
		}
	}
}