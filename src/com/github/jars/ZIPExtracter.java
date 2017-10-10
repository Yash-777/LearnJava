package com.github.jars;

import java.io.BufferedOutputStream;
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
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.github.os.Platform;

/**
 * ZIPExtracter class is to extract the contents of a ZIP file.
 * 
 * JavaScript library to zip and unzip files http://gildas-lormeau.github.com/zip.js/
 * 
 * @author yashwanth.m
 *
 */
public class ZIPExtracter extends Platform {

	// create a buffer with large size which improve performance to read/write data.
	final int BUFFER_SIZE = 4096;// 2048
	
	public static void main(String[] args) {
		String chromeZIP = "D:\\testFile.zip";
		String outdir = "D:\\JarFiles";
		outdir.replaceAll("\\\\", "/");
		try {
			//new ZIPExtracter().extractZIP(chromeZIP, outdir, false);
			new ZIPExtracter().extractAll(chromeZIP, outdir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath	the ZIP file location.
	 * @param destDirectory		the location to extract the file. 
	 * @return 
	 */
	@SuppressWarnings("resource")
	public void extractZIP( String zipFilePath, String destDirectory, boolean isFileStream ) throws IOException {
		
		checkDirectory(destDirectory);

		if( isFileStream ) {
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry entry = zipIn.getNextEntry();
			// iterates over entries in the zip file
			while (entry != null) {
				String filePath = destDirectory + File.separator + entry.getName();
				System.out.println("Entrey « "+entry.getName());
				if (!entry.isDirectory()) { // if the entry is a file, extracts it
					extractFile(zipIn, filePath);
				} else { // if the entry is a directory, make the directory
					checkDirectory(filePath);
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
			
		} else {
			ZipFile zipFile = new ZipFile( zipFilePath );
			System.out.println("ZipFile Location : "+ zipFile.getName());
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				System.out.println("Entrey « "+entry.getName());
				InputStream stream = zipFile.getInputStream( entry );
				FileOutputStream output = null;
				
				try {
					String filePath = destDirectory + File.separator + entry.getName();
					int len = 0;
					if ( !entry.isDirectory() ) {// if the entry is a file, extracts it
						byte[] buffer = new byte[BUFFER_SIZE];
						output = new FileOutputStream(filePath);
						while ((len = stream.read(buffer)) > 0)
							output.write(buffer, 0, len);
					} else {// if the entry is a directory, make the directory
						checkDirectory(filePath);
					}
				} finally {
					if(output!=null) output.close();
					if(stream!=null) stream.close();
				}
			}
		}
	}

	/**
	 * « http://fahdshariff.blogspot.in/2011/08/java-7-working-with-zip-files.html
	 * 
	 * A file tree is walked depth first, but you cannot make any assumptions about the iteration order
	 * that sub-directories are visited. As for the order in which the files are read, 
	 * it depends (in the current implementation) on the supplied DirectoryStream
	 * 
	 * https://en.wikipedia.org/wiki/File_URI_scheme
	 * https://docs.oracle.com/javase/tutorial/essential/io/walk.html
	 * http://www.concretepage.com/java/jdk7/traverse-directory-structure-using-files-walkfiletree-java-nio2
	 * https://stackoverflow.com/a/31696833/5081877
	 */
	public void extractAll(String zipFilePath, String destDirectory) throws IOException{
		
		checkDirectory(destDirectory);
		int javaVersion = new Platform().getJavaVersion();
		
		if ( javaVersion > 6 ) {
			
			final Path destination = Paths.get( destDirectory );
			
			try (FileSystem zipFileSystem = createZipFileSystem(zipFilePath)) {
				final Path rootPath = zipFileSystem.getPath("/");
				////walk the zip file tree and copy files to the destination
				Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
					
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					
						Path targetPath = destination.resolve(rootPath.relativize(dir).toString());
						System.out.println("Entrey Folder « "+targetPath);
						if (Files.notExists(targetPath)) {
							Files.createDirectory(targetPath);
						}
						return FileVisitResult.CONTINUE;
					}
					
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Path targetPath = destination.resolve(rootPath.relativize(file).toString());
						System.out.println("Entrey File « "+targetPath);
						Files.copy(file, targetPath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
						return FileVisitResult.CONTINUE;
					}
				});
			}
		} else {
			extractZIP(zipFilePath, destDirectory, true);
		}
	}
	
	void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
		int read = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((read = zipIn.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
		bos.close();
	}
	
	void checkDirectory( String destDirectory) {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
	}
	FileSystem createZipFileSystem(String zipFilename) throws IOException {
		// convert the filename to a URI
		final Path path = Paths.get(zipFilename);
		final URI uri = URI.create("jar:file:" + path.toUri().getPath());

		final Map<String, String> zipProperties = new HashMap<>();
		/* We want to read an existing ZIP File, so we set this to False */
		zipProperties.put("create", "false");
		zipProperties.put("encoding", "UTF-8");
		
		return FileSystems.newFileSystem(uri, zipProperties);
	}
}