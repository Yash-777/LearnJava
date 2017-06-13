package com.github.amazoneS3;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author yashwanth.m
 *
 */
public class EncryptString {

	public static void main(String[] args) {
		System.out.println("MD5 : "+ cryptWithMD5("yash") );// c296539f3286a899d8b3f6632fd62274
		
		System.out.println("Crypto : "+ encryptDecryptText("Yash", "ENCRYPT_MODE"));
		System.out.println("Crypto : "+ encryptDecryptText("D8nfRH8nlhQ=", "DECRYPT_MODE"));
		
		keyBaseEncrypt("Yash");
	}
	
	/**
	 * This method is to encrypt the password by using the MD5 algorithm
	 */
	public static String cryptWithMD5(String toEncrypt){
		String result=null;
		try {
			result = toEncrypt;
			if(toEncrypt != null) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(toEncrypt.getBytes());
				
				BigInteger hash = new BigInteger(1, md.digest());
				result = hash.toString(16);
				while(result.length() < 32) {
					result = "0" + result;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * cryptographic cipher for encryption and decryption. It forms the core of the 
	 * Java Cryptographic Extension (JCE) framework. 
	 * 
	 * http://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
	 * 
	 * http://www.developersnote.com/2013/11/encrypt-and-decrypt-example-in-java.html#.WS_69Ch97cc
	 */
	private static byte[] sharedvector = {
		0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
	};
	/**
	 * @param RawText
	 * @param mode
	 * @return
	 */
	public static String encryptDecryptText(String RawText, String mode) {
		byte[] keyArray = new byte[24];
		byte[] temporaryKey, toEncryptArray = null;
		
		String returnText = "";
		String USER_KEY = "github.com";
		
		try {
			
			MessageDigest m = MessageDigest.getInstance("MD5");
			temporaryKey = m.digest(USER_KEY.getBytes("UTF-8"));
		
			if(temporaryKey.length < 24) { // DESede require 24 byte length key
				int index = 0;
				for(int i=temporaryKey.length;i< 24;i++) {
					keyArray[i] =  temporaryKey[index];
				}
			}
		
			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyArray, "DESede");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(sharedvector);
			// PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(sharedvector, 20);
			
			if( mode.equals("ENCRYPT_MODE") ) {
				//cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
				cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
				//cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, pbeParameterSpec);
				toEncryptArray =  RawText.getBytes("UTF-8");
				byte[] encrypted = cipher.doFinal(toEncryptArray);
				
				returnText = org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
			} else if( mode.equals("DECRYPT_MODE") ) {
				//cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
				cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
				//cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, pbeParameterSpec);
				byte[] decrypted = cipher.doFinal(Base64.decodeBase64(RawText));
				
				returnText = new String(decrypted, "UTF-8");
			}
	
		} catch (BadPaddingException e) {
			System.out.println("Encrypted Key is not generated based on USER_KEY");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return returnText;
	}
	
	/**
	 * 128 bit key encryption
	 * 
	 * <P>java.security.NoSuchAlgorithmException: Cannot find any provider supporting GIT</P>
	 * <P>http://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html</P>
	 * java.security.InvalidKeyException: Wrong algorithm: AES or Rijndael required
	 * 
	 * <p> Text: Yash « É™Z!Z,NÀÌ(]†lãJ
	 * @param text
	 * @return
	 */
	public static String keyBaseEncrypt( String text ) {
		try {
			String key = "Bar12345Bar12345"; // 128 bit key
			String provider = "AES";
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), provider);
			Cipher cipher = Cipher.getInstance(provider);
			
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			// encrypt the text « É™Z!Z,NÀÌ(]†lãJ
			System.out.println("encrypted : "+ new String(encrypted));
			StringBuilder sb = new StringBuilder();
			for (byte b: encrypted) {
				sb.append((char)b);
			}
			// the encrypted String « ??Z!Z,N??(]?l?J
			System.out.println("encrypted:" + sb.toString());
			
			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			String decrypted = new String(cipher.doFinal(encrypted));
			System.out.println("decrypted:" + decrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
}
