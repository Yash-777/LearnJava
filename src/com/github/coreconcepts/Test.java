package com.github.coreconcepts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Object[] array = new String[3];
		array[0] = "a";
		//array[1] = 1;   // throws java.lang.ArrayStoreException
		System.out.println("ArrayStoreException : "+array.toString());
		
		// ArrayList is a raw type. References to generic type ArrayList<E> should be parameterized
		@SuppressWarnings("rawtypes")
		ArrayList list = new ArrayList<String>();
		list.add("a");
		list.add(1); // OK
		list.add(new Object()); // OK
		
		ArrayList<String> listGenerics = new ArrayList<String>();
		listGenerics.add("a");
		//list.add(1);  // compilation error
		
		ArrayList<String> reflectionList_Generics = new ArrayList<String>();
		reflectionList_Generics.add("a");
		Method[] methods = List.class.getMethods();
		for(Method m : methods) {
			if(m.getName().equals("add")) {
				try {
					m.invoke(reflectionList_Generics, 1);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		System.out.println(reflectionList_Generics.toString());
		//System.out.println(list_GenericsHetro.get(0));
		//System.out.println((Object) list_GenericsHetro.get(1));
	}
}
