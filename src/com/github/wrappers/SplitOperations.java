package com.github.wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import jdk.nashorn.api.scripting.ScriptObjectMirror;


/**
 * Run Javascript on the JVM with Rhino/Nashorn
 * 
 * Rhino « https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino
Rhino is an open-source implementation of JavaScript written entirely in Java.
It is typically embedded into Java applications to provide scripting to end users.
It is embedded in J2SE 6 as the default Java scripting engine.

Nashorn is a JavaScript engine developed in the Java programming language by Oracle.
It is based on the Da Vinci Machine and has been released with Java 8.

 * @author yashwanth.m
 *
 */
public class SplitOperations {
	public static void main(String[] args) {
		String str = "my.file.png.jpeg", separator = ".";
		javascript_Split(str, separator);
		// lastPortions(str, separator);
		// portions();
		// jarVersion();
		
	}
	public static void javascript_Split( String str, String separator ) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		// Script Variables « expose java objects as variable to script.
		engine.put("strJS", str);
		
		// JavaScript code from file
		File file = new File("E:/StringSplit.js");
		// expose File object as variable to script
		engine.put("file", file);
		
		try {
			engine.eval("print('Script Variables « expose java objects as variable to script.', strJS)");
			
			// javax.script.Invocable is an optional interface.
			Invocable inv = (Invocable) engine;
			
			// JavaScript code in a String
			String functions = "function functionName( functionParam ) { print('Hello, ' + functionParam); }";
			engine.eval(functions);
			// invoke the global function named "functionName"
			inv.invokeFunction("functionName", "function Param value!!" );
			
			// evaluate a script string. The script accesses "file" variable and calls method on it
			engine.eval("print(file.getAbsolutePath())");
			// evaluate JavaScript code from given file - specified by first argument
			engine.eval( new java.io.FileReader( file ) );
			
			String[] typedArray = (String[]) inv.invokeFunction("splitasJavaArray", str );
			System.out.println("File : Function returns an array : "+ typedArray[1] );
			
			ScriptObjectMirror scriptObject = (ScriptObjectMirror) inv.invokeFunction("splitasJavaScriptArray", str, separator );
			System.out.println("File : Function return script obj : "+ convert( scriptObject ) );
			
			Object eval = engine.eval("(function() {return ['a', 'b'];})()");
			Object result = convert(eval);
			System.out.println("Result: {}"+ result);
			
			// JavaScript code in a String. This code defines a script object 'obj' with one method called 'hello'.
			String objectFunction = "var obj = new Object(); obj.hello = function(name) { print('Hello, ' + name); }";
			engine.eval(objectFunction);
			// get script object on which we want to call the method
			Object object = engine.get("obj");
			inv.invokeMethod(object, "hello", "Yash !!" );
			
			Object fileObjectFunction = engine.get("objfile");
			inv.invokeMethod(fileObjectFunction, "hello", "Yashwanth !!" );
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Object convert(final Object obj) {
		System.out.println("\tJAVASCRIPT OBJECT: {}"+ obj.getClass());
		if (obj instanceof Bindings) {
			try {
				final Class<?> cls = Class.forName("jdk.nashorn.api.scripting.ScriptObjectMirror");
				System.out.println("\tNashorn detected");
				if (cls.isAssignableFrom(obj.getClass())) {
					final Method isArray = cls.getMethod("isArray");
					final Object result = isArray.invoke(obj);
					if (result != null && result.equals(true)) {
						final Method values = cls.getMethod("values");
						final Object vals = values.invoke(obj);
						System.err.println( vals );
						if (vals instanceof Collection<?>) {
							final Collection<?> coll = (Collection<?>) vals;
							Object[] array = coll.toArray(new Object[0]);
							return array;
						}
					}
				}
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		}
		if (obj instanceof List<?>) {
			final List<?> list = (List<?>) obj;
			Object[] array = list.toArray(new Object[0]);
			return array;
		}
		return obj;
	}
	
	public static void lastPortions(String str, String separator) {
		System.out.println("lastIndexOf() & substring() methods of Java.lang.String");
		// int firstIndex = str.indexOf( separator );
		int lastIndexOf = str.lastIndexOf( separator );
		String begningPortion = str.substring( 0, lastIndexOf );
		String endPortion = str.substring( lastIndexOf + 1 );
		System.out.println("First Portion : " + begningPortion );
		System.out.println("Last  Portion : " + endPortion );
		
		// Splits the provided text into an array. https://stackoverflow.com/a/47590792/5081877
		String[] split = str.split( Pattern.quote( separator ) );
		String lastOne = split[split.length-1];
		System.out.println("Split Array : "+ lastOne);
		
		System.out.println("Java 8 sequential ordered stream from an array.");
		String firstItem = Stream.of( split )
								 .reduce( (first,last) -> first ).get();
		String lastItem = Stream.of( split )
								.reduce( (first,last) -> last ).get();
		System.out.println("First Item : "+ firstItem);
		System.out.println("Last  Item : "+ lastItem);
		
		

		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html
		// org.apache.commons.lang3.StringUtils.substringAfterLast(String str, String separator);
		String afterLast = StringUtils.substringAfterLast(str, separator);
		System.out.println("StringUtils AfterLast : "+ afterLast);
		
		String beforeLast = StringUtils.substringBeforeLast(str, separator);
		System.out.println("StringUtils BeforeLast : "+ beforeLast);
		
		String open = "[", close = "]";
		String[] groups = StringUtils.substringsBetween("Yash[777]Sam[7]", open, close);
		System.out.println("String that is nested in between two Strings "+ groups[0]);
		
		// Guava « com.google.common.base.Splitter
		Splitter splitter = Splitter.on( separator ).trimResults();
		Iterable<String> iterable = splitter.split( str );
		String first_Iterable = Iterables.getFirst(iterable, "");
		String last_Iterable = Iterables.getLast( iterable );
		System.out.println(" Guava FirstElement : "+ first_Iterable);
		System.out.println(" Guava LastElement  : "+ last_Iterable);
	}
	
	public static void portions() { // String str, String separator
		String str = "abc?def,ghi?jkl,mno,pqr?stu,vwx?yz";
		// ? - \\? we have to escape sequence of some characters, to avoid use Pattern.quote( ",?" );
		String quotedText = Pattern.quote( "?" );
		String[] split = str.split(quotedText, 2); // ["abc", "def,ghi?jkl,mno,pqr?stu,vwx?yz"]
		for (String string : split) {
			System.out.println( string );
		}
	}
}