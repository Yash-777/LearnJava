package com.github.coreconcepts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Externalizable provides us writeExternal() and readExternal() method 
 * which gives us flexibility to control java serialization mechanism instead of relying on Java's default serialization. 
 * Correct implementation of Externalizable interface can improve performance of application drastically.

 * @author yashwanth.m
 *
 */

public class ExternisableDemo {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person person = new Person();
		person.setId(1);
		person.setName("Yash");
		serialization_TXT(person);
		de_serialization_EX();
	}
	public static void serialization_TXT(Object o) throws IOException{	
		
		FileOutputStream fileOut = new FileOutputStream(new File("ExternisableDemo.txt"));
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(o); // To store any object via serialization mechanism we call readoject() / writeObect()
		out.close();
		fileOut.close();
		System.out.println("Data Stored in ExternisableDemo.txt file");
	}
	public static void de_serialization_EX() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		FileInputStream fileIn = new FileInputStream(new File("ExternisableDemo.txt"));
		ObjectInputStream oin = new ObjectInputStream(fileIn);
		Object o =  oin.readObject();
		String className = o.getClass().getName();
		System.out.println("Class Name : "+className);
		Person p = (Person) o;
		System.out.println(p);
		oin.close();
		fileIn.close();
	}
}