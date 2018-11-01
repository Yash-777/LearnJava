package com.github.objects;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/** http://stackoverflow.com/questions/447898/what-is-object-serialization
 * 
 * @author yashwanth.m
 *
 */


class Employee implements Serializable {
	private static final long serialVersionUID = 2L;
	static int id;
	
	int eno; 
	String name;
	transient String password; // Using transient keyword means its not going to be Serialized.
}
class Emp implements Externalizable {
	int eno; 
	String name;
	transient String password; // No use of transient, we need to take care of write and read.
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(eno);
		out.writeUTF(name);
		//out.writeUTF(password);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.eno = in.readInt();
		this.name = in.readUTF();
		//this.password = in.readUTF(); // java.io.EOFException
	}
}

public class SerializationDemo {
	static String fileName = "D:/serializable_file.ser";
			
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Employee emp = new Employee( );
		Employee.id = 1; // Can not Serialize Class data.
		emp.eno = 77;
		emp.name = "Yash_777";
		emp.password = "confidential";
		objects_WriteRead(emp, fileName);
		
		Emp e = new Emp( );
		e.eno = 77;
		e.name = "Yash";
		e.password = "confidential";
		objects_WriteRead_External(e, fileName);
		
		/*String stubHost = "127.0.0.1";
		Integer anyFreePort = 7777;
		socketRead(anyFreePort); //Thread1
		socketWrite(emp, stubHost, anyFreePort); //Thread2*/
	}
	/**
	 * Creates a stream socket and connects it to the specified port number on the named host. 
	 */
	public static void socketWrite(Employee objectToSend, String stubHost, Integer anyFreePort) {
		try { // CLIENT - Stub[marshalling]
			Socket client = new Socket(stubHost, anyFreePort);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(objectToSend);
			out.flush();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Creates a server socket, bound to the specified port. 
	public static void socketRead(  Integer anyFreePort ) {
		try { // SERVER - Stub[unmarshalling ]
			ServerSocket serverSocket = new ServerSocket( anyFreePort );
			System.out.println("Server serves on port and waiting for a client to communicate");
				/*System.in.read();
				System.in.read();*/
			
			Socket socket = serverSocket.accept();
			System.out.println("Client request to communicate on port server accepts it.");
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Employee objectReceived = (Employee) in.readObject();
			System.out.println("Server Obj : "+ objectReceived.name );
			
			socket.close();
			serverSocket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void objects_WriteRead( Employee obj, String serFilename ) throws IOException{
		FileOutputStream fos = new FileOutputStream( new File( serFilename ) );
		ObjectOutputStream objectOut = new ObjectOutputStream( fos );
		objectOut.writeObject( obj );
		objectOut.close();
		fos.close();
		
		System.out.println("Data Stored in to a file");
		
		try {
			FileInputStream fis = new FileInputStream( new File( serFilename ) );
			ObjectInputStream ois = new ObjectInputStream( fis );
			Object readObject;
			readObject = ois.readObject();
			String calssName = readObject.getClass().getName();
			System.out.println("Restoring Class Name : "+ calssName); // InvalidClassException
			
			Employee emp = (Employee) readObject;
			System.out.format("Obj[No:%s, Name:%s, Pass:%s]", emp.eno, emp.name, emp.password);
			
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void objects_WriteRead_External( Emp obj, String serFilename ) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File( serFilename ));
		ObjectOutputStream objectOut = new ObjectOutputStream( fos );
		
		obj.writeExternal( objectOut );
		objectOut.flush();
		
		fos.close();
		
		System.out.println("Data Stored in to a file");
		
		try {
			// create a new instance and read the assign the contents from stream.
			Emp emp = new Emp();
			
			FileInputStream fis = new FileInputStream(new File( serFilename ));
			ObjectInputStream ois = new ObjectInputStream( fis );
			
			emp.readExternal(ois);
			
			System.out.format("Obj[No:%s, Name:%s, Pass:%s]", emp.eno, emp.name, emp.password);
			
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}