package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UN_Modifiable_List {
	public static void main(String[] argv) throws Exception {
		List<String> stuff = Arrays.asList(new String[] { "a", "b" ,"c"});
		try {
			stuff.add("Yashwanth.M");
		} catch(Exception e) {
			System.out.println("List Interface as it is converted and containds data which id <B>UN-Modifiable</B>");
		}
		System.out.println("Array --> List"+stuff);
		
		List<String> list = new ArrayList<String>(stuff);
		list = Collections.unmodifiableList(list);
		try {
			list.set(0, "new value");
		} catch (UnsupportedOperationException e) {
			System.out.println("Can not add any data to list because it is UN-Modifiable");
		}
		
		Set<String> set = new HashSet<String>(stuff);
		set = Collections.unmodifiableSet(set);
		Map<?, ?> map = new HashMap<Object, Object>();
		map = Collections.unmodifiableMap(map);
		System.out.println("Collection is read-only now.");
	}
}
