package com.github.objects;

/**
 * @author yashwanth.m
 *
 */
public enum Enum_SingleTon {
	INSTANCE;
	
	public synchronized void instanceMethod(){
		System.out.println("Enum Method");
	}
}

