package com.github.cloud.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Properties;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

/**
 * How to use Blob storage from Java
 * https://docs.microsoft.com/en-us/azure/storage/blobs/storage-java-how-to-use-blob-storage#UploadBlob
 * https://docs.microsoft.com/en-us/azure/storage/files/storage-java-how-to-use-file-storage
 * 
 * Azure Storage samples using Java
 * https://docs.microsoft.com/en-us/azure/storage/common/storage-samples-java
 * 
 * https://github.com/MicrosoftDocs/azure-docs
 * https://github.com/Azure-Samples/storage-file-java-getting-started
 * https://github.com/Azure/azure-storage-java
 * 
 * https://docs.microsoft.com/en-us/rest/api/storageservices/Put-Block?redirectedfrom=MSDN
 * 
 * https://stackoverflow.com/questions/24424543/azure-storage-block-blob-upload-from-android-example
 * https://stackoverflow.com/questions/27484477/how-to-upload-video-to-azure-media-services-from-aws-s3
 * 
 * @author yashwanth.m
 *
 */
public class Azure_BLOBStorage {
	
	static String storageConnectionString = null, containerName = null;
	
	static void checkContainerPermissions() {
	  try {
		Properties props = new Properties();
		props.load(Azure_BLOBStorage.class.getClassLoader().getResourceAsStream("azure.properties"));
		
		storageConnectionString = "DefaultEndpointsProtocol="+props.getProperty("DefaultEndpointsProtocol")+
								"; AccountName="+props.getProperty("AccountName")+
								"; AccountKey="+props.getProperty("AccountKey");
		
		containerName = props.getProperty("containerName");
		System.out.format("ConnectionString:[%s]\n ContainerName:[%s]\n ", storageConnectionString, containerName);
		// Retrieve storage account from connection-string.
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		// Create the blob client.
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		
		// Get a reference to a container. The container name must be lower case
		CloudBlobContainer container = blobClient.getContainerReference(containerName);
		
		// Create a permissions object.
		BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
		// Include public access in the permissions object.
		containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
		
		// Create the container if it does not exist.
		boolean flag = container.createIfNotExists();
		if( flag ) {
			 System.out.format("Successfully created the container \"%s\".", container.getName());
		} else {
			System.out.println(containerName+" : Container is already there , so no need to create...");
		}
		
		// Set the permissions on the container.
		container.uploadPermissions(containerPermissions);
		System.out.println("permission updated to Container...");
	  } catch (IOException | StorageException | URISyntaxException | InvalidKeyException e) {
		e.printStackTrace();
	  }
	}
	
	static CloudStorageAccount getStorageAccount() {
		try {
			if( storageConnectionString == null )
				checkContainerPermissions();
			return CloudStorageAccount.parse(storageConnectionString);
		} catch (InvalidKeyException | URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CloudBlobContainer getBolbContainer() {
		
		try {
			CloudStorageAccount storageAccount = getStorageAccount();
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			return container;
		} catch ( StorageException | URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void dleteBlobContainer() {
		try {
			CloudBlobContainer container = getBolbContainer();
			// Delete the blob container.
			container.deleteIfExists();
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadBlob( String fileLocation ) {
		try {
			File file = new File( fileLocation);
			CloudBlobContainer container = getBolbContainer();
			CloudBlockBlob blob = container.getBlockBlobReference( fileLocation );
			blob.upload(new FileInputStream( file ), file.length());
			String url = blob.getUri().toString();
			System.out.println("Uploaded FILE URL : "+url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listContainerBlobs() {
		CloudBlobContainer container = getBolbContainer();
		// Loop over blobs within the container and output the URI to each of them.
		for (ListBlobItem blobItem : container.listBlobs()) {
			System.out.println(blobItem.getUri());
		}
	}
	
	public void downloadBlob( String blobName ) {
		CloudBlobContainer container = getBolbContainer();
		// Loop through each blob item in the container.
		for (ListBlobItem blobItem : container.listBlobs()) {
			// If the item is a blob, not a virtual directory.
			if (blobItem instanceof CloudBlob) {
				// Download the item and save it to a file with the same name.
				CloudBlob blob = (CloudBlob) blobItem;
				try {
					if( blobName.equalsIgnoreCase( blob.getName() ))
						blob.download( new FileOutputStream( "C:\\mydownloads\\" + blob.getName() ));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (StorageException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void deleteBlob() {
		try {
			CloudBlobContainer container = getBolbContainer();
			// Retrieve reference to a blob named "myimage.jpg".
			CloudBlockBlob blob = container.getBlockBlobReference("myimage.jpg");
			// Delete the blob.
			blob.deleteIfExists();
		} catch (URISyntaxException | StorageException e) {
			e.printStackTrace();
		}
	}
}