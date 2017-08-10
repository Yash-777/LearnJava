package com.github.exceptionshandling;

public class StackOverflowErrorExample {
	
	public static void recursivePrint(int num) {
		System.out.print("Number: " + num);
		
		if(num == 0)
			return;
		else
			recursivePrint(++num);
	}
	
	public static void main(String[] args) {
		//StackOverflowErrorExample.recursivePrint(1);
		A obj = new A();
		System.out.println(obj.toString());
	}
}

class A {
	private int aValue;
	private B bInstance = null;
	
	public A() {
		aValue = 0;
		bInstance = new B();
	}
	
	@Override
	public String toString() {
		return "A";
	}
}

class B {
	private int bValue;
	private A aInstance = null;
	
	public B() {
		bValue = 10;
		aInstance = new A();
	}
	
	@Override
	public String toString() {
		return "B";
	}
}
