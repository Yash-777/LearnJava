package com.github.os.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExecutorService_FixedPool {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(ExecutorService_FixedPool::forword);
		service.submit(ExecutorService_FixedPool::backword);
		
		service.shutdown();
	}
	static int count = 20;
	public static void forword() {
		for (int i = 0; i < count; i++) {
			System.out.println("forword : "+i);
		}
	}
	public static void backword() {
		for (int i = count; i > 0; i--) {
			System.out.println("\tbackword : "+i);
		}
	}
}
