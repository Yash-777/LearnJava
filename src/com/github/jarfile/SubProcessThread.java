package com.github.jarfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubProcessThread extends Thread
{
    private InputStream inputStream;
    private boolean sysout;
    public StringBuilder output;
    
    public SubProcessThread(final InputStream inputStream, final boolean sysout) {
        this.output = new StringBuilder();
        this.inputStream = inputStream;
        this.sysout = sysout;
    }
    
    @Override
    public void run() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                if (this.sysout) {
                    System.out.println(line);
                }
                this.output.append(line).append("\n");
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getOutput() {
        return this.output.toString();
    }
}