package com.github.cloud.storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class GenerateSymmetricMasterKey {
	private static final String keyDir  = System.getProperty("java.io.tmpdir"); 
	private static final String keyName = "secret.key";
	
	public static void main(String[] args) throws Exception {
		// [-57, -75, -89, -125, -25, -65, 72, 60, 25, -20, 68, 72, 47, 126, -83, 42, -43, 95, -77, 124, -122, -17, 86, -113, -45, 109, -24, -72, -10, 77, 8, -8]
		//Generate symmetric 256 bit AES key.
		/*KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
		// Wrong keysize: must be equal to 128, 192 or 256
		symKeyGenerator.init(192); 
		SecretKey symKey = symKeyGenerator.generateKey();
		byte[] encoded = symKey.getEncoded();
		secterKeyData(encoded);*/
		
		SecretKey retrieveEncryptionKey = retrieveEncryptionKey();
		byte[] encoded2 = retrieveEncryptionKey.getEncoded();
		
		secterKeyData( encoded2 );
		
		javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("HMACSHA1");
		generator.init(120);
		System.out.println("awsAccessKeyId");
		byte[] awsAccessKeyId = generator.generateKey().getEncoded();
		secterKeyData( awsAccessKeyId );
		generator.init(240);
		byte[] awsSecretAccessKey = generator.generateKey().getEncoded();
		System.out.println("awsSecretAccessKey");
		secterKeyData( awsSecretAccessKey );
		
		/*//Save key.
		saveSymmetricKey(keyDir, symKey);
		
		//Load key.
		SecretKey symKeyLoaded = loadSymmetricAESKey(keyDir, "AES");
		String algorithm = symKeyLoaded.getAlgorithm(); // AES
		System.out.println("Algorithm Key : "+symKeyLoaded.getEncoded().toString());*/
	}

	public static void saveSymmetricKey(String path, SecretKey secretKey) throws IOException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(secretKey.getEncoded());
		FileOutputStream keyfos = new FileOutputStream(path + "/" + keyName);
		keyfos.write(x509EncodedKeySpec.getEncoded());
		keyfos.close();
	}
	
	public static SecretKey loadSymmetricAESKey(String path, String algorithm) 
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException{
		//Read private key from file.
		File keyFile = new File(path + "/" + keyName);
		FileInputStream keyfis = new FileInputStream(keyFile);
		byte[] encodedPrivateKey = new byte[(int)keyFile.length()];
		keyfis.read(encodedPrivateKey);
		keyfis.close(); 
		System.out.println("KEY : "+encodedPrivateKey.toString());
		//Generate secret key.
		return new SecretKeySpec(encodedPrivateKey, "AES");
	}
	/**
	 * In the real world, this key will need to be persisted somewhere. For this demo we'll generate
	 * a new random one each time.
	 * 
	 * http://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/java-example-code.html
	 */
	private static SecretKey retrieveEncryptionKey() {
		SecureRandom random = new SecureRandom();
		/*byte[] rawKey = new byte[16]; // 128 bits
		random.nextBytes(rawKey);*/
		byte rawKey[] = random.generateSeed(20);
		secterKeyData( rawKey );
		return new SecretKeySpec(rawKey, "AES");
	}
	
	public static void secterKeyData( byte[] encoded ) {
		System.out.println("Algorithm Key : "+encoded.toString());
		
		//StringBuffer key = new StringBuffer();
		for (byte b : encoded) {
			System.out.println( (char)b );
		}
		System.out.println("==========");
	}
}
