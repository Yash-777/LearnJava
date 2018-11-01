package com.github.coreconcepts;

public class InterviewLogics {
	public static void main(String[] args) {
		// Sum of digits between the range 100 to 1000 is 3.
		for (int i = 100; i < 1000; i++) {
			sumOfDigits(i);
		}
	}
	static boolean sumOfDigits( int num ) {
		int sum = 0, temp = num;
		while( num > 0 ) {
			sum += num % 10;
			num = num / 10;
		}
		if( sum == 3 ) {
			System.out.println("Sum of Digits is 3 : "+ temp);
			return true;
		}
		return false;
	}
}
