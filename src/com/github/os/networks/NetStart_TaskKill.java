package com.github.os.networks;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://stackoverflow.com/q/6204003/5081877
 * <pre>
 * <{@code
 * @echo off 
cls
echo %date% 
FOR /F "usebackq tokens=5" %%i IN (`netstat -aon ^| find "8080"`) DO taskkill /F /PID %%i
echo PID killed!!!!!!
pause
 * 
 * }
 * </pre>
 * @author yashwanth.m
 *
 */
public class NetStart_TaskKill {
	public static void main(String[] args) throws Exception {
		System.out.println("===== NetStart_TaskKill =====");
		String port = "8080";
		Set<String> result = netstat(port);
		System.out.println( result );
		
		for (String string : result) {
			taskKill(string);
		}
	}
	static void taskKill(String processId) throws Exception {
		System.out.println("===== TaskKill =====");
		String command = "netstat -a -n -o | find";
		Process proc = Runtime.getRuntime().exec("cmd.exe /C "+ command +" \""+ processId+"\"");
		
		String result = Troubleshooting.getProcessOutPut(proc);
		System.out.println(result);
		
		proc.waitFor();
	}
	static Set<String> netstat( String port) throws IOException, InterruptedException {
		System.out.println("===== NetStart =====");
		String command = "taskkill /f /pid";
		Process proc = Runtime.getRuntime().exec("cmd.exe /C "+ command +" \""+ port+"\"");
		
		String result = Troubleshooting.getProcessOutPut(proc);
		
		proc.waitFor();
		
		String[] lines = result.split("\n");
		Set<String> treeSet = new TreeSet<>();
		
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			System.out.format("Line[%d]: %s\n",i, line);
			int lastIndexOf = line.lastIndexOf(" ");
			String processId = line.substring(lastIndexOf, line.length());
			treeSet.add(processId);
			//System.out.println("Process ID: "+processId);
		}
		
		for (String string : treeSet) {
			System.out.println("Process ID: "+string);
		}
		
		return treeSet;
	}
	
}