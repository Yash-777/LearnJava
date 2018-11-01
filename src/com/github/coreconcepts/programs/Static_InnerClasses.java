package com.github.coreconcepts.programs;

/**
 * <ul>
 * <b><a href="https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html">Nested Classes</a></b>
 * <li><b>Static Nested Classes</b> «
 * A static class i.e. created inside a class is called static nested class in java.
 * It cannot access non-static data members and methods. It can be accessed by outer class name.
 * It can access static data members of outer class including private.
 * 
 * <pre><code>
 * OuterClass.StaticNestedClass nestedObject =
				new OuterClass.StaticNestedClass();
 * </pre></code>
 * </li>
 * <li><b>Inner Classes</b> « 
 * To instantiate an inner class, you must first instantiate the outer class. Then,
 * create the inner object within the outer object with this
 * <br />syntax:
 * <pre><code>
 *  OuterClass outerObject = new OuterClass();
 *  OuterClass.InnerClass innerObject = outerObject.new InnerClass();
 * </pre></code>
 * </li>
 * </ul>
 *  There are two special kinds of inner classes: local classes and anonymous classes.
 * @author yashwanth.m
 *
 */
public class Static_InnerClasses {
	int a = 10;
	static int b = 20;
	
	static class Class2 {
		void method2() {
			// System.out.println("Parent Instance Vars : "+a); // Not accesable
			System.out.println("Parent Static Vars : "+b); 
		}
	}
	
	class Class3 {
		void method2() {
			System.out.println("Parent Instance Vars : "+a);
			System.out.println("Parent Static Vars : "+b); 
		}
	}
	public static void main(String[] args) {
		System.out.println("static Inner Class");
		Static_InnerClasses.Class2 objStatic = new Static_InnerClasses.Class2();
		objStatic.method2();
		
		System.out.println("Instance Inner Class");
		Static_InnerClasses obj = new Static_InnerClasses();
		Class3 objInstance = obj.new Class3();
		objInstance.method2();
	}
}
