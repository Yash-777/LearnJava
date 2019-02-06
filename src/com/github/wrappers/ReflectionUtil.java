package com.github.wrappers;

import java.lang.reflect.Field;


public class ReflectionUtil {
	/**
	 * Number abstract class has functions for byte,short,int,long,float,double.
	 * All these members data Represent directly in the memory(@Native).
	 * static final Class<Integer>  TYPE = (Class<>) Class.getPrimitiveClass("int");
	 */
	public static Field getIntegerField() throws NoSuchFieldException, SecurityException {
		// private final int value; Get this variable and update with new value.
		String fieldName = "value";
		Field field = Integer.class.getDeclaredField( fieldName );
		field.setAccessible(true);
		return field;
	}
	/**
	 * As it represents class data using hash code to find in Memory.
	 * public String(String original) {
	 *   this.value = original.value;
	 *   this.hash = original.hash;
	 * }
	 */
	public static Field getStringField() throws NoSuchFieldException, SecurityException {
		// private final char value[]; Get this variable and update with new value.
		String fieldName = "value";
		Field field = String.class.getDeclaredField( fieldName );
		field.setAccessible(true);
		return field;
	}
	
	public static void mutableString(String from, String to) throws Exception {
		// https://docs.oracle.com/javase/tutorial/reflect/member/fieldTrouble.html
		Field field = getStringField();
		char[] newValue = to.toCharArray();
		field.set(from, newValue);
	}
	public static void mutableInteger(Integer from, Integer to) throws Exception {
		Field field = getIntegerField();
		Integer object = (Integer) field.get(from);
		field.set(object, to);
	}
	public static void mutableChar(String from, char to) throws Exception {
		Field field = getStringField();
		char[] oldValue = (char[]) field.get(from);
		oldValue[0] = to;
	}
}
