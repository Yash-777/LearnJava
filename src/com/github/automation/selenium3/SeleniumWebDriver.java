package com.github.automation.selenium3;

import java.io.IOException;

import io.github.yash777.driver.Browser;
import io.github.yash777.driver.Drivers;
import io.github.yash777.driver.WebDriverException;

public class SeleniumWebDriver {
	
	public static void main(String[] args) {
		Drivers drivers = new Drivers();
		/** Auto Map driver version from browser version */
		try {
			String driverPath = drivers.getDriverPath(Browser.CHROME, 63, "");
			System.out.println("Path : "+ driverPath);
			
			/*String CH69 = drivers.getDriverPath(Browser.CHROME, 68, "2.40");
			System.out.println("Path : "+ CH69);*/
			
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
	}
}
