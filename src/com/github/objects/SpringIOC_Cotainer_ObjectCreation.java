package com.github.objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SpringBean {
	int id;
	String name;
	
	public SpringBean() {
		System.out.println("Loading the class form the class metadata.");
	}
	public SpringBean(int id, String name) {
		this.id = id;
		this.name = name;
		System.out.println("Constructor injection...");
	}
	
	// @Autowiring
	public void setId(int id) {
		this.id = id;
		System.out.println("Setter injection...");
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[ "+this.id+", '"+this.name+"' ]";
	}
}

public class SpringIOC_Cotainer_ObjectCreation {
	public static void main(String[] args) throws ClassNotFoundException {
		// https://stackoverflow.com/a/5658199/5081877
		Class<?> beanClass = Class.forName("com.github.objects.SpringBean");
		
		// https://docs.oracle.com/javase/tutorial/reflect/member/index.html
		getClassMembers(beanClass);
		/*
<bean id="beanId_setter" class="com.github.objects.SpringBean">
    <property name="id" value="777" />
    <property name="id" value="Yash" />
</bean>

<bean id="beanId_constructor" class="com.github.objects.SpringBean">
    <constructor-arg name="id" value="8787"/>
    <constructor-arg name="id" value="Yash"/>
</bean>
		 */
		setterInjection(beanClass, 777, "Yash");
		constructorInjection(beanClass, 8787, "Yash");
	}
	public static void getClassMembers( Class<?> beanClass ) {
		Constructor<?>[] allConstructors = beanClass.getDeclaredConstructors(); // getConstructors();
		for (Constructor<?> ctor : allConstructors) {
			System.out.println(" : "+ ctor);
		}
		
		Method[] allMethods = beanClass.getDeclaredMethods(); // getMethods()
		for (Method m : allMethods) {
			String mname = m.getName();
			System.out.println(" : "+ mname);
		}
	}
	public static void setterInjection( Class<?> beanClass, int id, String name ) {
		try {
			SpringBean beanId_setter = (SpringBean) beanClass.newInstance();
			beanId_setter.setId( id ); // Setting the bean properties.
			beanId_setter.setName( name );
			
			System.out.println("setterInjection : Bean created with object value : "+ beanId_setter.toString() );
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static void constructorInjection( Class<?> beanClass, int id, String name ) {
		try {
			Constructor<?> beanConstructor = beanClass.getConstructor(Integer.TYPE, String.class);
			SpringBean beanId_constructor = (SpringBean) beanConstructor.newInstance(id, name);
			System.out.println("constructorInjection : Bean created with object value : "+ beanId_constructor.toString() );
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
