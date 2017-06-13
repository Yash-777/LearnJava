package com.github.jdbc;

/**
 * <ul>Pool

<li>Pooling Mechanism is the way of creating the Objects in advance. When a class is loaded.</li>
<li>It improves the application performance [By re using same object's to perform any action 
on Object-Data] & memory [allocating and de-allocating many objects creates a significant 
memory management overhead].</li>
<li>Object clean-up is not required as we are using same Object, reducing the Garbage collection load.</li>


<ul><b>String Constant pool</b>
<li>String literal pool maintains only one copy of each distinct string value. which must be immutable.</li>
<li>When the intern method is invoked, it check object availability with same content in pool using 
equals method. « If String-copy is available in the Pool then returns the reference. « Otherwise, 
String object is added to the pool and returns the reference.</li>
</ul>
</ul>

 * @author yashwanth.m
 *
 */
public class StringPoolTest {
	
	public static void main(String[] args) { // Integer.valueOf(), String.equals()
		String eol = System.getProperty("line.separator"); //java7 System.lineSeparator();
		
		String s1 = "Yash".intern();
		System.out.format("Val:%s Hash:%s SYS:%s "+eol, s1, s1.hashCode(), System.identityHashCode(s1));
		String s2 = "Yas"+"h".intern();
		System.out.format("Val:%s Hash:%s SYS:%s "+eol, s2, s2.hashCode(), System.identityHashCode(s2));
		String s3 = "Yas".intern()+"h".intern();
		System.out.format("Val:%s Hash:%s SYS:%s "+eol, s3, s3.hashCode(), System.identityHashCode(s3));
		String s4 = "Yas"+"h";
		System.out.format("Val:%s Hash:%s SYS:%s "+eol, s4, s4.hashCode(), System.identityHashCode(s4));
	}
}
