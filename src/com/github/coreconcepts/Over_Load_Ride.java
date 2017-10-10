package com.github.coreconcepts;

/**
 * <P>OverHiding: Static access from reference side.</p>
 * <p>OverRiding: Instance access from constructor side.</p>
 * 
<pre>
O/P «
Static A
Instance B - Over Riding
Static B - Over Hiding
Instance B - Over Riding
</pre>
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
}
class B extends A {
	public static void one(){
		System.out.println("Static B - Over Hiding");
	}
	public void two(){
		System.out.println("Instance B - Over Riding");
	}
}