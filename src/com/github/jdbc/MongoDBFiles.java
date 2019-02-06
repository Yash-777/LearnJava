package com.github.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * MongoDBFiles class used Grid FS to store files.
 * <UL><B>Grid FS</B> stores files in two collections:
 * <LI>bucketName.chunks (GridFS divides the file into chunks that are stored as 
 * distinct documents in a chunks collection.)</LI>
 * <LI>bucketName.files GridFS stores information about stored files in file collection.
 * There will be one files collection document per stored file.</LI>
 * </UL>
 * 
 * <p> https://stackoverflow.com/a/33544285/5081877 </p>
 * 
 * http://mongodb.github.io/mongo-java-driver/
 * @author yashwanth.m
 *
 */
public class MongoDBFiles {
	
	static Properties props = new Properties();
	
	static Mongo mongoClient = null;
	static DB db = null;
	static boolean auth = false;
	
	static String mongoDBHost, mongoDBName, mongoDBUserName, mongoDBPassword, mongoDB_BucketName;
	static Integer mongoDBPort;
	
	static {
		ClassLoader classLoader = MongoDBFiles.class.getClassLoader();
		InputStream resourceAsStream = classLoader.getResourceAsStream("mongo.properties");
		try {
			props.load(resourceAsStream);
			mongoDBHost = props.getProperty("mongoDBHost");
			mongoDBPort = Integer.valueOf( props.getProperty("mongoDBPort") );
			mongoDBName = props.getProperty("mongoDBName");
			mongoDBUserName = props.getProperty("mongoDBUserName");
			mongoDBPassword = props.getProperty("mongoDBPassword");
			mongoDB_BucketName = props.getProperty("mongoDB_BucketName");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void getConnection() {
		try {
			mongoClient = new Mongo(mongoDBHost, mongoDBPort);
			db = mongoClient.getDB( mongoDBName );
			auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Static MongoDB Authentication Status « "+auth);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static void closeConnection() {
		try {
			mongoClient.close();
			auth = false;
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
	}
	public static void main(String[] args) {
		String fileName = "777";
		String objectID = upload( "E:\\Nexus.mp4", "First");
		//upload("E:\\IMediaWriterVedio7.mp4", "First", "");
		download( "D:\\IMediaWriterVedio"+fileName+".mp4", objectID);
	}

	public static String upload(String uploadFileLocation, String fileName) {
		String objectID = "";
		try {
			// http://mongodb.github.io/mongo-java-driver/3.0/driver/reference/connecting/authenticating/#ldap-plain
			getConnection();
			if ( auth ) {
				//Create instance of GridFS implementation
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				GridFSInputFile gridFsInputFile = gridFs.createFile(new File(uploadFileLocation));
				gridFsInputFile.setId( "777" );
				gridFsInputFile.setFilename(fileName); //Set a name on GridFS entry
				
				DBObject metadata = new BasicDBObject();
				metadata.put("uploadedBy", "Yashwanth");
				metadata.put("description", "File with some unique data, To avoid problem in retreiving.");
				
				gridFsInputFile.setMetaData(metadata);
				
				gridFsInputFile.save(); //Save the file to MongoDB
				Object id = gridFsInputFile.getId();
				objectID = id.toString();
				System.out.println("Uploaded Successfully. - "+ objectID);
			}
			//download( "E:\\IMediaWriterVedio"+fileName+".mp4", fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return objectID;
	}
	
	public static void download(String downloadFileLocation, String fileID) {
		try {
			getConnection();
			if( auth) {
				BasicDBObject query = new BasicDBObject();
				//query.put("_id", new ObjectId( fileID ));
				query.put("_id", fileID );
				
				//Create instance of GridFS implementation
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				GridFSDBFile outputImageFile = gridFs.findOne( query );
				outputImageFile.writeTo(new File( downloadFileLocation ));
				System.out.println("Downloaded Successfully.");
				
				/*
				BasicDBObject query = new BasicDBObject();
				query.put("_id", new ObjectId( fileID ));
				GridFSDBFile outputImageFile = gridFs.findOne( query );
				inputStream = outputImageFile.getInputStream();*/
				
				/*String downloadFileLocation	= "D:\\noSQL_Image.png";
				outputImageFile.writeTo(new File( downloadFileLocation ));
				System.out.println("Downloaded Successfully.");
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				outputImageFile.writeTo( baos );
				byte[] imageData = baos.toByteArray();*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	@SuppressWarnings("deprecation")
	public static void upload(String uploadFileLocation, String fileName, String driverSessionId) {
		Mongo mongoClient = null;
		try {
			mongoClient = new MongoClient(mongoDBHost, mongoDBPort.intValue());
			DB db = mongoClient.getDB(mongoDBName);
			boolean auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Mongo DB Authentication >>> " + auth);
			if (auth) {
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				GridFSInputFile gridFsInputFile = gridFs.createFile(new File(uploadFileLocation));
				gridFsInputFile.setId(driverSessionId);
				gridFsInputFile.setFilename(fileName);
				gridFsInputFile.save();
				System.out.println("Uploaded Successfully.");
			}
			
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (mongoClient != null)
				mongoClient.close();
			new File(uploadFileLocation).delete();
		}
	}
}