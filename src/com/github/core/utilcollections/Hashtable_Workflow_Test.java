package com.github.core.utilcollections;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.os.SystemUtil;
import com.github.wrappers.ImmutableTest;

public class Hashtable_Workflow_Test {
	public static void main(String[] args) throws Exception {
		int initialCapacity = 1; float loadFactor = 0.75f;
		Map<String, String> map = new Hashtable<>(initialCapacity, loadFactor);
		map.put("class", "Yash"); // « String generated hash code is available in SCM while interning. 
		map.put("class2", new String("Yash"));
		System.out.println("Map Object : "+ map.toString()); // {class2=Changed value, class=Changed value}
		ImmutableTest.mutableString("Yash", "Changed...");
		map.put("Yash", "Yash");
		System.out.println("Map Object : "+ map.toString()); // {class2=Changed value, class=Changed...}
		
		boolean containsKey = map.containsKey("Yash"); // (e.hash == key.hashCode()) && e.key.equals(key)
		SystemUtil.print(containsKey);
		
		for (Integer i = 0; i < 25; i++) {
			map.put(i.toString(), "Yash");
		}
		
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			SystemUtil.printMapEntery(entry);
		}
		
		//LinkedHashMap
	}
}
