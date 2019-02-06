package com.github.java8;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

/**
 * https://gist.github.com/maxmalysh/a991bbe4a923539f19fb
 * 
 * @author yashwanth.m
 *
 */
public class StringStreamPerfromace_OverRegex {

	public interface NumericChecker {
		boolean isNumeric(String string);
	}

	public static class TestCase {
		public String string;

		public TestCase(String string) {
			this.string = string;
		}

		long runWith(NumericChecker checker) {
			long startTime = System.nanoTime();
			checker.isNumeric(string);
			long stopTime  = System.nanoTime();
			return (stopTime - startTime) / 1000000;
		}

	}

	public static NumericChecker regexChecker = new NumericChecker() {
		private String regex = "\\d+";

		@Override
		public boolean isNumeric(String string) {
			return string.matches(regex);
		}
	};

	public static NumericChecker lamdaChecker = new NumericChecker() {
		@Override
		public boolean isNumeric(String string) {
			return string.chars().allMatch(x -> Character.isDigit(x));
		}
	};

	public static NumericChecker parallelChecker = new NumericChecker() {
		@Override
		public boolean isNumeric(String string) {
			return string.chars().parallel().allMatch(x -> Character.isDigit(x));
		}
	};

	public static void main(String[] args) {
		int[] size = {500000, 5000000, 50000000, 100000000};
		List<TestCase> tests = new ArrayList<>();

		// Pure numbers
		for (int N : size) {
			String string = RandomStringUtils.randomNumeric(N);
			tests.add(new TestCase(string));
		}

		// Not a number, adding just for the sake of diversity
		String string = new StringBuilder().append(RandomStringUtils.randomNumeric(50000000))
										   .append("foobar")
										   .append(RandomStringUtils.randomNumeric(50000000))
										   .toString();
		tests.add(new TestCase(string));

		// Warm-up JIT compiler
		for (TestCase test : tests.subList(0, 1)) {
			test.runWith(regexChecker);
			test.runWith(lamdaChecker);
			test.runWith(parallelChecker);
		}

		for (TestCase test : tests) {
			long runTimeRegex = test.runWith(regexChecker);
			long runTimeLambda = test.runWith(lamdaChecker);
			long runTimeParallel = test.runWith(parallelChecker);

			System.out.printf("%6d ms\n", runTimeRegex);
			System.out.printf("%6d ms\n", runTimeLambda);
			System.out.printf("%6d ms\n\n", runTimeParallel);
		}
	}

	}