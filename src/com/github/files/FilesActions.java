package com.github.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.github.os.Platform;
/**
 * Download the file and maintain locally.
 * 
 * <P>Stream Copy is the best way to copy File in Java.
 * <a href="http://www.journaldev.com/861/java-copy-file"> Reference URL </a></p>
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
	/**
	 * Checks the directory already exists or not. if the directory does not exist, create it.
	 * 
	 * <P> http://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
	 * @param directoryName the name of the folder, you want to create.
	 */
	public void createUsersHomeFolder( String directoryName ) {
		Platform os = new Platform();
		int javaVersion = os.getJavaVersion();
		String path = os.getUsersHomeDir() + File.separator + directoryName;
		if ( javaVersion <= 6 ) {
			File theDir = new File( path );
			try {
				if ( !theDir.exists() ) {
					System.out.println("creating directory: " + directoryName);
					theDir.mkdir();
				}
			} catch(SecurityException se) {
				try {
					FileUtils.forceMkdir( theDir );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				Files.createDirectories(Paths.get( path ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	/**
	 * <B>Java Copy File – Stream</B>
	 * 
	 * <P>This is the conventional way of file copy in java, here we create two Files, source and destination.
	 * Then we create InputStream from source and write it to destination file using OutputStream for java
	 * copy file operation.</p>
	 * 
	 * @param source       « the input file
	 * @param dest         « the output file
	 * @throws IOException « if any file is unavailable then throws exception.
	 */
	public static void copyFileUsingStream(File source, File dest) throws IOException {
		long start = System.nanoTime();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
		System.out.println("Time taken by Stream Copy = "+(System.nanoTime()-start));
	}
	
	/**
	 * <B>Java Copy File – java.nio.channels.FileChannel</B>
	 * 
	 * <P>Java NIO classes were introduced in Java 1.4 and FileChannel can be used to copy file in java.
	 * According to transferFrom() method javadoc, this way of copy file is supposed to be faster than
	 * using Streams for java copy files.</P>
	 * 
	 * @param source       « the input file
	 * @param dest         « the output file
	 * @throws IOException « if any file is unavailable then throws exception.
	 */
	@SuppressWarnings("resource")
	public void copyFileUsingChannel(File source, File dest) throws IOException {
		long start = System.nanoTime();
		FileChannel sourceChannel = null, destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally{
			sourceChannel.close();
			destChannel.close();
		}
		System.out.println("Time taken by Channel Copy = "+(System.nanoTime()-start));
	}
	
	/**
	 * <b>Java Copy File – Apache Commons IO FileUtils</b>
	 * <p>Apache Commons IO FileUtils.copyFile(File srcFile, File destFile) can be used to copy file in java.
	 * If you are already using Apache Commons IO in your project, it makes sense to use this for code simplicity.
	 * Internally it uses Java NIO FileChannel, so you can avoid this wrapper method if you are not already using
	 * it for other functions.</p>
	 * 
	 * @param source       « the input file
	 * @param dest         « the output file
	 * @throws IOException « if any file is unavailable then throws exception.
	 */
	public void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
		long start = System.nanoTime();
		/*FileUtils.copyInputStreamToFile(fileInputStrean, new File("targetFile2.jpg"));*/
		FileUtils.copyFile(source, dest);
		System.out.println("Time taken by Apache Commons IO Copy = "+(System.nanoTime()-start));
	}
	
	/**
	 * <b>Java Copy File – Files class</b>
	 * <p>If you are working on Java 7 or higher, you can use Files class copy() method to copy file in java.
	 * It uses File System providers to copy the files.</p>
	 * 
	 * @param source       « the input file
	 * @param dest         « the output file
	 * @throws IOException « if any file is unavailable then throws exception.
	 */
	public void copyFileUsingJava7Files(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}
}