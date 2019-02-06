package com.github.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileSet_Properties {
	public static void main(String[] args) throws Exception {
		System.out.println("===== File Propertie Details =====");
		String absoluteFilePath = "D:/DB_SQL.txt";
		File file = new File( absoluteFilePath );
		
		
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis : "+ currentTimeMillis);
		
		BasicFileAttributes attributes = getFileAttributes(file);
		FileTime lastModifiedTime = attributes.lastModifiedTime();
		long millis = lastModifiedTime.toMillis();
		System.out.println("+ Last Modified : "+ millis);
		long minutesDiff = ( (currentTimeMillis - millis)/60000 );
		long hoursDiff = minutesDiff/60;
		System.out.println("1 Minutes	60000 ms :: M:"+ minutesDiff + " H:"+hoursDiff);
		
		System.out.println("Get Creating Date : "+getFileDateString(attributes, false));
		System.out.println("Get Creating Date Java8 : "+getFileDateString_Java8(attributes));
		
		System.out.println("===== Folder Chanin Files =====");
		String folderPath = "D:/FOL/";
		File folder = new File( folderPath );
		
		String vpath = folder.getCanonicalPath(); // Path - D:/FOLDER/ « ConicalPath - D:/FOLDER
		System.out.println("Directory Canonical Path : "+ vpath);
		
		getDirectoryScanner(folder);
	}
	
	public static void folderPaths(File file) {
		Path paths = file.toPath();
		for (Path path : paths) {
			System.out.println("From Forword Path : "+path);
		}
		System.out.println(paths);
	}
	
	public static void getDirectoryScanner( File dir ) throws Exception {
		if (dir == null) {
			throw new Exception("No directory specified.");
		} else if ( !dir.exists() ) {
			throw new Exception(dir.getAbsolutePath() + " does not exist. [FolderNotFoundException]");
		} else if ((!dir.isDirectory()) && (dir.exists())) {
			throw new Exception(dir.getCanonicalPath() + " is not a directory.");
		}
		
		subFolderPath(dir);
		
		listDirectoryFiles(dir);
	}
	public static String subFolderPath( File dir ) throws IOException {
		String vpath = dir.getCanonicalPath();
		System.out.println("Directory Path : "+ vpath);
		if ((vpath.length() > 0) && (!vpath.endsWith(File.separator))) {
			vpath = vpath + File.separator;
			System.out.println("Directory Path : "+ vpath);
		}
		return vpath;
	}
	/**
	 * Returns an array of strings naming the files and directories in the
	 * directory denoted by this abstract pathname.
	 * @param dir
	 * @throws Exception
	 */
	private static void listDirectoryFiles(File dir) throws Exception {
		String vpath = dir.getCanonicalPath(); // Path - D:/FOLDER/ « ConicalPath - D:/FOLDER
		System.out.println("Directory Canonical Path : "+ vpath);
		
		String[] list = dir.list();
		if (list == null) {
			if (!dir.exists()) {
				throw new Exception(dir + " does not exist.");
			}
			if (!dir.isDirectory()) {
				throw new Exception(dir + " is not a directory.");
			}
			throw new Exception("IO error scanning directory '" + dir.getAbsolutePath() + "'");
		}
		
		for (String string : list) {
			String filePath = vpath +File.separator+ string;
			File file = new File( filePath );
			if( file.isDirectory() ) {
				System.out.println("Direcoty : "+ file.getAbsolutePath() );
				listDirectoryFiles(file);
			} else {
				System.out.println("File : "+ file.getAbsolutePath() );
			}
		}
	}
	
	public static BasicFileAttributes getFileAttributes( File file ) {
		BasicFileAttributes attributes = null;
		try {
			Path filePath = file.toPath();
			attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to get file attributes: "+ exception.getMessage());
		}
		return attributes;
	}
	static String datePattern = "HH:mm:ss dd/MM/yyyy";
	static DateFormat dateFormat = new SimpleDateFormat( datePattern );
	public static String getFileDateString( BasicFileAttributes attributes, boolean isLastModified ) {
		String date = "";
		
		FileTime creationTime = attributes.creationTime();
		FileTime lastModifiedTime = attributes.lastModifiedTime();
		System.out.println(creationTime+" is the same as "+lastModifiedTime);
		
		if( isLastModified ){
			date = dateFormat.format(lastModifiedTime.toMillis());
		} else {
			date = dateFormat.format(creationTime.toMillis());
		}
		/*Date creationDate = dateFormat.parse(dateFormat.format(creationTime.toMillis()));
		date = creationDate.getDate()+"/"+(creationDate.getMonth() + 1)+"/"+(creationDate.getYear() + 1900);*/
		return date;
	}
	public static String getFileDateString_Java8( BasicFileAttributes attributes ) {
		long cTime = attributes.creationTime().toMillis();
		ZonedDateTime t = Instant.ofEpochMilli(cTime).atZone(ZoneId.of("UTC"));
		String dateCreated = DateTimeFormatter.ofPattern( datePattern ).format(t);
		return dateCreated;
	}
}
