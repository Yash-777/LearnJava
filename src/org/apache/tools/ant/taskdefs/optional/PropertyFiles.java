package org.apache.tools.ant.taskdefs.optional;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.apache.tools.ant.BuildException;

/*
The basic procedure for creating a custom task includes:

Creating a Java class that subclasses org.apache.tools.ant.Task and overrides the execute() method.
Packaging your class file(s) into a jar file.
In a standard Ant build.xml file, registering your custom task by using the standard taskdef task.
 */
public class PropertyFiles extends org.apache.tools.ant.Task /* 1 */ {
	
	/*// https://ant.apache.org/manual/develop.html
	<target name="propertiesToken">
		<taskdef name = "propertyfiles" classname = "org.apache.tools.ant.taskdefs.optional.PropertyFiles" />
		
		<propertyfiles propertyfile="${developmentProps}" comment="My Data" decoration=true> 
		  <entry key="DB.driverClassName" value = "${@driverClassName}"/>
		  <entry key="DB.url"             value = "${@url}"            />
		  <entry key="DB.username"        value = "${@username}"       />
		  <entry key="DB.password"        value = "${@password}"       defaultValue=""/>
		</propertyfiles>
	</target>
	 */
	private File propertyfile;
	public File getPropertyfile() {
		return propertyfile;
	}
	public void setPropertyfile(File propertyfile) {
		this.propertyfile = propertyfile;
	}
	
	private boolean decoration = false;
	public boolean getDecoration() {
		return decoration;
	}
	public void setDecoration(boolean decoration) {
		this.decoration = decoration;
	}
	
	private Vector<Entry> entries;
	public PropertyFiles() {
		this.entries = new Vector<Entry>();
	}
	public Entry createEntry() {
		Entry e = new Entry();
		this.entries.addElement(e);
		return e;
	}
	
	private String comment;
	public void setComment(String hdr) {
		this.comment = hdr;
	}
	
	@Override
	public void execute() throws BuildException {
		/*log("Property Files! ", Project.MSG_INFO);
		
		// https://ant.apache.org/manual/tutorial-writing-tasks.html
		// use of the reference to Project-instance
		String message = getProject().getProperty("ant.project.name");
		// Task's log method
		log("Here is project '" + message + "'.");
		// where this task is used?
		log("I am used in: " +  getLocation() );*/
		
		checkParameters();
		readFile();
		executeOperation();
		writeFile();
	}
	
	private void checkParameters() throws BuildException {
		if (!checkParam(this.propertyfile)) {
			throw new BuildException("file token must not be null.", getLocation());
		}
	}
	private boolean checkParam(File param) {
		return param != null;
	}
	
	private Properties properties;
	private void readFile() throws BuildException {
		this.properties = new Properties();

		try {
			if (this.propertyfile.exists()) {
				System.out.println("Updating property file « : " + this.propertyfile.getAbsolutePath());
				
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(this.propertyfile);
					BufferedInputStream bis = new BufferedInputStream(fis);
					
					/*@SuppressWarnings("resource")
					String result = new BufferedReader(new InputStreamReader(new FileInputStream(this.propertyfile)))
							.lines()
							.parallel().collect(Collectors.joining(" [] "));
					System.out.println("File Data « : " + result);*/
					
					this.properties.load(bis);
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			} else {
				throw new BuildException("File not available : ", getLocation());
			}
		} catch (IOException ioe) {
			throw new BuildException(ioe.toString());
		}
	}
	private void writeFile() throws BuildException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// case '=', ':', '#', '!' : // Fall through, escaped
			
			// store the properties list in an output stream « prop.store(System.out, "PropertiesDemo");
			//this.properties.store(baos, this.comment);
			propertiesToStream( baos );
			
			OutputStream os = new FileOutputStream(this.propertyfile);
			try {
				try {
					os.write(baos.toByteArray());
				} finally {
					os.close();
				}
			} catch (IOException x) {
				/*FileUtils.getFileUtils().tryHardToDelete(this.propertyfile);
				throw x;*/
			}
		} catch (IOException x) {
			throw new BuildException(x, getLocation());
		}
	}
	private String lineSeparator;
	public void propertiesToStream( ByteArrayOutputStream baos ) {
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		lineSeparator = java.security.AccessController.doPrivileged(
				new sun.security.action.GetPropertyAction("line.separator"));
		//System.out.println("LineSeparator : "+ lineSeparator);
		
		List<String> result = new LinkedList<>();
		result.add( "#" + new Date().toString() + lineSeparator );
		result.add( "#" + this.comment          + lineSeparator );
		
		Set<String> keySet = this.properties.stringPropertyNames();
		
		
		
		int lastEelement = 0;
		for (String key : keySet) {
			lastEelement++;
			
			
			String formatedString;
			if(decoration) {
				
				// https://stackoverflow.com/a/49408297/5081877
				int maxLength = 0;
				for (String keySize : keySet) {
					if (keySize.length() > maxLength) {
						maxLength = keySize.length();
					}
				}
				
				System.out.format("%-" + maxLength + "s =%s\n", key, this.properties.getProperty( key ) );
				if( lastEelement < keySet.size() ) {  // Getting extra null key because of '\n'
					formatedString = String.format("%-" + maxLength + "s =%s%s", key, this.properties.getProperty( key ), lineSeparator);
				} else {
					formatedString = String.format("%-" + maxLength + "s =%s", key, this.properties.getProperty( key ));
				}
			} else {
				if( lastEelement < keySet.size() ) {  // Getting extra null key because of '\n'
					formatedString = String.format("%s=%s%s", key, this.properties.getProperty( key ), lineSeparator);
				} else {
					formatedString = String.format("%s=%s", key, this.properties.getProperty( key ));
				}
			}
			
			result.add( formatedString );
		}
		
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : result) {
			try {
				out.writeBytes(element);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void executeOperation() throws BuildException {
		for (Enumeration<PropertyFiles.Entry> e = this.entries.elements(); e.hasMoreElements();) {
			Entry entry = (Entry)e.nextElement();
			entry.executeOn(this.properties);
		}
		System.out.println("=== executeOperation() === : " + this.entries);
	}
	// Nested Elements
	public static class Entry {
		private String key;
		private String value;
		private String defaultValue;
		
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		
		protected void executeOn(Properties props) throws BuildException {
			//String oldValue = (String)props.get(this.key);
			props.put(this.key, this.value);
		}
	}
}