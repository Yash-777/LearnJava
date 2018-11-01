package com.github.jars;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <ul>Open Terminal Console for
 * <li> Windows « [cmd.exe /c start      java -jar *.jar]
 * <li> MAC     « [open -a Terminal.app  java -jar *.jar]
 * <li> Linux   « [xterm -e              java -jar *.jar]
 * 
 * @author yashwanth.m
 *
 */
public class RunjarFile_Freemaker {
	// Configure FreeMarker « You should do this ONLY ONCE, when your application starts,
	// then reuse the same Configuration object elsewhere.
	@Deprecated
	static Configuration cfg = new Configuration();
	
	public static void main(String[] args) throws Exception {
		/*String templateFile = ApplicationPath.getApplicationPath()+File.separator+"fremarkerTemplate.ftl";
		System.out.println("Tempate File path : "+ templateFile );*/
		htmlTemplate( "fremarkerTemplate.ftl", "D:\\FreemakerOutPutFile.html" );
	}
	
	public static void htmlTemplate(String templateFile, String outputTemplate) throws Exception {

		// 2.1. Prepare the template input:
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Vogella example");
		input.put("exampleObject", "Java object");

		List<String> countries = new ArrayList<String>();
		countries.add("India");
		countries.add("United States");
		countries.add("Germany");
		countries.add("France");
		input.put("countries", countries);
		
		// 2.2. Get the template
		Template template = cfg.getTemplate( templateFile );

		// 2.3. Generate the output « Write output to the console
		Writer consoleWriter = new OutputStreamWriter(System.out);
		template.process(input, consoleWriter);

		// For the sake of example, also write output into a file:
		Writer fileWriter = new FileWriter(new File( outputTemplate ));
		template.process(input, fileWriter);
		fileWriter.close();
	}
	
	public static void createRunnableJAR_MAC() throws IOException, InterruptedException {
		String macRunnableJAR = "/Users/Yashwanth/RunnableJarConversion";
		createMacLauncher(macRunnableJAR, "/usr/bin/java", "/usr/local/LearnJava/LearnJava.jar", "777");
		
		System.out.println("Run Command : ["+macRunnableJAR+"]");
		Process process = Runtime.getRuntime().exec( "open -a Terminal.app "+macRunnableJAR );
		process.waitFor();
	}
	
	/**
	 * runableJarOutputTemplateMAC.ftl
	 * <p>
	 * <pre><code> ${javaLocation} -jar ${userJarLocation} ${userid} </code></pre>
	 * </p>
	 * @param userid			some argument to runnable jar file
	 * @param macRunnableJAR	Creating a new Runnable Jar File with this name.
	 * @param userjarlocation	Jar file location.
	 * @param javapathmac		java installed location in MAC OS.
	 */
	public static void createMacLauncher(String macRunnableJAR, String javapathmac, String userjarlocation, String userid) {
		
		try {
			//Load template from runableJarTemplate.ftl
			Template template = cfg.getTemplate("runableJarOutputTemplateMAC.ftl");
			
			// Build the data-model
			Map<String, Object> input_DataModel = new HashMap<String, Object>();
			input_DataModel.put("javaLocation", javapathmac);
			input_DataModel.put("userJarLocation", userjarlocation);
			
			input_DataModel.put("userid", userid);
			
			// File output
			File executableFile = new File( macRunnableJAR );
			if( !executableFile.getParentFile().exists() )
				executableFile.getParentFile().mkdirs();
			
			// File output
			Writer fileWriter = new FileWriter ( executableFile );
			template.process(input_DataModel, fileWriter);
			fileWriter.flush();
			fileWriter.close();
			
			// Setting file as executable (Runnable Jar File)
			executableFile.setExecutable(true);
			executableFile.setReadable(true);
			executableFile.setWritable(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
