package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class UN_Modifiable_List {
	public static void main(String[] argv) throws Exception {
		
		List<String> list_of_Arrays = Arrays.asList(new String[] { "a", "b" ,"c"});
		
		try {
			list_of_Arrays.add("Yashwanth.M");
		} catch(java.lang.UnsupportedOperationException e) {
			System.out.println("List Interface executes AbstractList add() fucntion which throws UnsupportedOperationException.");
		}
		System.out.println("Arrays → List : " + list_of_Arrays);

		Iterator<String> iterator = list_of_Arrays.iterator();
		while (iterator.hasNext()) System.out.println("Iteration : " + iterator.next() );
		
		ListIterator<String> listIterator = list_of_Arrays.listIterator();
		while (listIterator.hasNext())    System.out.println("Forward  iteration : " + listIterator.next() );
		while(listIterator.hasPrevious()) System.out.println("Backward iteration : " + listIterator.previous());

		
		List<String> list = new ArrayList<String>(list_of_Arrays);
		list.add("new List Object"); // List created using constructor.
		
		list = Collections.unmodifiableList(list);
		try {
			list.set(0, "new value");
		} catch (UnsupportedOperationException e) {
			System.out.println("Can not add any data to list because it is UN-Modifiable");
		}
		
		Set<String> set = new HashSet<String>(list_of_Arrays);
		set = Collections.unmodifiableSet(set);
		
		Map<?, ?> map = new HashMap<Object, Object>();
		map = Collections.unmodifiableMap(map);
		System.out.println("Collection is read-only now.");
	}
}
