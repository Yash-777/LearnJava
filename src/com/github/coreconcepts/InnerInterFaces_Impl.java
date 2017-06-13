package com.github.coreconcepts;

public class InnerInterFaces_Impl implements InnerInterFaces.Inner_InnerInterFace {
	
	public static void main(String[] args) {
		new InnerInterFaces_Impl().abstractMethod11();
	}
	
	@Override
	public void abstractMethod11() {
		System.out.println("InnerInterFaces Body.");
	}
}