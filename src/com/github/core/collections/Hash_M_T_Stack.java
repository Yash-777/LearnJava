package com.github.coreconcepts;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Hash_M_T_Stack {
	public static void main(String[] args) {
		new Thread() {
			@Override public void run() {
				HashMap<String, Integer> hm = new HashMap<String, Integer>();
				hm.put("key0", 10); // Compiler Widens.
				hm.put("key1", null);
				hm.put("key0", new Integer(16)); // Overridden.
				hm.put(null, 20);
				hm.put(null, 70);
				hm.put(null, null);
				System.out.println("HashMap : "+hm); // hm.toString()
				
				Iterator<Entry<String, Integer>> it = hm.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
					System.out.println(pair.getKey() + " = " + pair.getValue());
				
					it.remove(); // avoids a ConcurrentModificationException
				}
				// we can convert HashMap to synchronizedMap
				Collections.synchronizedMap(hm);
			}
		}.start();
		new Thread() {
			@Override public void run() {
				Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
				try {
					ht.put("product1", 12);
					ht.put("product2", 13);
					ht.put("product2", 14);
				//	ht.put(null, 20);
				//	ht.put("product2", null);
					System.out.println("hash Table : "+ht);
					
				} catch (NullPointerException nul) {
					System.out.println("HashTable will not accept null's eighter in key/value");
					IllegalArgumentException iae = new IllegalArgumentException("nulls not accept");
					iae.initCause(nul);
					throw iae;
				}
			}
		}.start();
	}
}