package com.github.coreconcepts.programs;

public class DrawPatterns {
	
	public static void main(String[] args) {
		int steps = 5;
		boolean useSaperator = true;
		
		pyramid(steps, useSaperator);
		pyramid_Left(steps, useSaperator, false);
		pyramid_Left(steps, useSaperator, true);
	}
	public static void pyramid(int steps, boolean useSaperator) {
		String saperator = " ";
		for (int i = 1; i <= steps; i++) {
			// For Spaces till i < steps
			for (int j = i; j < steps; j++) {
				print(" ");
				if (useSaperator && saperator.length() > 0) {
					print(saperator);
				}
			}
			// Left
			for (int j = i; j > 0; j--) {
				print(i);
				if (useSaperator && j - 1 > 0) {
					print(saperator);
				}
			}
			// Right - After completing Left, Avoid center element So, (i - 1)
			for (int k = i - 1; k > 0; k--) {
				if (useSaperator) {
					print(saperator);
				}
				print(i);
			}
			print("\n");
		}
	}
	// JS - https://stackoverflow.com/questions/52457726/inclined-pyramid-pattern
	// J  - https://stackoverflow.com/a/54518616/5081877
	public static void pyramid_Left(int steps, boolean useSaperator, boolean isTriangle) {
		String saperator = " * ";
		for (int i = 1; i <= steps; i++) {
			// Forward
			for (int j = 1; j <= i; j++) {
				print(i);
				if (useSaperator && j+1 <= i) {
					print(saperator);
				}
			}
			print("\n");
			if (i == steps && !isTriangle) {
				// Backward - After completing Forward
				int k = i - 1;
				while (k > 0) {
					for (int j = k; j > 0; j--) {
						print(k);
						if (useSaperator && j-1 > 0) {
							print(saperator);
						}
					}
					k--;
					print("\n");
				}
			}
		}
	}
	public static void print(Object o) {
		System.out.print(o);
	}
}
/* 
 * Left
 * 1
 * 2 * 2
 * 3 * 3 * 3
 * 2 * 2
 * 1
 * 
 * Top
 *     1
 *   2 * 2
 * 3 * 3 * 3
 */
