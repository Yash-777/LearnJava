package com.github.core.utilcollections;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 
 * https://stackoverflow.com/q/109383/5081877
 * 
 * @author yashwanth.m
 *
 */
public class HashMap_ValueSort {
	public static void main(String[] args) {
		HashMap<String, String> hm = new HashMap<>();
		hm.put("1", "b");
		hm.put("2", "t");
		hm.put("4", "q");
		hm.put("3", "a");
		hm.put("11", "a");
		
		System.out.println("Hash map : "+ hm);
		Map<Object, Object> sortedMap_Value = 
				hm.entrySet().stream()
					.sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println("Sorted Map [Vlaues]: "+ sortedMap_Value);
		
		Map<Object, Object> sortedMap_Key = 
				hm.entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println("Sorted Map [Keys]: "+ sortedMap_Key);
		
		System.out.println("Java 8 lower version solution [Key]: "+ sortedMap(hm, false));
		System.out.println("Java 8 lower version solution [Value]: "+ sortedMap(hm, true));
		
		// Sorting in Reverse order.
		hm.entrySet().stream()
			.sorted( (k1, k2) -> -k1.getValue().compareTo( k2.getValue() ) )
			.forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
		
		Map<Object, Object> sortedMapReverse_Value = 
				hm.entrySet().stream()
					.sorted( Collections.reverseOrder( Map.Entry.comparingByValue() ) )
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println("Sorted Map Reverse order[Vlaues]: "+ sortedMapReverse_Value);
		
		Map<Object, Object> sortedMapReverseLimit_Value = 
				hm.entrySet().stream()
					.sorted( Collections.reverseOrder( Map.Entry.comparingByValue() ) )
					.limit( 3 )
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println("Sorted Map Reverse order[Vlaues]: "+ sortedMapReverseLimit_Value);
	}
	
	public static <K, V> Map<K, V> sortedMap(Map<K, V> map, boolean usingValue) {
		List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Object>() {
				@SuppressWarnings("unchecked")
				public int compare(Object o1, Object o2) {
					if( usingValue )
					return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue())
								.compareTo(((Map.Entry<K, V>) (o2)).getValue());
					
					return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getKey())
								.compareTo((V) ((Map.Entry<K, V>) (o2)).getKey());
				}
			}
		);
		
		Map<K, V> result = new LinkedHashMap<>();
		for (Iterator<Entry<K, V>> it = list.iterator(); it.hasNext();) {
			Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
