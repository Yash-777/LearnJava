package com.github.core.utilcollections;

import java.util.ArrayList;
import java.util.Collections;

class Emp implements Comparable<Emp>{
	int id, age;
	String name;
	
	Emp(int id, String names, int age) {
		this.id = id;
		name = names;
		this.age = age;
	}

	@Override
	public int compareTo(Emp e) {
		if( id > e.id ) {
			return 1;
		} else if ( id < e.id ) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "["+id+", "+name+", "+age+" ]";
	}
}
public class ArrayList_Comparable_EmpID {
	public static void main(String[] args) {
		ArrayList<Emp> al = new ArrayList<>();
		al.add( new Emp(10, "John", 36) );
		al.add( new Emp(7, "Yash", 24) );
		al.add( new Emp(10, "Sam", 26) );
		al.add( new Emp(14, "Raju", 25) );
		
		System.out.println("List of Obejcts : \n"+ al);
		
		Collections.sort(al);
		System.out.println("List of Object after Sort :\n"+ al);
	}
}
