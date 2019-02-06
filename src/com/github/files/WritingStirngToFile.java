package com.github.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;

/**
 * https://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
 * 
 * @author yashwanth.m
 *
 */
public class WritingStirngToFile {
	public static void main(String[] args) throws Exception {
		String targetFilePath = "D:/filename.txt";
		String text = "Yashwanth.M";
		writeLines(text, targetFilePath);
	}
	
	/**
	 * If the file exists then it will be truncated to zero size;otherwise, a new file will be created.
	 * The output will be written to the file and is buffered.
	 * 
	 * @param text  the content to write to the file
	 * @param targetFilePath  the file to write
	 * @throws FileNotFoundException 
	 */
	public static void writeLines(String text, String targetFilePath) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter( targetFilePath )) {
			out.println(text);
		}
	}
	
	/**
	 * The String will be added to the end of the file rather than overwriting.
	 */
	public static void appending_AppacheCommons(String text, String targetFilePath) throws IOException {
		FileUtils.writeStringToFile( new File( targetFilePath ), text, Charset.defaultCharset(), true);
	}
	
	/**
	 * Writes a String to a file creating the file if it does not exist.
	 */
	public static void overwriting_AapacheCommons(String text, String targetFilePath) throws IOException {
		FileUtils.writeStringToFile( new File( targetFilePath ), text, Charset.defaultCharset(), false);
	}
	
	public static void writeToFile_Java7(String text, String targetFilePath) throws IOException {
		Path targetPath = Paths.get(targetFilePath);
		byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
		Files.write(targetPath, bytes, StandardOpenOption.CREATE);
	}
}