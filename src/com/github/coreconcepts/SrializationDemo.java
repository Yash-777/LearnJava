package com.github.coreconcepts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** http://stackoverflow.com/questions/447898/what-is-object-serialization/31651945#31651945
 * 
 * Object-Serialization is process of converting the state of an object into steam of bytes(so that Serializable-objects cannot  read and understood by humans),
 * Object_DeSerialization is the process of getting the state of an object an store it into object.
 * (before storing it check serialVersionUID form file/network and .class file serialVersionUID is same. if not throw java.io.InvalidClassException,) 
 * 
 *  On providing implementation of serializable interface(marker interface) we are providing information to compiler to use
 * Java Serialization mechanism to serialize this object.
 * 
 * A Java object is serializable. if its class or any of its superclasses implements either the java.io.Serializable interface 
 * or its subinterface, java.io.Externalizable.
 * 
 * Object Serialization in Java is a process used to convert Object into a binary format which can be persisted into disk or 
 * sent over network to any other running Java virtual machine; the reverse process of creating object from binary stream is 
 * called deserialization in Java.

 * @author yashwanth.m
 *
 */


class Employee implements Serializable { 
	private static final long serialVersionUID = 2L;
	int eno; 
	String name;
	
	static int id; // static information belongs to Entire-Class not for a particular-object. but we can access through object.
	
	int newlyaddedafeter = 10;
	
	transient String password; //  sensitive information 
	
	private String classlevel;

	public String getClasslevel() {		return classlevel;	}
	public void setClasslevel(String classlevel) {		this.classlevel = classlevel;	}
}

public class SrializationDemo {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Employee e = new Employee();
		e.eno = 12;
		e.name = "Yash";
		
		e.password = "confidential";
		Employee.id = 19;
		e.setClasslevel("Class Spesific data");
		serialization_TXT(e); // Serializable objects cannot  read and understood by humans.
		de_serialization();
	}
	public static void serialization_TXT(Object o) throws IOException{
		
		FileOutputStream fileOut = new FileOutputStream(new File("SerializationOut.txt"));
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(o); // To store any object via serialization mechanism we call readoject() / writeObect()
		out.close();
		fileOut.close();
		System.out.println("Data Stored in SerializationOut.txt file");
	}
	@SuppressWarnings("resource")
	public static void de_serialization() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		FileInputStream fileIn = new FileInputStream(new File("SerializationOut.txt"));
		
		Object o = new ObjectInputStream(fileIn).readObject();
		
		//Employee e = (Employee) Class.forName(o.getClass().getName()).newInstance(); // creating new object.
		
		o.getClass().getName();
		Employee e = (Employee) o; // then type cast to that Correct-Class-type. So that we can get data and store it into this current object.
		// java.io.InvalidClassException: core.Employee; local class incompatible: stream class desc serialVersionUID = 1, local class serialVersionUID = 2
		System.out.println(e.eno + "\t" +e.name +"\t" + e.password + "\t"  + Employee.id +"\n"+ e.getClasslevel());
	}
}