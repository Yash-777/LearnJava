package com.github.core.utilcollections;

import java.util.Arrays;

public class ArrayList_CustomWrokFlow {
	public static void main(String[] args) {
		
		// https://docs.oracle.com/javase/specs/jvms/se6/html/Overview.doc.html#12237
		// https://docs.oracle.com/javase/7/docs/technotes/guides/jni/spec/types.html
		int a = 128; // int limit ranges to 0 - 127
		int b = 128;
		System.out.println("a : "+ System.identityHashCode(a) ); // a : 366712642
		System.out.println("b : "+ System.identityHashCode(b) ); // b : 1829164700
		
		int x = 127;
		int y = 127;
		System.out.println("x : "+ System.identityHashCode(x) ); // x : 2018699554
		System.out.println("y : "+ System.identityHashCode(y) ); // y : 2018699554
		
		System.out.println("String.length(), array.length, arrayList.size()");
		
		System.out.println("Displaying Array of elements in different formats.");
		int[] ele = {10, 5, 60, 96, 77};
		displayElements(ele);
		
		Integer[] elementData = {25, 77 , 66};
		
		Integer[] copyOf = Arrays.copyOf(elementData, 4);
		System.out.println(" Arrays.copyOf( arr, size )"+ Arrays.toString( copyOf ) ); // [25, 77, 66, null]
		
		//copyElementsForword(elementData, copyOf, 1);
		arrayListAddElement(elementData, copyOf, 1, 21);
		
		Integer[] arrayListAddElement = arrayListAddElement(elementData, 1, 33);
		System.out.println("new Array with index value add : " + Arrays.toString( arrayListAddElement ));
	}
	
	public static void displayElements( int[] a ) {
		// https://stackoverflow.com/a/29140403/5081877
		//Array.toString() « getClass().getName() + "@" + Integer.toHexString(hashCode());
		System.out.println("Array.toString() : "+ a.toString() ); 
		System.out.println("Arrays.toString( arr) : "+Arrays.toString( a ));
		//System.out.println(Arrays.deepToString( a ));  // for nested arrays. wrappers are not allowed.
		
		System.out.println("Java 8 « Streams : ");
		Arrays.stream( a ).forEach(System.out::println);
		
		
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			System.out.print( a[i] +"\t");
		}
		System.out.println(".\n =====");
	}
	
	public static Integer[] arrayListAddElement( Integer[] srcArr, int index, int insertVal) {
		Integer[] copyOf = Arrays.copyOf(srcArr, srcArr.length+1 );
		arrayListAddElement(srcArr, copyOf, index, insertVal);
		return copyOf;
	}
	
	public static void arrayListAddElement( Integer[] srcArr, Integer[] destArr, int srcArrCopyIndexPosition, int insertVal ) {
		copyElementsForword(srcArr, destArr, srcArrCopyIndexPosition);
		
		destArr[srcArrCopyIndexPosition] = insertVal;
		System.out.println("arrayListAddElement « System.arraycopy( )"+ Arrays.toString( destArr ) );
	}
	public static void copyElementsForword( Integer[] srcArr, Integer[] destArr, int srcArrCopyIndexPosition ) {
		
		if( srcArr.length < srcArrCopyIndexPosition + 1 ) {
			throw new ArrayIndexOutOfBoundsException();
		}
		int destArr_Limit = destArr.length - ( srcArrCopyIndexPosition + 1 );
		System.arraycopy(srcArr, srcArrCopyIndexPosition, destArr, srcArrCopyIndexPosition + 1, destArr_Limit );
		System.out.println("copyElementsForword « System.arraycopy( )"+ Arrays.toString( destArr ) );
	}
	
}
