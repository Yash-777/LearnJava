package com.github.files;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * https://stackoverflow.com/a/12273296/5081877
 * @author yashwanth.m
 *
 */
public class ReadAndPrintXMLFileWithSTAX {
	public static void main(String argv[]) throws Exception {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in = new FileInputStream("books.xml");
		XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
		streamReader.nextTag(); // Advance to "book" element
		streamReader.nextTag(); // Advance to "person" element
		
		int persons = 0;
		while (streamReader.hasNext()) {
			if (streamReader.isStartElement()) {
				switch (streamReader.getLocalName()) {
					case "first": {
						System.out.println("First Name : "+streamReader.getElementText());
						break;
					}
					case "last": {
						System.out.println("Last Name : "+streamReader.getElementText());
						break;
					}
					case "age": {
						System.out.println("Age : "+streamReader.getElementText());
						break;
					}
					case "person" : { persons ++; }
				}
			}
			streamReader.next();
		}
		System.out.println("Persons Count : "+persons);
	}
}
