package com.github.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class CPU_Cores_JVM is to determine the number of processes available to the
 * Java Virtual Machine by using the static Runtime method.
 * <P> Number of CPU cores allocated to the JVM 
 * <a href="https://stackoverflow.com/q/4759570/5081877">stackoverflow Post</a>
 * @author yashwanth.m
 *
 */
public class CPU_Cores_JVM {
	public static void main(String[] args) {
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("Allocation CPU Core in Current Machine « "+cores);
		
		//System.out.println("CPU Cores : "+ getNumberOfCPUCores() );
		
		System.out.println("Windows with Cygwin installed : "+ System.getenv("NUMBER_OF_PROCESSORS"));
	}
	public static int getNumberOfCPUCores() {
		String command = "cmd /C WMIC CPU Get /Format:List";
		Process process = null;
		int numberOfCores = 0;
		try {
			process = Runtime.getRuntime().exec(command);
	
			BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()) );
			String line;
	
			while ((line = reader.readLine()) != null) {
				if (line.contains("NumberOfCores")) {
					numberOfCores = Integer.parseInt(line.split("=")[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numberOfCores;
	}
}
