package com.github.wrappers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.github.os.Platform;
import com.github.os.SystemUtil;

/**
 * String buffers support mutable strings. Because String objects are immutable they can be shared.
 * 
 * <p><blockquote><pre>
 *     char data[] = {'a', 'b', 'c'};
 *     String str = new String(data);
 * </pre></blockquote></p>
 * <ul>
 * <li>String:
 * Every string makes an entry in SCP. When use new operator stores in Heap, SCP.
 * In order to consume less memory.
 * <br />By using reflection API we can only change the SCP value, because RunTime Constant Pool. Class MetaData
 * per class structure and SCP are in same Method Area. So, String class static `value` variable data can be changed. 
 * </li>
 * 
 * <li>StringBuffer:
 * Makes an entry in SCP and taking the hash value in SCP into Heap of SB.
 * But when converting to String, it directly return value instead of making an entry in SCP as not uses hash
 * on returning.</li>
 * 
 * <li>Number:
 * Uses native buckets with some range limit to store values.</li>
 * </ul>
 * 
 * As of JDK <=6, String class used some extera fields
 * <p><blockquote><pre>
 *    //The offset is the first index of the storage that is used.
 *    private final int offset;		
 *    // The count is the number of characters in the String.
 *    private final int count;
 * </pre></blockquote></p>
 * <p>So due to this reason, when using reflection API to change the SCP value we get result as.
 * <p><blockquote><pre>
 *    field.set("from", "newValue");
 *    JDK6 outputs to - newV     - Due to count,offset variable in String class.
 *       Arrays.copyOfRange(str, str.offset, str.offset+str.count);
 *    JDK8 outputs to - newValue
 * </pre></blockquote></p>
 * 
 * @author yashwanth.m
 *
 */
public class ImmutableTest extends ReflectionUtil {
	
	public static void main(String[] args) throws Exception {
		Platform platform = new Platform();
		System.out.println("Build Path Java verison : "+ platform.getJavaElements().toString());
		
		String str = "Ya"; // SCP
		char[] chars = {'Y', 'a'}; // Native
		String str_h = new String(str); // SCP, Heap
		String str_p = new String(chars); // Only Heap
		SystemUtil.printWithHashCode(str, str_p);
		mutableString(str, "Ya77"); // Changed value in SCP
		SystemUtil.printWithHashCode(str, str_p); // Return the value form String
		SystemUtil.printWithHashCode(str_h, str_p);
		String s = str_h;
		SystemUtil.printWithHashCode(s, str);
		mutableString(str, "Yash");
		SystemUtil.printWithHashCode(s, str);
		
		//numberNativeBuckets();
		stringSCP();
		//stringBufferMemory();
	}
	public static void stringBufferMemory() throws Exception {
		StringBuffer buff = new StringBuffer("Yash"); // Saved to SCP
		String str = "Yash"; // Interned as available in SCP
		mutableString(str, "Changed"); // Changed in SCP not for Value of StringBuffer in heap.
		SystemUtil.printWithHashCode(buff, str);
			/* String(char[] value, boolean share) { // assert share : "unshared not supported";
			 *    this.value = value;
			 * }
			 */
		mutableString("777", " 7_value_7");
		mutableString("Yash 7_value_7", "Not direct val from SB it uses hash on toString()");
		buff.append("777"); // Checked in SCP with hash. As available in SCP taken that value in SCP
		SystemUtil.printWithHashCode(buff, "777");
	}
	public static void numberNativeBuckets() throws Exception {
		SystemUtil.printHorizontalLine();
		// Object Value in Heap but value points to Native value in int bucket.
		Integer num = new Integer(10);
		SystemUtil.printWithHashCode(num);
		mutableInteger(10, 11);
		SystemUtil.printWithHashCode(num);
		
		int in = 10, in2 = 127; short sh = 10;
		SystemUtil.printWithHashCode(in, sh);
		in = 127; sh = 127;
		SystemUtil.printWithHashCode(in, sh);
		SystemUtil.printWithHashCode(in, in2);
		in = 128; in2 = 128; // Out of the range for int = [-128 « 0 « 127]
		SystemUtil.printWithHashCode(in, in2);
	}
	public static void stringSCP() throws Exception {
		SystemUtil.printHorizontalLine();
		referenceCheck();
		SystemUtil.printHorizontalLine();
		
		String s1 = "Yash", s2 = "Yash"; // SCP - s1, s2 refer to same interned string in SCP
		String s3 = new String("Yash"); // Heap
		SystemUtil.printWithHashCode(s1, s2);
		String s4 = s1.substring(1); // ash - New entry in SCP
		// s1, s2 point to Same String value in Pool, Changing the content. Where as S3 object referees to object in Heap.
		mutableString(s1, "Changed value"); // Reflection-Class JVM_6 - S1:[Chan], JVM_7 S1:[Changed Ref value]
		SystemUtil.print(s1, s2);
		System.out.println("Length funtion return char[].length value : "+ s2.length() );
		SystemUtil.print(s1, s3);
		SystemUtil.printWithHashCode(s1, s3);
		
		System.out.println("SubString of a String : "+ s4);
		
		//String is immutable is that it is used by the class loading mechanism
		SystemUtil.printHorizontalLine();
		Map<String, String> map = new Hashtable<>();
		map.put("class", "Yash"); // « String generated hash code is available in SCM while interning. 
		map.put("class2", new String("Yash"));
		System.out.println("Map Object : "+ map.toString()); // {class2=Changed value, class=Changed value}
		mutableString("Yash", "Changed...");
		map.put("Yash", "Yash");
		System.out.println("Map Object : "+ map.toString()); // {class2=Changed value, class=Changed...}
		
		ArrayList<String> list = new ArrayList<>();
		list.add("Yash");
		list.add( new String("Yash") );
		System.out.println("List Object : "+ list.toString());
	}
	
	/**
	 * Java is Pass-by-value, but allows us to emulate pass be reference by passing a Java reference
	 * (i.e. a pointer) by value. Meaning it passes a copy of the Java reference.
	 */
	public static void referenceCheck() {
		System.out.println("String objects are immutable they can be shared.");
		System.out.println("SCP: Literals reference Check...");
		String s1 = "Yash", s2 = s1;
		SystemUtil.print(s1, s2);
		s1 = "M";
		SystemUtil.print(s1, s2);
		
		System.out.println("HEAP: Object reference Check...");
		String o1 = new String("Yash");
		String o2 = o1;
		SystemUtil.printWithHashCode(o1, o2);
		o1 = new String("M");
		SystemUtil.printWithHashCode(o1, o2);
		
		System.out.println("HEAP: String buffers support mutable strings");
		StringBuffer buff1 = new StringBuffer("Yash");
		StringBuffer buff2 = buff1;
		SystemUtil.printWithHashCode(buff1, buff2);
		buff1.append("-777");
		SystemUtil.printWithHashCode(buff1, buff2);
		
		System.out.println("Custom Class Values...");
		Ref obj1 = new ImmutableTest().new Ref();
		obj1.s = "Yash";
		Ref obj2 = obj1; // Passing the Reference, not the value
		
		SystemUtil.printWithHashCode(obj1.s, obj2.s);
		obj1.s = "M";
		SystemUtil.printWithHashCode(obj1.s, obj2.s);
	}
	
	public class Ref {
		String s; // https://stackoverflow.com/a/11801590/5081877
	}
}
