package com.github.files;

import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
		// https://gerardnico.com/wiki/jmeter/test_plan
		// http://jmeter.apache.org/usermanual/get-started.html
		// https://www.blazemeter.com/blog/5-ways-launch-jmeter-test-without-using-jmeter-gui
		FileInputStream in;
		try {
			in = new FileInputStream("LoopTestPlan.jmx");
			HashTree testPlanTree = SaveService.loadTree(in);
			in.close();
			
			System.out.println("Tree : "+testPlanTree);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}