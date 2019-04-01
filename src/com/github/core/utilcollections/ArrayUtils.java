package com.github.core.utilcollections;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import com.github.wrappers.String_Interview;

public class ArrayUtils {
	public static void main(String[] args) {
		int[] a = {9, 1, 2}, b = {3, 4, 9};
			// {0, 1, 2}, b = {3, 4, 9};
			//{0, 1, 2, 1}, b = {3, 4, 9};
		
		int[] sum = arraysAddition_java8(a, b); // [3, 5, 11] - [3, 5, 11, 1]
		System.out.println(ArrayFunctions.toString(sum));
		
		int[] sum1 = arraysAddition(a, b);
		System.out.println(ArrayFunctions.toString(sum1));
		
		int[] sumEle = arraysAddition_Int(a, b); // [0, 3, 6, 1] - [0, 0, 4, 7, 0]
		System.out.println(ArrayFunctions.toString(sumEle));
	}
	private static int[] arraysAddition_java8(int[] a, int b[]) {
		int startInclusive = 0, endExclusive = Math.max(a.length, b.length);
		IntUnaryOperator mapper = index -> (index < a.length ? a[index] : 0) + (index < b.length ? b[index] : 0);
		
		return IntStream.range(startInclusive, endExclusive).map(mapper).toArray();
	}
	public static int[] arraysAddition(int[] a1, int[] a2) {
		// a1 array length must be greater than a2 array.
		String stringResult = "";
		int borrowing = 0;
		int[] resultArr = new int[a1.length+1];
		for (int i = a1.length - 1, j = a2.length - 1; i >= 0; i--, j--) {
			int n1 = a1[i];
			int n2 = 0;
			if (j >= 0) {
				n2 = a2[j];
			}
			
			int temp = n1 + n2 + borrowing;
			borrowing = 0; // After adding make it as ZERO.
			
			if (temp > 9) {
				borrowing = 1;
				temp -= 10;
			}
			resultArr[i+1] = temp;
		}
		if (borrowing > 0) {
			resultArr[0] = borrowing;
		}
		stringResult = Arrays.toString(resultArr);
		System.out.format("--- \n [%s + %s]=[%s]\n --- \n", Arrays.toString(a1), Arrays.toString(a2), stringResult);
		return resultArr;
	}
	
	public static int[] arraysAddition_Int(int[] a1, int[] a2) {
		// a1 array length must be greater than a2 array.
		String stringResult = "";
		int borrowing = 0;
		int[] resultArr = new int[a1.length+1];
		for (int i = a1.length - 1, j = a2.length - 1; i >= 0; i--, j--) {
			int n1 = a1[i];
			int n2 = 0;
			if (j >= 0) {
				n2 = a2[j];
			}
			
			int temp = n1 + n2;
			System.out.format("[%d + %d]:[%d]\n", n1, n2, temp);
			if (borrowing > 0) {
				temp += borrowing;
				borrowing = 0;
			}
			if (temp > 9) {
				borrowing = temp / 10; // Tens digit
				temp = temp % 10; // Once digit
				System.out.format("Borrowing:Tempm [%d]:[%d]\n", borrowing, temp);
			}
			resultArr[i+1] = temp;
		}
		if (borrowing > 0) {
			resultArr[0] = borrowing;
		}
		stringResult = Arrays.toString(resultArr);
		System.out.format("[%s + %s]=[%s]\n --- \n", Arrays.toString(a1), Arrays.toString(a2), stringResult);
		return resultArr;
	}
	
	public static String arraysAddition_Char(char[] c1, char[] c2) {
		String stringResult = "";
		int borrowing = 0;
		int[] resultArr = new int[c1.length+1];
		for (int i = c1.length - 1, j = c2.length - 1; i >= 0; i--, j--) {
			int n1 = String_Interview.getNumericValue(c1[i]); // Convert to ascii  value, but i need the exact value.
			int n2 = 0;
			if (j >= 0) {
				n2 = String_Interview.getNumericValue(c2[j]);
			}
			
			int temp = n1 + n2;
			System.out.format("[%d + %d]:[%d]\n", n1, n2, temp);
			if (borrowing > 0) {
				temp += borrowing;
				borrowing = 0;
			}
			if (temp > 9) {
				borrowing = temp / 10; // Tens digit
				temp = temp % 10; // Once digit
				System.out.format("Borrowing:Tempm [%d]:[%d]\n", borrowing, temp);
			}
			resultArr[i+1] = temp;
		}
		if (borrowing > 0) {
			resultArr[0] = borrowing;
		}
		stringResult = Arrays.toString(resultArr);
		System.out.format("[%s + %s]=[%s]\n --- \n", c1.toString(), c2.toString(), stringResult);
		return stringResult;
	}
}
