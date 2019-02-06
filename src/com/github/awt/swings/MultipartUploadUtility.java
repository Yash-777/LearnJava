package com.github.awt.swings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provides an abstraction layer for sending Multipart HTTP
 * POST requests to a web server.
 * 
 * @author yashwanth.m
 * 
 */
public class MultipartUploadUtility {
	public String boundary, charset;
	private static final String LINE_FEED = "\r\n";
	private HttpURLConnection httpConn;
	public OutputStream outputStream;
	private PrintWriter writer;

	/**
	 * This constructor initializes a new HTTP POST request with content type is set to multipart/form-data.
	 */
	public MultipartUploadUtility( String requestURL, String charset ) throws IOException {
		this.charset = charset;
		// creates a unique boundary based on time stamp
		boundary = System.currentTimeMillis() + "";
		
		// https://stackoverflow.com/questions/24637038/jersey-2-multipart-upload-client
		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoOutput(true); // indicates POST method
		httpConn.setDoInput(true);
		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		outputStream = httpConn.getOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
	}

	/**
	 * Add a upload file section to the request
	 */
	public void addFilePart(String fieldName, File uploadFile, boolean useProgressBarPercentage)
			throws IOException {
		String fileName = uploadFile.getName();
		writer.append( boundary ).append(LINE_FEED);
		writer.append("Content-Disposition: form-data; "
				+ "name=\"" + fieldName + "\"; filename=\"" + fileName + "\"");
		writer.append(LINE_FEED);
		writer.append("Content-Type: "+ URLConnection.guessContentTypeFromName(fileName));
		writer.append(LINE_FEED);
		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
		writer.flush();
		
		FileInputStream inputStream = new FileInputStream(uploadFile);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		if( useProgressBarPercentage ) {
			// Upload takes place in UploadTask class.
		} else {
			while ( (bytesRead = inputStream.read(buffer)) != -1 ) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();
			
			writer.append(LINE_FEED);
			writer.flush();
		}
		
	}
	
	/**
	 * Completes the request and receives response from the server.
	 * @return a list of Strings as response in case the server returned
	 * status OK, otherwise an exception is thrown.
	 * @throws IOException
	 */
	public List<String> finish() throws IOException {
		List<String> response = new ArrayList<String>();
		outputStream.flush();
		writer.append(LINE_FEED).flush();
		writer.append( boundary ).append(LINE_FEED);
		writer.close();
		
		// checks server's status code first
		int status = httpConn.getResponseCode();
		if (status == HttpURLConnection.HTTP_OK) {
			InputStreamReader inputStreamReader = new InputStreamReader(httpConn.getInputStream());
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.add(line);
			}
			reader.close();
			httpConn.disconnect();
		} else {
			// if any network error occurred or the server returns non-HTTP_OK status code.
			throw new IOException("Server returned non-OK status: " + status);
		}
		
		return response;
	}
	
	public static void main(String[] args) {
		String charset = "UTF-8";
		File uploadFile1 = new File("D:/PIC1.PNG");
		File uploadFile2 = new File("D:/PIC2.PNG");
		String requestURL = "http://localhost:8080/SteamingServlet/files/UploadFile";
		
		try {
			MultipartUploadUtility multipart = new MultipartUploadUtility(requestURL, charset);
			
			multipart.writer.append("User-Agent : CodeJava").append(LINE_FEED);
			multipart.writer.append("Test-Header : Header-Value").append(LINE_FEED);
			multipart.writer.append( multipart.boundary).append(LINE_FEED);
			multipart.writer.append("Content-Disposition: form-data; name=myFilesDiscription\r\n");
			multipart.writer.append("Content-Type: text/plain; charset=" + charset);
			multipart.writer.append(LINE_FEED);
			multipart.writer.append("myFilesDiscriptionValue").append(LINE_FEED);
			multipart.writer.flush();
			
			multipart.addFilePart("fileUpload", uploadFile1, false);
			multipart.addFilePart("fileUpload", uploadFile2, false);
			
			List<String> response = multipart.finish();
			System.out.println("SERVER REPLIED RESPONSE : ");
			
			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}