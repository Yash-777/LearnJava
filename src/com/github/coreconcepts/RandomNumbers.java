package com.github.coreconcepts;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

/**
 * https://stackoverflow.com/q/41107/5081877
 * 
 * @author yashwanth.m
 *
 */
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
		String randomString = java.util.UUID.randomUUID().toString();
		System.out.println("Random type 4 UUID : "+ randomString);
		// Random UUID : 44271c83-d737-4539-b81b-7443869bd5e4
		
		// org.apache.commons.lang3.RandomStringUtils
		String randomAlphanumeric = RandomStringUtils.randomAlphanumeric( 12 ); // any number
		System.out.println("commons.lang3 : "+randomAlphanumeric); // 12 « O8CmFpoIURHx
		
		// https://mvnrepository.com/artifact/org.apache.commons/commons-text - java8 version
		RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
			.withinRange('0', 'z')
			.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
			.build();
		String generate = randomStringGenerator.generate(12); // toUpperCase() if you want
		System.out.println("commons-text : "+generate); // SV1dSGKJIu2N
	}
}