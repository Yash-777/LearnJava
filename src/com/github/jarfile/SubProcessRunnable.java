package com.github.jarfile;

import java.io.InputStream;
import java.util.Scanner;

public class SubProcessRunnable
implements Runnable {
    private InputStream inputStream;

    public SubProcessRunnable(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(this.inputStream);
        scanner.useDelimiter("\r\n");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }
}