package com.github.singleObjects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yashwanth.m
 *
 */
public class ThreadsAccessing_SingleTon {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Parametarised_SingleTon instance2 = Parametarised_SingleTon.getInstance();
		instance2.myMethod();
		System.out.println("Hash Code :"+instance2.hashCode());
		System.out.println("Hash Code :"+ System.identityHashCode(instance2));
		
		Enum_SingleTon.INSTANCE.instanceMethod();
		System.out.println("Enum :"+ System.identityHashCode( Enum_SingleTon.INSTANCE ));
		
		Default_SingleTon instance = Default_SingleTon.getInstance();
		instance.myMethod();
		System.out.println("Hash Code :"+instance.hashCode());
		System.out.println("Hash Code :"+ System.identityHashCode(instance));
		
		Enum_SingleTon.INSTANCE.instanceMethod();
		System.out.println("Enum :"+ System.identityHashCode( Enum_SingleTon.INSTANCE ));
	}
	public static void defaultConstructor() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Default_SingleTon instance2 = Default_SingleTon.getInstance();
		instance2.myMethod();
		System.out.println("Hash Code :"+instance2.hashCode());
		
		// reflection concept to get constructor with no parameters [Default Constructor]
		Constructor<Default_SingleTon> constructor = Default_SingleTon.class.getDeclaredConstructor( new Class<?>[0] );
		constructor.setAccessible(true); // force the constructor to be accessible out side the class.
		Default_SingleTon foo = constructor.newInstance( new Object[0] );
		constructor.setAccessible(false); //close the accessibility of a constructor.
		foo.myMethod();
		System.out.println( foo.hashCode() );
		
		Default_SingleTon instance = Default_SingleTon.getInstance();
		instance.myMethod();
		System.out.println("Hash Code :"+instance.hashCode());
	}
	public static void paramConstrctor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		// Default Constructor
		Class<?> parameterTypes[] = new Class<?>[1];
		parameterTypes[0] = Integer.TYPE;
		Object arglist[] = new Object[1];
		arglist[0] = new Integer(37);
		
		@SuppressWarnings("unchecked")
		Constructor<Parametarised_SingleTon> constructor 
			= (Constructor<Parametarised_SingleTon>) Parametarised_SingleTon.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Parametarised_SingleTon foo = constructor.newInstance( arglist );
		// close the accessibility of a constructor.
		constructor.setAccessible(false);
		foo.myMethod();
		System.out.println( foo.hashCode() );
	}
}
