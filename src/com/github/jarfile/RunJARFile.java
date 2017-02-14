package com.github.jarfile;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;

public class RunJARFile
{
    static String newClient;
    
    static {
        RunJARFile.newClient = "cmd.exe /c start ";
    }
    
    public static void main(final String[] args) {
        runJarUsingParams();
        final String command = "ping www.github.com";
        commandsOfOS(RunJARFile.newClient, command);
    }
    
    private static void commandsOfOS(final String newClient, final String command) {
        try {
            final Process process = Runtime.getRuntime().exec(String.valueOf(newClient) + command);
            final InputStream inputStream = process.getInputStream();
            final SubProcessThread inputConsumer = new SubProcessThread(inputStream, true);
            inputConsumer.start();
            final int status = process.waitFor();
            System.out.println("Exited with status: " + status);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void runJarUsingParams() {
        final String jarLocation = "D:\\JarFiles\\CoreConcepts.jar";
        final List<String> commandLineArguments = new ArrayList<String>();
        commandLineArguments.add("args1");
        commandLineArguments.add("args2");
        commandLineArguments.add("args3");
        final Map<String, String> systemProperties = new HashMap<String, String>();
        systemProperties.put("key1", "value1");
        systemProperties.put("key2", "value2");
        systemProperties.put("key3", "value3");
        systemProperties.put("key4", "value4");
        System.out.println("Command-Line Arguments : " + commandLineArgumentsList(commandLineArguments));
        System.out.println("Environment Variables" + systemPropertiesMap(systemProperties));
        runtime(jarLocation, commandLineArguments, systemProperties);
        builder(jarLocation, commandLineArguments, systemProperties);
    }
    
    public static void list2StringArray(final List<String> commandLineArguments) {
        final Object[] arguments = commandLineArguments.toArray();
        final String[] strCopyArguments = Arrays.copyOf(arguments, arguments.length, (Class<? extends String[]>)String[].class);
        System.out.println("Command-Line Arguments : " + strCopyArguments.toString());
        String[] array;
        for (int length = (array = strCopyArguments).length, i = 0; i < length; ++i) {
            final String sc = array[i];
            System.out.println(sc);
        }
        String[] strArrayArguments = new String[commandLineArguments.size()];
        strArrayArguments = commandLineArguments.toArray(strArrayArguments);
        System.out.println("Command-Line Arguments : " + strArrayArguments.toString());
        String[] array2;
        for (int length2 = (array2 = strArrayArguments).length, j = 0; j < length2; ++j) {
            final String sa = array2[j];
            System.out.println(sa);
        }
        final String[] arr = commandLineArguments.toArray(new String[commandLineArguments.size()]);
        System.out.println("Command-Line Arguments : " + arr.toString());
        String[] array3;
        for (int length3 = (array3 = arr).length, k = 0; k < length3; ++k) {
            final String sa2 = array3[k];
            System.out.println(sa2);
        }
    }
    
    public static String commandLineArgumentsList(final List<String> commandLineArguments) {
        return commandLineArguments.toString().replaceAll("\\[|,|\\]", "");
    }
    
    public static String systemPropertiesMap(final Map<String, String> systemProperties) {
        return systemProperties.toString().replaceAll("\\{|[,]", " -D").replaceAll("\\-D ", "-D").replaceAll("}", "");
    }
    
    public static void runtime(final String jarLocation, final List<String> commandLineArguments, final Map<String, String> systemProperties) {
        final String runCommand = "java " + systemPropertiesMap(systemProperties) + " -jar " + jarLocation + " " + commandLineArgumentsList(commandLineArguments);
        System.out.println("Run Command : [" + runCommand + "]");
        try {
            final Process process = Runtime.getRuntime().exec(runCommand);
            Thread.sleep(5000L);
            process.destroy();
            final InputStream inputStream = process.getInputStream();
            final SubProcessRunnable spr = new SubProcessRunnable(inputStream);
            final Thread thread = new Thread((Runnable)spr, "StreamReader_ThreadID");
            thread.start();
            final int status = process.waitFor();
            System.out.println("Exited with status: " + status);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void builder(final String jarLocation, final List<String> commandLineArguments, final Map<String, String> systemProperties) {
        final String runCommand = "java " + systemPropertiesMap(systemProperties) + " -jar " + jarLocation + " " + commandLineArgumentsList(commandLineArguments);
        System.out.println("Run Command : [" + runCommand + "]");
        final ProcessBuilder builder = new ProcessBuilder(Arrays.asList(runCommand.split(" ")));
        try {
            final Process process = builder.start();
            final InputStream inputStream = process.getInputStream();
            final SubProcessThread inputConsumer = new SubProcessThread(inputStream, true);
            inputConsumer.start();
            Thread.sleep(5000L);
            process.destroy();
            final int exitValue = process.waitFor();
            if (exitValue != 0) {
                System.out.println("Abnormal process termination");
            }
            System.out.println("Exited with status: " + exitValue);
            final String processOuput = inputConsumer.output.toString();
            System.out.println("OUT PUT «\n" + processOuput);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}