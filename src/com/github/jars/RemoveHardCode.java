package com.github.jars;

public class RemoveHardCode {
	public static void main(String[] args) throws InterruptedException {
		
		StringBuffer argumentsList = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			argumentsList.append("args["+i+"] = "+args[i]+"\n");
		}
		System.out.println("Command line arguments :\n"+argumentsList.toString());
		
		System.out.format("System properties :\n [key1:%s] \n [key2:%s]",
		System.getProperty("key1"), System.getProperty("key2"));
		System.out.format("\n [key3:%s] \n [key4:%s]\n",
				System.getProperty("key3"), System.getProperty("key4"));
		
		Thread.sleep( 1000 * 10 );
		
		System.out.println("Completed Execution.");
	}
}
