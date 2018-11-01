package com.github.wrappers;

/**
 * 0.1 + 0.6 = 0.70000005
 * 
 * 
 * @author yashwanth.m
 *
 */
public class DecimalValues {
	public static void main(String[] args) {
		Float d1 = 0.1f, d2 = 0.6f;
		
		Float result = d1 + d2;
		System.out.println("Result : "+ result); // 0.70000005
		
		test2();
		test3();
		test4();
	}
	public static void test2() {
		Float d1 = 0.2f, d2 = 0.63f;
		
		Float result = d1 + d2;
		System.out.println("Result : "+ result); // 0.83
	}
	public static void test3() {
		Float d1 = 0.1f, d2 = 0.5f;
		
		Float result = d1 + d2;
		System.out.println("Result : "+ result); // 0.6
	}
	public static void test4() {
		Float d1 = 0.6f, d2 = 0.6f;
		
		Float result = d1 + d2;
		System.out.println("Result : "+ result); // 1.2
	}
	public static void test5() {
		Float d1 = 0.1f, d2 = 0.1f;
		
		Float result = d1 + d2;
		System.out.println("Result : "+ result); // 0.2
	}
}
