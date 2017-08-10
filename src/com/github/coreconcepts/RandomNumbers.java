package com.github.coreconcepts;

import java.util.Random;

public class RandomNumbers {
	public static void main(String[] args) {
		Random random = new Random();
		Integer randomInt = random.nextInt( Integer.MAX_VALUE );
		System.out.println("Random ID : "+ randomInt);
		// Random ID : 2010859352

		pseudoRamdomID();
	}
	/**
	 * Generates type 4 (pseudo randomly generated) UUID. EX:{@code 44271c83-d737-4539-b81b-7443869bd5e4}
	 * The {@code UUID} is generated using a cryptographically strong pseudo
	 * random number generator.
	 */
	public static void pseudoRamdomID() {
		String randomID = java.util.UUID.randomUUID().toString();
		System.out.println("Random type 4 UUID : "+ randomID);
		// Random UUID : 44271c83-d737-4539-b81b-7443869bd5e4
	}
}