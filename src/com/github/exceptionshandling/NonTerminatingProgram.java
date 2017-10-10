package com.github.exceptionshandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.http.conn.ClientConnectionManager;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.github.cloud.storage.S3Objects;

/**
 * https://stackoverflow.com/a/18608092/5081877
 * 
 * org.openqa.selenium.NoSuchWindowException
 *  https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/6511#issuecomment-192149573
 * 
 * java.lang.NoSuchMethodError
 *  Thread.currentThread().destroy();
 *  Normally, this error is caught by the compiler; this error can only occur at run time
 *  if the definition of a class has incompatibly changed.
 * 
 * com.amazonaws.services.s3.model.AmazonS3Exception
 * @author yashwanth.m
 *
 */
@SuppressWarnings("deprecation")
public class NonTerminatingProgram {
	@SuppressWarnings({ "unused" })
	private static final List<ClientConnectionManager> connectionManagers = null;
	static Properties props = new Properties();
	static InputStream resourceAsStream;
	static {
		ClassLoader classLoader = new S3Objects().getClass().getClassLoader();
		resourceAsStream = classLoader.getResourceAsStream("aws.properties");
		try {
			props.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			new NonTerminatingProgram().s3RuntimeException();
			System.out.println("---");
			//Thread.setDefaultUncaughtExceptionHandler(null);
			//Thread.currentThread().setDaemon(true);
			System.out.println("===");
		} catch (Exception e) {
			System.out.println("EX: "+e.getMessage());
		}
		//fileCheckedException();
	}
	
	static void fileCheckedException() {
		FileReader file;
		try {
			file = new FileReader("C:\\test\\a.txt");
			BufferedReader fileInput = new BufferedReader(file);
			
			// Print first 3 lines of file "C:\test\a.txt"
			for (int counter = 0; counter < 3; counter++) 
				System.out.println(fileInput.readLine());
			
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void s3RuntimeException() {
		// https://github.com/aws/aws-sdk-java/issues/994#issuecomment-275048011
		int connectionTimeout = 1 * 1000, readTimeout = 55 * 1000;
		ClientConfiguration config = new ClientConfiguration();
		config.setConnectionTimeout(connectionTimeout);
		config.setSocketTimeout(readTimeout); 
		config.setRetryPolicy(PredefinedRetryPolicies.getDefaultRetryPolicyWithCustomMaxRetries(5));
		
		AWSCredentials awsCreds = new 
				BasicAWSCredentials(props.getProperty("accessKey"), props.getProperty("secretKey"));
		AmazonS3 s3Client = new AmazonS3Client( awsCreds );
				//new AmazonS3Client( awsCreds, config );
		
		String s3_BucketName = props.getProperty("bucketname");
		String folderPath_fileName = props.getProperty("path");
		System.out.println("File Path : "+folderPath_fileName);
		try {
			
			com.github.cloud.storage.S3Objects.uploadObject(s3Client, s3_BucketName, folderPath_fileName);
		} catch(AmazonS3Exception auth) {
			System.out.println("Authentication details erroer : "+auth.toString());
			if( auth.getErrorCode().equalsIgnoreCase("SignatureDoesNotMatch") || auth.getStatusCode() == 403 ) {
				System.out.println("Thread to Interupt.");
				//System.exit(1); // Finally block will not execute.
				//throw new RuntimeException("To terminate java program.");
				Thread.currentThread().interrupt();
				boolean interrupted = Thread.interrupted();
				if ( interrupted ) {
					System.out.println("Thread.interrupted()");
					//Thread.currentThread().destroy();// java.lang.NoSuchMethodError
					boolean holdsLock = Thread.holdsLock( Thread.currentThread() );
					System.out.println("holdsLock : "+holdsLock);
				} else {
					System.out.println("! « Thread.interrupted()");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}