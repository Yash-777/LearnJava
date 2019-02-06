package com.github.files;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * https://stackoverflow.com/a/50581691/5081877
 * 
 * @author yashwanth.m
 *
 */
public class ReadingFileAsString {
	public static void main(String[] args) throws IOException {
		String fileName = "E:/parametarisation.csv";
		File file = new File( fileName );
		
		String fileStream = commons_FileUtils( file );
				// guava_DiskFile( file );
				// streamFile_Buffer( file );
				// getDiskFile_Java7( file );
				// getDiskFile_Lines( file );
		System.out.println( " File Over Disk : \n"+ fileStream );
		
		try {
			String src = "https://code.jquery.com/jquery-3.2.1.js";
			URL url = new URL( src );
			
			String urlStream = commons_IOUtils( url );
					// guava_ServerFile( url );
					// streamURL_Scanner( url );
					// streamURL_Buffer( url );
			System.out.println( " File Over Network : \n"+ urlStream );
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	// BufferedReader with InputStreamReader.
	@SuppressWarnings("resource")
	public static String getDiskFile_Lines( File file ) throws IOException {
		StringBuffer text = new StringBuffer();
		FileInputStream fileStream = new FileInputStream( file );
		BufferedReader br = new BufferedReader( new InputStreamReader( fileStream ) );
		for ( String line; (line = br.readLine()) != null; )
			text.append( line + System.lineSeparator() );
		return text.toString();
	}
	
	// Java 7 (java.nio.file.Files.readAllBytes)
	public static String getDiskFile_Java7( File file ) throws IOException {
		byte[] readAllBytes = java.nio.file.Files.readAllBytes(Paths.get( file.getAbsolutePath() ));
		return new String( readAllBytes );
	}
	
	// Java 8 using Stream API for BufferReader.
	public static String streamURL_Buffer( URL url ) throws IOException {
		java.io.InputStream source = url.openStream();
		BufferedReader reader = new BufferedReader( new InputStreamReader( source ) );
		//List<String> lines = reader.lines().collect( Collectors.toList() );
		return reader.lines().collect( Collectors.joining( System.lineSeparator() ) );
	}
	@SuppressWarnings("resource")
	public static String streamFile_Buffer( File file ) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( file ) );
		return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	}
	
	static String charsetName = java.nio.charset.StandardCharsets.UTF_8.toString();
	// Scanner Class with regex `\A`. which matches the beginning of input.
	@SuppressWarnings("resource")
	public static String streamURL_Scanner( URL url ) throws IOException {
		java.io.InputStream source = url.openStream();
		Scanner scanner = new Scanner(source, charsetName).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}
	@SuppressWarnings("resource")
	public static String streamFile_Scanner( File file ) throws IOException {
		Scanner scanner = new Scanner(file, charsetName).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}
	
	// Guava: Google using class Resources, Files
	static Charset charset = com.google.common.base.Charsets.UTF_8;
	public static String guava_ServerFile( URL url ) throws IOException {
		return Resources.toString( url, charset );
	}
	public static String guava_DiskFile( File file ) throws IOException {
		return Files.toString( file, charset );
	}
	
	// APACHE - COMMONS IO using classes IOUtils, FileUtils
	static Charset encoding = org.apache.commons.io.Charsets.UTF_8;
	public static String commons_IOUtils( URL url ) throws IOException {
		java.io.InputStream in = url.openStream();
		try {
			return IOUtils.toString( in, encoding );
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	public static String commons_FileUtils( File file ) throws IOException {
		return FileUtils.readFileToString( file, encoding );
		/*List<String> lines = FileUtils.readLines( fileName, encoding );
		return lines.stream().collect( Collectors.joining("\n") );*/
	}
	
	public String getBase64String( String fileLocation ) {
		try {
			File file = new File( fileLocation );
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			byte[] byteArray = new byte[(int) file.length()];
			
			try {
				dis.readFully(byteArray); // now the array contains the image
			} catch (Exception e) {
				byteArray = null;
			} finally {
				dis.close();
			} // org.apache.commons.codec.binary.
			String encodedfile = new String(Base64.encodeBase64( byteArray ), "UTF-8");
			return encodedfile;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
