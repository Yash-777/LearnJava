package com.github.os;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * ZIPExtracter class is to extract the contents of a ZIP file.
 * @author yashwanth.m
 *
 */
public class ZIPExtracter extends Platform {

	// create a buffer to improve copy performance later.
	byte[] buffer = new byte[2048];
	
	public static void main(String[] args) {
		String chromeZIP = "D:\\LearnJava.zip";
		String outdir = "D:\\JARS\\";
		
		try {
			//new ZIPExtracter().extractZIP(chromeZIP, outdir);
			new ZIPExtracter().extractAll(chromeZIP, outdir);
			//new ZIPExtracter().exratctFileList(chromeZIP, outdir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used for reading files in the ZIP file format.
	 * Includes support for both compressed and uncompressed entries.
	 * 
	 * <P> Through this method we can extract files but not folders in it.
	 * 
	 * @param zipfilePath	the ZIP file location.
	 * @param outdir		the location to extract the file. 
	 * @return 
	 */
	public String exratctFileList( String zipfilePath, String outdir ) {
		String outpath = null;
		FileInputStream fis = null;
		ZipInputStream zipIS = null;
		ZipEntry zEntry = null;
		try {
			fis = new FileInputStream( zipfilePath );
			zipIS = new ZipInputStream( new BufferedInputStream( fis ) );
			
			// Use while loop to extract all the contents of ZIP file.
			if ( (zEntry = zipIS.getNextEntry()) != null ) {
				System.out.format( "Entry: %s len %d added %TD \n", 
						zEntry.getName(), zEntry.getSize(), new Date(zEntry.getTime()));
				
				outpath = outdir + "/" + zEntry.getName();
				FileOutputStream output = null;
				try {
					output = new FileOutputStream( outpath );
					int len = 0;
					if ( zEntry.getSize() == 0 ) {
						// It's a folder unable to extract it.
					} else {
						while ((len = zipIS.read(buffer)) > 0)
							output.write(buffer, 0, len);
					}
				} finally {
					if(output!=null) output.close();
				}
			}
			zipIS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outpath;
	}
	
	@SuppressWarnings("resource")
	public void extractZIP( String zipfilePath, String outdir ) throws IOException {
		ZipFile zipFile = new ZipFile( zipfilePath );
		System.out.println("zipFile : "+ zipFile);

		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while(entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			InputStream stream = zipFile.getInputStream( entry );
			
			String outpath = outdir + "/" + entry.getName();
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(outpath);
				int len = 0;
				if ( entry.getSize() == 0 ) {
					// It's a folder unable to extract it.
				} else {
					while ((len = stream.read(buffer)) > 0)
						output.write(buffer, 0, len);
				}
			} finally {
				if(output!=null) output.close();
				if(stream!=null) stream.close();
			}
		}
	}
	// https://en.wikipedia.org/wiki/File_URI_scheme
	// you will need to remove file:/ or file:/// for Windows and file:// for Linux.
	public void extractAll( String zipfilePath, String outdir) throws IOException{
		
		URI uri = null;
		int javaVersion = new Platform().getJavaVersion();
		
		if ( javaVersion > 6 ) {
			uri = new File( zipfilePath ).toURI();       // file:/127.0.0.1/D:/A_JARS/A/chromedriver_stream.zip
					// Paths.get( zipfilePath ).toUri(); // file:///D:/A_JARS/A/chromedriver_stream.zip
			System.out.println("URI : "+uri);
			
			final Path toDirectory = new File( outdir ).toPath();
			
			// https://bugs.openjdk.java.net/browse/JDK-7156873
			URI createURI = URI.create( "jar:" + uri );
			System.out.println("Created URI : "+createURI);
			
			Map<String, String> env = new HashMap<>();
			env.put("create", "true");
			FileSystem zipFs = FileSystems.newFileSystem( createURI, env);
			
			System.out.println("--"+ zipFs.getRootDirectories());
			for(Path root : zipFs.getRootDirectories()) {
				/**
				 * A file tree is walked depth first, but you cannot make any assumptions about the iteration order
				 * that sub-directories are visited. As for the order in which the files are read, 
				 * it depends (in the current implementation) on the supplied DirectoryStream
				 * 
				 * https://docs.oracle.com/javase/tutorial/essential/io/walk.html
				 * http://www.concretepage.com/java/jdk7/traverse-directory-structure-using-files-walkfiletree-java-nio2
				 */// Paths.get( zipfilePath ), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
				Files.walkFileTree(root, 
						new SimpleFileVisitor<Path>() {
							@Override
							public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
									throws IOException {
								// You can do anything you want with the path here
								Files.copy(file, toDirectory);
								return FileVisitResult.CONTINUE;
							}
							
							@Override
							public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) 
									throws IOException {
							/* In a full implementation, you'd need to create each 
							 sub-directory of the destination directory before copying files into it.*/
								return super.preVisitDirectory(dir, attrs);
							}
						});
			}
		} else {
			exratctFileList(zipfilePath, outdir);
		}
	}
}