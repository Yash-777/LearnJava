package com.github.files;

public class FileNameUtil {
	public static final int MAX_FILENAME_LENGTH = 201;
	public static void main(String[] args) throws Exception {
		System.out.println( getFileName("file.txt", ".txt") );
		System.out.println( getFileName(" fileName.sh", ".sh ") );
		
		String fileName = getFileFromPath("D:/Folder1/FileName.txt");
		System.out.println("File Name : "+ fileName );
		System.out.println( getFileName( fileName, ".txt ") );
		
		
		System.out.println("user.home : "+ System.getProperty("user.home") ); // C:\Users\yashwanth.m

	}
	
	public static String getFileFromPath(String filePath) throws Exception {
		String erroeMessage = null;
		if ( filePath != null ) {
			filePath = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());
			return filePath;
		} else {
			erroeMessage = "File Name or Extension is null.";
			throw new NullPointerException( erroeMessage );
		}
	}
	/**
	 * @param file « fileWithExtension
	 * @param extension
	 */
	public static String getFileName( String file, String extension ) throws Exception {
		String erroeMessage = null;
		if ( file != null && extension != null ) {
			file = file.trim();
			extension = extension.trim();
			
			if( extension.length() < 2 ) {
				erroeMessage = "Invalid file extension : "+extension;
				throw new Exception( erroeMessage );
			} else if ( file.length() == 0 && file.length() >= MAX_FILENAME_LENGTH ) {
				erroeMessage = "File length is too long : "+file.length();
				throw new Exception( erroeMessage );
			} else {
				file = file.substring( 0, file.length() - extension.length() );
				return file;
			}
		} else {
			erroeMessage = "File Name or Extension is null.";
			throw new NullPointerException( erroeMessage );
		}
	}
}
