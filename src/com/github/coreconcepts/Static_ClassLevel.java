package com.github.coreconcepts;
/*
 * OuterClass.StaticNestedClass nestedObject = new OuterClass.StaticNestedClass();
 */
public class Static_ClassLevel {
	public static int a = 10;
	static class Static_Member_Class {
		public int siv = 101;
		public static int ssv = 11;
		public static int a = 1;
		
		public static void sm(){ System.out.println("Static static class"); }
		public void im() { System.out.println("static instance class"); }
	}
	public static void main(String[] args) {
		Static_ClassLevel.Static_Member_Class inner_Obj = new Static_ClassLevel.Static_Member_Class();
		inner_Obj.im();
		
		Static_ClassLevel.Static_Member_Class.sm();
		System.out.println("variable : "+a);
		System.out.println("variable : "+Static_Member_Class.a);
		
	}
}
