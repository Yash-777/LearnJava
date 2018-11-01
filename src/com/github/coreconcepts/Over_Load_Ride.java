package com.github.coreconcepts;

/**
 * <p>Defining a Method with the Same Signature as a Superclass's Method
 * <P>OverHiding: Static access from reference side.</p>
 * <p>OverRiding: Instance access from constructor side.</p>
 * 
<pre>
O/P «
Static A
Instance B
Static B - Over Hiding
Instance B - Over Riding
</pre>
 * 
 * <UL>
 *   <LI>If Class or Method is final we can't Override it.</LI>
 * </UL>
 * 
 * @author yashwanth.m
 *
 */
public class Over_Load_Ride {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		A a = new B();
		a.one();
		a.two();
		
		B b = new B();
		b.one();
		b.two();
	}
}
class A {
	public static void one(){
		System.out.println("Static A");
	}
	public void two(){
		System.out.println("Instance A");
	}
	public void three(int a){
		System.out.println("Parameter and Return Type");
	}
	public void three(String a){
		System.out.println("Paramerter Change [Same method with Return Type change leads to ERROR]");
	}
}
class B extends A {
	public static void one(){
		System.out.println("Static B - Over Hiding");
	}
	public void two(){
		System.out.println("Instance B - Over Riding");
	}
}