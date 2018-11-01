package com.github.coreconcepts;

/**
 * <ul> Super and This Keywords
 * <li>This refers to current class object where as super refers to super class object.</li>
 * 
 * <li>Using this we can access all non static methods and variables. Using super
 * we can access super class variable and methods from sub class.</li>
 * <li>Using this(); call we can call other constructor in same class. Using super we can call
 * super class constructor from sub class constructor.</li>
 * </ul>
 * 
 * @author yashwanth.m
 *
 */
class SuperClass {
	static {
		System.out.println("Super Class - Static block");
	}
	{
		System.out.println("Super Class - Instance block");
	}
	
	SuperClass() { // Default Call to super(); Constructor
		System.out.println(" Super Class -Default constructor ");
	}
	
	void instanceMethod() {
		System.out.println(" Super Class -instanceMethod");
	}
}

class ThisClass extends SuperClass {
	
	static {
		System.out.println("this Class - Static block");
	}
	{
		System.out.println("this Class - Instance block");
	}
	
	int a;
	
	ThisClass() { // Default Call to super(); Constructor
		super(); // default « first statement of the constructor.
		System.out.println(" Sub Class -Default constructor ");
	}
	
	// constructor overloading
	ThisClass( int a ) {
		// Use to invoke current class constructor
		this(); // Calling default constructor, Use it as first statement of the constructor.
		
		this.a = a; // use if both instance and local variable names are same.
		
		System.out.println(" Sub Class -Parametarised constructor ");
		
		this.instanceMethod(); // use this keyword to call current class non static methods .
	}
	
	void instanceMethod() {
		
		System.out.println(" Sub Class -instanceMethod (1)");
		super.instanceMethod();
		System.out.println(" Sub Class -instanceMethod (2)");
	}
	
}

public class This_Super_Keywords {
	public static void main(String[] args) {
		System.out.println("Main start...");
		new ThisClass( 10 );
		System.out.println("Main end.");
		
		System.out.println("=====================");
		SuperClass a = new ThisClass();
		a.instanceMethod();
		
		System.out.println("---------------------");
		ThisClass b = new ThisClass();
		b.instanceMethod();
	}
}
