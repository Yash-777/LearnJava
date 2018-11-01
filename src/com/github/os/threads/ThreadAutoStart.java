package com.github.os.threads;

public class ThreadAutoStart extends Thread {
	public ThreadAutoStart() {
		super();
		start();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(" I : "+ i);
		}
	}
	
	public static void main(String[] args) {
		new ThreadAutoStart();
	}
}
