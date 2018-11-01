package com.github.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * https://archive.apache.org/dist/jmeter/
 * 
 * <UL> Constant Scheduler JMX Files « https://jmeter.apache.org/demos/
 * <LI> LoopTestPlan.jmx </LI>
 * <LI> AuthManagerTestPlan.jmx </LI>
 * <LI> JDBC-Pre-Post-Processor.jmx </LI>
 * </UL>
 * 
 * @author yashwanth.m
 *
 */
public class XML_JMeterFromExistingJMX {
	
	public static void main(String[] args) {
		
		System.out.println("\n ===== JMX ===== \n");
		identifyJMX_Type();
	}
	public static void identifyJMX_Type() {
		// https://gerardnico.com/wiki/jmeter/test_plan
		// http://jmeter.apache.org/usermanual/get-started.html
		// https://www.blazemeter.com/blog/5-ways-launch-jmeter-test-without-using-jmeter-gui
		try {
			File jmxFile = new File("./LoopTestPlan.jmx");
			FileInputStream in = new FileInputStream( jmxFile );
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
			Document doc = builder.parse( in );
			String threadGroupExp = "//ThreadGroup";
			NodeList threadGroupList = (NodeList)xpath.compile(threadGroupExp).evaluate(doc, XPathConstants.NODESET);
			
			String stepGroupExp = "//kg.apc.jmeter.threads.SteppingThreadGroup";
			NodeList stepGroupList = (NodeList)xpath.compile(stepGroupExp).evaluate(doc, XPathConstants.NODESET);
			
			String ultimateGroupExp = "//kg.apc.jmeter.threads.UltimateThreadGroup";
			NodeList ultimateGroupList = (NodeList)xpath.compile(ultimateGroupExp).evaluate(doc, XPathConstants.NODESET);
			
			String childHashTree = "//jmeterTestPlan/hashTree/hashTree";
			NodeList nodeList4 = (NodeList)xpath.compile(childHashTree).evaluate(doc, XPathConstants.NODESET);

			if (threadGroupList.getLength() > 0) {
				System.out.println("ThreadGroupGui - "+ threadGroupList.getLength());
			} else if (stepGroupList.getLength() > 0) {
				System.out.println("SteppingThreadGroupGui - "+ stepGroupList.getLength());
			} else if (ultimateGroupList.getLength() > 0) {
				System.out.println("UltimateThreadGroupGui - "+ ultimateGroupList.getLength());
			} else {
				System.out.println("Non of the Above are matched, in provided JMX File \n"+ nodeList4.item(0));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}