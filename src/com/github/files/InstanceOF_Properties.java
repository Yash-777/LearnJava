package com.github.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InstanceOF_Properties {
	static Properties props= new Properties();
	public static void main(String[] args) {
		String propertiesFilename = "keys_ReferedKeys.properties";
		
		ClassLoader classLoader = InstanceOF_Properties.class.getClassLoader();
		InputStream resourceAsStream = classLoader.getResourceAsStream( propertiesFilename );
		
		try {
			props.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// intProp : 777, stringProp : Yash, longProp : 77.7
		instanceOfCheck("intProp");
		instanceOfCheck("stringProp");
		instanceOfCheck("longProp");
	}
	
	static void instanceOfCheck( String key ) {
		Object value = props.get( key );
		
		if( value instanceof Long ) {
			System.out.format("\n Long « key:value [%s:%s]", key, value);
		} else if( value instanceof Integer ) {
			System.out.format("\n Integer « key:value [%s:%s]", key, value);
		} else if( value instanceof String ) {
			System.out.format("\n String « key:value [%s:%s]", key, value);
		} else {
			System.out.format("\n key:value [%s:%s]", key, value);
		}
	}
}
