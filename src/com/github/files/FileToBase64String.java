package com.github.files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;

public class FileToBase64String {
	public String getBase64String( String fileLocation ) {
		try {
			File file = new File( fileLocation );
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			byte[] byteArray = new byte[(int) file.length()];
			
			try {
				dis.readFully(byteArray); // now the array contains the image
			} catch (Exception e) {
				byteArray = null;
			} finally {
				dis.close();
			} // org.apache.commons.codec.binary.
			String encodedfile = new String(Base64.encodeBase64( byteArray ), "UTF-8");
			return encodedfile;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}