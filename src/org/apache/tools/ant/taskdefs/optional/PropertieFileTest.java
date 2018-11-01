package org.apache.tools.ant.taskdefs.optional;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertieFileTest {
	public static void main(String[] args) {
		
	try {
		Properties properties = new Properties();
		properties.put("jdbc.driver", "com.mysql.jdbc.Driver");
		properties.put("jdbc.url", "jdbc:mysql://localhost:3306/technicalkeeda");
		properties.put("jdbc.username", "root");
		properties.put("jdbc.password", "passw#ord!");
		
		properties.store(System.out, "Writing properties to System.out stream");

		/*FileOutputStream fos = new FileOutputStream("e://config.properties");
		properties.store(fos, "Writing properties to a file");
		fos.close();*/
		
		List<String> result = new LinkedList<>();
		Set<String> keySet = properties.stringPropertyNames();
		for (String key : keySet) {
			result.add( key +" = "+ properties.getProperty( key ) );
		}
		//https://stackoverflow.com/a/5619144/5081877
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : result) {
			// out.writeUTF(element+"\n");
			out.writeBytes( element+"\n" );
		}
		
		
		OutputStream os = new FileOutputStream("e://config.properties");
		try {
			os.write(baos.toByteArray());
		} finally {
			os.close();
		}
		
		/*String result = new BufferedReader(new InputStreamReader(new FileInputStream(file))).lines()
				   .parallel().collect(Collectors.joining(" [] "));
		System.out.println("File Data « : " + result);*/
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
}