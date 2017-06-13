package com.github.amazoneS3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
/**
 * When my program is requesting for a file which is not available in S3_Bucket which exists
 * and the requested file/object key is not available in S3.
 * Here i am getting an pre-signed url to access that file which is not available in S3.
 * Here is their any method to get url as null [when the requested object is not available in S3]
 * 
 * @author yashwanth.m
 *
 */
public class S3Objects {
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

	public static void main(String[] args) throws IOException, AmazonServiceException, AmazonClientException, InterruptedException {
		AWSCredentials awsCreds = new 
						BasicAWSCredentials(props.getProperty("accessKey"), props.getProperty("secretKey"));
						// PropertiesCredentials(resourceAsStream);
		AmazonS3 s3Client = new AmazonS3Client( awsCreds );

		String s3_BucketName = props.getProperty("bucketname");
		String folderPath_fileName = props.getProperty("path");
		System.out.println("File Path : "+folderPath_fileName);
		uploadObject(s3Client, s3_BucketName, folderPath_fileName);
		//downloadObject(s3Client, s3_BucketName, folderPath_fileName);
		//getSignedURLforS3File(s3Client, s3_BucketName, folderPath_fileName);
		/*String url = getPreSignedURLAsString_Object(s3Client, s3_BucketName, folderPath_fileName);
		System.out.println("Received response:"+url);*/
		
		/*String url = getPreSignedURLAsString_GetMetaData(s3Client, s3_BucketName, folderPath_fileName);
		System.out.println("Received response:"+url);*/
		
		/*String url = getPreSignedURLAsString_DoesKeyExists(s3Client, s3_BucketName, folderPath_fileName);
		System.out.println("Received response:"+url);*/
	}
	
	public static String getPreSignedURLAsString_DoesKeyExists(
			AmazonS3 s3Client, String s3_BucketName, String folderPath_fileName) {
		try {
			boolean doesObjectExist = s3Client.doesObjectExist(s3_BucketName, folderPath_fileName);
			if (doesObjectExist) {
				System.out.println("Key Exists in S3");
				return getSignedURLforS3File(s3Client, s3_BucketName, folderPath_fileName);
			}
		} catch(AmazonS3Exception s3) {
			System.out.println("Received error response:"+s3.getMessage());
		}
		return "Key Not Found.";
	}
	
	public static String getPreSignedURLAsString_GetObject(AmazonS3 s3Client,
			String s3_BucketName, String folderPath_fileName) {
		GetObjectRequest request = new GetObjectRequest(s3_BucketName,folderPath_fileName);
		try {
			S3Object s3object = s3Client.getObject( request );
			System.out.println("Content-Type  : " + s3object.getObjectMetadata().getContentType());
			System.out.println("Content-Length: " + s3object.getObjectMetadata().getContentLength());
			return getSignedURLforS3File(s3Client, s3_BucketName, folderPath_fileName);
		} catch(AmazonS3Exception s3) {
			System.out.println("Received error response:"+s3.getMessage());
		}
		return "The specified key does not exist.";
	}

	public static String getPreSignedURLAsString_GetMetaData(AmazonS3 s3Client,
			String s3_BucketName, String folderPath_fileName) {
		GetObjectMetadataRequest request = new GetObjectMetadataRequest(s3_BucketName,folderPath_fileName);
		try {
			@SuppressWarnings("unused")
			ObjectMetadata objectMetadata = s3Client.getObjectMetadata( request );
			System.out.println("Key Exists in S3");
			return getSignedURLforS3File(s3Client, s3_BucketName, folderPath_fileName);
		} catch(AmazonS3Exception s3) {
			System.out.println("Received error response:"+s3.getMessage());
		}
		return "Key Not Found.";
	}
	
	//	<MaxKeys>1000</MaxKeys>
	public static String getSingnedURLKey(AmazonS3 s3Client, String s3_BucketName, String folderPath_fileName) {
		String folderPath = folderPath_fileName.substring(0,folderPath_fileName.lastIndexOf("/"));		
		ObjectListing folderPath_Objects = s3Client.listObjects(s3_BucketName, folderPath);
		
		List<S3ObjectSummary> listObjects = folderPath_Objects.getObjectSummaries();
		for(S3ObjectSummary object : listObjects) {
			if(object.getKey().equalsIgnoreCase(folderPath_fileName)) {
				return getSignedURLforS3File(s3Client, s3_BucketName, folderPath_fileName);
			}
		}
		return "The specified key does not exist.";
	}

	//	providing pre-signed URL to access an object w/o any AWS security credentials.
	//	Pre-Signed URL = s3_BucketName.s3.amazonaws.com/folderPath_fileName?AWSAccessKeyId=XX&Expires=XX&Signature=XX
	public static String getSignedURLforS3File(AmazonS3 s3Client, String s3_BucketName, String folderPath_fileName){
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(s3_BucketName, folderPath_fileName, HttpMethod.GET);
		request.setExpiration( new Date(System.currentTimeMillis() + 1000 * 60 * 15) ); // Default 15 min
		
		String url = s3Client.generatePresignedUrl( request ).toString();
		System.out.println("Pre-Signed URL = " + url);
		return url;
	}
	
	public static void uploadObject(AmazonS3 s3Client, String s3_BucketName, String folderPath_fileName) 
			throws AmazonServiceException, AmazonClientException, InterruptedException{
		TransferManager tm = new TransferManager(s3Client);
		
		PutObjectRequest putObjectRequest = 
				new PutObjectRequest(s3_BucketName, folderPath_fileName, new File( props.getProperty("filePath") ));
		Upload myUpload = tm.upload( putObjectRequest );
		myUpload.waitForCompletion();//block the current thread and wait for your transfer to complete.
		tm.shutdownNow();			 //to release the resources once the transfer is complete.
	}
	
	//	When accessing a key which is not available in S3, it throws an exception The specified key does not exist.
	public static void downloadObject(AmazonS3 s3Client, String s3_BucketName, String folderPath_fileName) throws IOException{
		GetObjectRequest request = new GetObjectRequest(s3_BucketName,folderPath_fileName);
		try {
			S3Object s3object = s3Client.getObject( request );
			System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
			S3ObjectInputStream objectContent = s3object.getObjectContent();
			
			FileUtils.copyInputStreamToFile(objectContent, new File("targetFile2.jpg"));
		} catch(AmazonS3Exception s3) {
			System.out.println("Received error response:"+s3.getMessage());
		}
	}
}
