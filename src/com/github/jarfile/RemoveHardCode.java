package com.github.jarfile;

public class RemoveHardCode {
    public static void main(String[] args) throws InterruptedException {
        StringBuffer argumentsList = new StringBuffer();
        int i = 0;
        while (i < args.length) {
            argumentsList.append("args[" + i + "] = " + args[i] + "\n");
            ++i;
        }
        System.out.println("Command line arguments :\n" + argumentsList.toString());
        System.out.format("System properties :\n [key1:%s] \n [key2:%s]", System.getProperty("key1"), System.getProperty("key2"));
        System.out.format("\n [key3:%s] \n [key4:%s]\n", System.getProperty("key3"), System.getProperty("key4"));
        Thread.sleep(10000);
        System.out.println("Completed Execution.");
    }
}