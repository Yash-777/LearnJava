package com.github.java8;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * java-8-lambda-comparator « https://stackoverflow.com/q/44225896/5081877
 * 
 * @author yashwanth.m
 *
 */
public class Lambda_Long_Comparator {
	public static void main(String[] args) {
		
		List<Employee> java7 = getEmployees();
		
		// Sort with Inner Class
		// anonymous class implements the Comparator interface
		Comparator<Employee> timeCompare = new Comparator<Employee>() {
			@Override public int compare(Employee e1, Employee e2) {
				return e1.getCreationTime().compareTo( e2.getCreationTime() );
			}
		};
		
		// Collections.sort(list); // Defaults to Comparable<T> « @compareTo(o1)
		Collections.sort(java7, timeCompare); // Comparator<T> « @compare (o1,o2)
		System.out.println("Java < 8 \n"+ java7);
		
		List<Employee> java8 = getEmployees();
		
		// Default methods can be used to chain multiple functions together (compose, andThen).
		/*Comparator<Employee> timeCompareLambda = (o1, o2) -> (int) ( o1.getCreationTime() - o2.getCreationTime());
		Collections.sort(java8, timeCompareLambda );*/
		
		// A comparator that compares by an extracted key. Pass references using :: keyword. 
		// static <T> Comparator<T> comparingLong(ToLongFunction<? super T> keyExtractor)
		/*ToLongFunction<Employee> keyExtracor = Employee::getCreationTime;
		Comparator<Employee> byTime = Comparator.comparingLong( Employee::getCreationTime );*/
		
		// Basic Sort with Lambda Support
		/*Comparator<Employee> functional_semantics = (e1, e2) -> {
			return e1.getCreationTime().compareTo( e2.getCreationTime() );
		};
		Collections.sort(java8, functional_semantics);*/
		
		Collections.sort(java8, Comparator
				.comparing( Employee::getCreationTime )
				.thenComparing( Employee::getName ));
		//java8.forEach((developer)->System.out.println(developer));
		System.out.println("Java 8 \n"+java8);
	}
	
	static List<Employee> getEmployees() {
		Date date = Calendar.getInstance().getTime();
		List<Employee> list = new ArrayList<Employee>();
		list.add( new Employee(4, "Yash", date.getTime()+7));
		list.add( new Employee(2, "Raju", date.getTime()+1));
		list.add( new Employee(4, "Yas", date.getTime()));
		list.add( new Employee(7, "Sam", date.getTime()-4));
		list.add( new Employee(8, "John", date.getTime()));
		return list;
	}
}
class Employee implements Comparable<Employee> {
	Integer id;
	String name;
	Long creationTime;
	
	public Employee(Integer id, String name, Long creationTime) {
		this.id = id;
		this.name = name;
		this.creationTime = creationTime;
	}
	
	@Override public int compareTo(Employee e) {
		return this.id.compareTo(e.id);
	}
	
	@Override public String toString() {
		return "\n["+this.id+","+this.name+","+this.creationTime+"]";
	}
	
	// Other getter and setter methods
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
}