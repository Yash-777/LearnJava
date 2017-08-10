package com.github.coreconcepts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * https://stackoverflow.com/a/31244596/5081877
 * @author yashwanth.m
 *
 */
public class Comparable_Comparator implements Comparable<Comparable_Comparator>{
	
	private int id, age;
	private String name;
	private long salary;
	
	public static void main(String[] args) {

		Comparable_Comparator e1 = new Comparable_Comparator(5, "Yash", 22, 1000);
		Comparable_Comparator e2 = new Comparable_Comparator(8, "Tharun", 24, 25000);
		Comparable_Comparator e3 = new Comparable_Comparator(1, "Sam", 21, 25000);
		Comparable_Comparator e4 = new Comparable_Comparator(7, "Raju", 25, 25000);
		List<Comparable_Comparator> list = new ArrayList<Comparable_Comparator>();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		Collections.sort(list); // call @compareTo(o1)
		System.out.println("Comparable : CompareTo(O1) \n\t"+list.toString());
		Collections.sort(list, Comparable_Comparator.NameComparator); // call @compare (o1,o2)
		System.out.println("Comparator : compare(O1, O2) \n\t"+list.toString());
		//Collections.sort(list, Comparable_Comparator.idComparator); // call @compare (o1,o2)
	}
	
	public static Comparator<Comparable_Comparator> NameComparator = new Comparator<Comparable_Comparator>() {
		@Override
		public int compare(Comparable_Comparator e1, Comparable_Comparator e2) {
			return e1.getName().compareTo(e2.getName());
		}
	};
	
	public static Comparator<Comparable_Comparator> idComparator = new Comparator<Comparable_Comparator>() {
		@Override
		public int compare(Comparable_Comparator e1, Comparable_Comparator e2) {
			return Integer.valueOf(e1.getId()).compareTo(Integer.valueOf(e2.getId()));
		}
	};
	
	@Override
	public int compareTo(Comparable_Comparator e) {
		//return Integer.valueOf(this.id).compareTo(Integer.valueOf(e.id));
		//return Character.toString(this.name.charAt(0)).compareToIgnoreCase(Character.toString(e.name.charAt(0)));
		if (this.id > e.id) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public Comparable_Comparator() { }
	public Comparable_Comparator(int id, String name, int age, long salary){
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) { 
		this.salary = salary;
	}

	public static Comparator<Comparable_Comparator> getNameComparator() {
		return NameComparator;
	}
	public static void setNameComparator(Comparator<Comparable_Comparator> nameComparator) {
		NameComparator = nameComparator;
	}

	public static Comparator<Comparable_Comparator> getIdComparator() {
		return idComparator;
	}
	public static void setIdComparator(Comparator<Comparable_Comparator> idComparator) {
		Comparable_Comparator.idComparator = idComparator;
	}

	@Override
	public String toString() {
		return "Comparable_Comparator [id=" + id + ", age=" + age + ", name=" + name + ", salary=" + salary + "]";
	}
}