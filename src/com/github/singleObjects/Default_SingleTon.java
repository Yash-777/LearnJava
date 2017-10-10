package com.github.singleObjects;

/**
 * The class Default_SingleTon having private constructor, So that it can be accessed
 * only in current class. In static block we are creating object by allocating memory to
 * the instance fields. Instantiating using static block as it is loaded once per class
 * so that we can maintain only single object, when ever request for the object we will
 * return the same object.
 * 
 * <p> NOTE: If a class is declared final then its methods are automatically (effectively) final.
 * This is because a final class cannot be sub classed, and thus its methods cannot be overridden.</P>
 * 
 * @author yashwanth.m
 *
 */
public class Default_SingleTon {
	private static Default_SingleTon instance;
	
	private Default_SingleTon(){
		System.out.println();
	}
	
	static {
		instance = new Default_SingleTon();
	}

	public static synchronized Default_SingleTon getInstance() {
		if (instance == null) {
			instance = new Default_SingleTon();
		}
		return instance;
	}
	
	public void myMethod(){
		System.out.println("My Method");
	}
	
	class A { } // Instance Scope
	static class B { } // Static Scope
	
	enum C{ obj; void method() { System.out.println("enum Method"); } }
	final class D {}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) { 
	/*will fail - compilation error, you need an instance of Test to instantiate A*/
		A a = new Default_SingleTon().new A();
	/*will compile successfully, not instance of Test is needed to instantiate B */
		B b = new B();
		C.obj.method();
		D d = new Default_SingleTon().new D();
	}
}

class Parametarised_SingleTon {
	private volatile static Parametarised_SingleTon instance;
	
	/*private MySingleTonParametarisedCons(){
		System.out.println("Default");
	}*/
	
	private Parametarised_SingleTon(Integer i){
		System.out.println("Value:"+i);
	}
	
	static {
		instance = new Parametarised_SingleTon( new Integer(10) );
	}

	public static synchronized Parametarised_SingleTon getInstance() {
		return instance;
	}
	
	public void myMethod(){
		System.out.println("My Method");
	}
}
