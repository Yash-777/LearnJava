package com.github.files;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.text.StrLookup;
import org.apache.commons.text.StrSubstitutor;

/**
 * 
 * Property keys and values are simply Strings. No processing happens to them, so you can't refer to another value in a value.
 * By using commons-lang(3) to load interpolated properties
 * 
 * <p><a href="https://stackoverflow.com/a/27724276/5081877"></a></p>
 * 
 * @author yashwanth.m
 *
 */
public class Properties_With_ReferedKeys {
	public static void main(String[] args) {
		
		ClassLoader classLoader = Properties_With_ReferedKeys.class.getClassLoader();
		
		String propertiesFilename = "keys_ReferedKeys.properties";
		Properties props = getMappedProperties(classLoader, propertiesFilename);
		
		System.out.println( props.getProperty("jdk") );
		
	}
	
	
	public static Properties getMappedProperties( ClassLoader classLoader, String configFilename ) {
		Properties fileProperties = new Properties();
		
		try {
			InputStream resourceAsStream = classLoader.getResourceAsStream( configFilename );
			
			Map<String, String> loadPropertiesMap = loadPropertiesMap( resourceAsStream );
			Set<String> keySet = loadPropertiesMap.keySet();
			System.out.println("Provided 'Key':'Value' pairs are...");
			for (String key : keySet) {
				System.out.println( key + " : " + loadPropertiesMap.get(key) );
			}
			
			fileProperties.putAll( loadPropertiesMap );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return fileProperties;
	}
	public static Map<String,String> loadPropertiesMap( InputStream inputStream ) throws IOException {
		final Map<String, String> unResolvedProps = new LinkedHashMap<String, String>();
		
		/*Reads a property list (key and element pairs) from the input byte stream. 
		 * The input stream is in a simple line-oriented format.
		 */
		@SuppressWarnings("serial")
		Properties props = new Properties() {
			@Override
			public synchronized Object put(Object key, Object value) {
				unResolvedProps.put( (String)key, (String)value );
				return super.put( key, value );
			}
		};
		props.load( inputStream );
		
		final Map<String,String> resolvedProps = new LinkedHashMap<String, String>( unResolvedProps.size() );
		
		// Substitutes variables within a string by values.
		StrSubstitutor sub = new StrSubstitutor( new StrLookup<String>() {
			@Override
			public String lookup( String key ) {
				
				/*The value of the key is first searched in the configuration file,
				 * and if not found there, it is then searched in the system properties.*/
				String value = resolvedProps.get( key );
				
				if (value == null)
					return System.getProperty( key );
				
				return value;
			}
		} );
		
		for ( String key : unResolvedProps.keySet() ) {
			
			/*Replaces all the occurrences of variables with their matching values from the resolver using the given 
			 * source string as a template. By using the default ${} the corresponding value replaces the ${variableName} sequence.*/
			String value = sub.replace( unResolvedProps.get( key ) );
			resolvedProps.put( key, value );
		}
		return resolvedProps;
	}
}