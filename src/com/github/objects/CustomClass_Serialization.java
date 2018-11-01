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

public class CustomClass_Serialization {
	static String serFilename = "D:/serializable_CustomClass.ser";
	
	public static void main(String[] args) throws IOException {
		Address add = new Address();
		add.country = "IND";
		
		User obj = new User("SE");
		obj.id = 7;
		obj.address = add;
		
		// Serialization
		objects_serialize(obj, serFilename);
		objects_deserialize(obj, serFilename);
		
		// Externalization
		objects_WriteRead_External(obj, serFilename);
	}
	
	public static void objects_serialize( User obj, String serFilename ) throws IOException{
		FileOutputStream fos = new FileOutputStream( new File( serFilename ) );
		ObjectOutputStream objectOut = new ObjectOutputStream( fos );
		
		// java.io.NotSerializableException: com.github.objects.Address
		objectOut.writeObject( obj );
		objectOut.flush();
		objectOut.close();
		fos.close();
		
		System.out.println("Data Stored in to a file");
	}
	public static void objects_deserialize( User obj, String serFilename ) throws IOException{
		try {
			FileInputStream fis = new FileInputStream( new File( serFilename ) );
			ObjectInputStream ois = new ObjectInputStream( fis );
			Object readObject;
			readObject = ois.readObject();
			String calssName = readObject.getClass().getName();
			System.out.println("Restoring Class Name : "+ calssName); // InvalidClassException
			
			User user = (User) readObject;
			System.out.format("Obj[Id:%d, Role:%s] \n", user.id, user.role);
			
			Address add = (Address) user.address;
			System.out.println("Inner Obj : "+ add.country );
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void objects_WriteRead_External( User obj, String serFilename ) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File( serFilename ));
		ObjectOutputStream objectOut = new ObjectOutputStream( fos );
		
		obj.writeExternal( objectOut );
		objectOut.flush();
		
		fos.close();
		
		System.out.println("Data Stored in to a file");
		
		try {
			// create a new instance and read the assign the contents from stream.
			User user = new User();
			
			FileInputStream fis = new FileInputStream(new File( serFilename ));
			ObjectInputStream ois = new ObjectInputStream( fis );
			
			user.readExternal(ois);
			
			System.out.format("Obj[Id:%d, Role:%s] \n", user.id, user.role);
			
			Address add = (Address) user.address;
			System.out.println("Inner Obj : "+ add.country );
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class Role {
	String role;
}
/*class User extends Role implements Serializable {

	private static final long serialVersionUID = 5081877L;
	Integer id;
	Address address;
	
	public User() {
		System.out.println("Default Constructor get executed.");
	}
	public User( String role ) {
		this.role = role;
		System.out.println("Parametarised Constructor.");
	}
}*/
//java.io.NotSerializableException: com.github.objects.Address
//To participate in serialization process, it must implement either Serializable or Externalizable interface.
class Address implements Serializable {

	private static final long serialVersionUID = 5081877L;
	String country;
}

class User extends Role implements Externalizable {
	
	Integer id;
	Address address;
	// mandatory public no-arg constructor
	public User() {
		System.out.println("Default Constructor get executed.");
	}
	public User( String role ) {
		this.role = role;
		System.out.println("Parametarised Constructor.");
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt( id );
		out.writeUTF( role );
		out.writeObject(address);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.id = in.readInt();
		this.address = (Address) in.readObject();
		this.role = in.readUTF();
	}
}


