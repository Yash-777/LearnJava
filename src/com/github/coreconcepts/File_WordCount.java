package com.github.coreconcepts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class File_WordCount {
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileInputStream(new File("./lib/RequiredJarFiles.txt")));
		int count = 0;
		while (sc.hasNext()) {
			String[] s = sc.next().split("d*[.@:=#-]");	
			
			for (int i = 0; i < s.length; i++) {
				if (!s[i].isEmpty()){
					System.out.println(s[i]);
					count++;
				}
			}
		}
		System.out.println("Word-Count : "+count);
	}
}