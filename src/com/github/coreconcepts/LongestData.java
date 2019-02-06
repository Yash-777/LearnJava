package com.github.coreconcepts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

public class LongestData {
	
	public static void main (String[] args) throws java.lang.Exception {
		String [] countries = {"Egypt", "France", "Japan", "Switzerland"};
		Integer[] populations = {92592000, 66991000, 126860000, 8401120};
		printTable(countries, populations);
	}

	public static void printTable(String[] countries, Integer[] populations){
		int longestInput = 0;
		for(int i = 0; i < countries.length; i++){
			int countLength = countries[i].length();
			int popLength = String.valueOf(populations[i]).length();
			if(countLength + popLength > longestInput)
				longestInput = countLength + popLength;
		}
		
		String longestString = getLongestString( countries );
		System.out.format("longest string: '%s'\n", longestString);
		
		Integer longestNumber = getLongestNumber( populations );
		System.out.format("longest Number: '%s'\n", longestNumber);
		
		for(int i = 0; i < countries.length; i++)
			System.out.format("%-" + longestString.length() + "s | %" + String.valueOf(longestNumber).length() + "d\n", countries[i], populations[i]);
		
		
		String[] data = {"one", "two", "three", "four"};
		JList<?> dataList = new JList<String>(data);
		System.out.println("javax.swing.JList : "+ dataList.toString() );
		
		// The value of the JList model property is an object that provides
		// a read-only view of the data.  It was constructed automatically.
		for(int i = 0; i < dataList.getModel().getSize(); i++) {
			System.out.println(dataList.getModel().getElementAt(i).toString().length());
		}
		
	}
	public static String getLongestString(String[] array) {
		int maxLength = 0;
		String longestString = null;
		for (String s : array) {
			if (s.length() > maxLength) {
				maxLength = s.length();
				longestString = s;
			}
		}
		return longestString;
	}
	public static Integer getLongestNumber(Integer[] array) {
		int maxLength = 0;
		Integer longestNumber = null;
		for (Integer i : array) {
			if (String.valueOf(i).length() > maxLength) {
				maxLength = String.valueOf(i).length();
				longestNumber = i;
			}
		}
		return longestNumber;
	}
	
	@SuppressWarnings("unchecked")
	public static void arraySortWhileInsert() {
		
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
