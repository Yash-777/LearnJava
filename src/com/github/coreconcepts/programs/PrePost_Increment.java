package com.github.coreconcepts.programs;

public class PrePost_Increment {
	
	static int staticVar = 10;
	
	public static void main(String[] cmdLineArgs) {
		int a = 80, b = 12;
		
		System.out.println("Post Increment : " + b++); // 12
		int sum = ++a * a / 100 + b++; // ++(a * a / 100) + b++
		System.out.println("Sum : "+ sum); // 78
		
		boolean equalsData = a == b;
		System.out.println("Expression to Var : "+ equalsData); // false
		
		String format = "Printf( %s )", args = "100";
		System.out.printf(format, args);
		
		System.out.println("\n Sum of digits : ");
		int c = 43;
		float f = 10f;
		double d = 10;
		System.out.println( c + f + d );
		
		int instanceVar = 20;
		new PrePost_Increment().incrementVar( instanceVar );
		System.out.println("instanceVar : "+ instanceVar);
		System.out.println("SUM : "+ instanceVar+staticVar);
	}
	
	public void incrementVar(int a) {
		a++;
		staticVar++;
		System.out.println("Pass By Value : "+ a);
	}
}
