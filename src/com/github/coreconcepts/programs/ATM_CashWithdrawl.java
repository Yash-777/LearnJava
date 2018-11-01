package com.github.coreconcepts.programs;

public class ATM_CashWithdrawl {
	public static void main(String[] args) {
		int enteredAmount = 976;
		
		if( enteredAmount / 500 > 0 ) {
			int fiveHundreds = enteredAmount / 500;
			enteredAmount -= fiveHundreds * 500;
			System.out.println(fiveHundreds+" : 500");
		}
		if( enteredAmount / 100 > 0 ) {
			System.out.println(enteredAmount / 100+" : 100");
			enteredAmount %= 100;
		}
		if( enteredAmount / 50 > 0 ) {
			System.out.println(enteredAmount / 50+" : 50");
			enteredAmount %= 50;
		}
		if( enteredAmount / 10 > 0 ) {
			System.out.println(enteredAmount / 10+" : 10");
			enteredAmount %= 10;
		}
		if( enteredAmount > 0 ) {
			System.out.println("Remaining Change : "+enteredAmount );
		}
	}
}
