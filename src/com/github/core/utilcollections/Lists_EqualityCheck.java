package com.github.core.utilcollections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

/**
 * https://stackoverflow.com/q/13501142/5081877
 * 
 * @author yashwanth.m
 *
 */
public class Lists_EqualityCheck {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> list_null = null;
		List<String> list_empty = Arrays.asList();
		List<String> list_empty2 = Arrays.asList();
		List<String> list1 = Arrays.asList("a", "b", "b", "c", "c");
		List<String> list2 = Arrays.asList("b", "c", "a", "c", "b");
		List<String> list22 = Arrays.asList("b", "c", "a", "c", "c");
		List<String> list3 = Arrays.asList("a", "", "b", "c", "c");
		List<String> list4 = Arrays.asList("a", null, "b", "c", "c");
		
		//System.out.println(com.amazonaws.util.CollectionUtils.mergeLists(list_null, list_empty)); // true
		System.out.println( haveSameElements(list2, list22));
		
		// http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/CollectionUtils.html
		boolean equalCollection = CollectionUtils.isEqualCollection(list3, list4);
		System.out.println("Apache Commons Collections : "+ equalCollection);
		
		// http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/ListUtils.html
		boolean equalList = ListUtils.isEqualList(list1, list2);
		System.out.println("Apache Commons List Collections : "+ equalList);
		
		//listElementsEquality_8(list2, list22);
	}
	
	/**
	 * Apache Commons Collections to the rescue once again:
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean haveSameElements(final List<String> list1, final List<String> list2) {
		// (list1, list1) is always true as the references are equal.
		if (list1 == list2) return true;
		
		// If either of the list is null or empty and if their lengths are not equal, they can't possibly match.
		if (list1 == null || list2 == null || list1.isEmpty() || list2.isEmpty() || list1.size() != list2.size()) {
			return false;
		} else {
			if (list1.containsAll(list2) && list2.containsAll( list1 )) {
				if ( list1.equals(list2) ) {
					System.out.println("DDD");
					return true;
				}
				System.out.println("Duplicates are not repeated as Same no of time over both the lists.");
			}
			return false;
			
			/*boolean equalCollection = org.apache.commons.collections.CollectionUtils.isEqualCollection(list1, list2);
			System.out.println("Apache Commons Collections : "+ equalCollection);
			return equalCollection;*/
		}
	}
	
	public static List<String> listElementsEquality_8(List<String> l1, List<String> l2) {
		List<String> ret = l1.stream()
				.filter(o -> !l2.contains(o))
				.collect(Collectors.toList());
		System.out.println( ret );
		return ret;
	}
}
