package com.github.automation.selenium3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Selenium3 {
	public static void main(String[] args) throws IOException {
		String browserName = "firefox", firefoxVersion = "55", FF_DriverPack = "v0.19.0",
				driverURLFormTomcatRoot = "http://testingapp3.clictest.com:8080/";
		
		FirefoxProfile profile = new FirefoxProfile(); // about:support - Troubleshooting Information
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		
		String binaryPath = "D:\\BrowsersCustomInstalation\\FF_43\\firefox.exe";
		// https://stackoverflow.com/a/10575676/5081877
		if( firefoxVersion != null  && !"".equalsIgnoreCase( firefoxVersion ) && browserName.contains("firefox")
				&& (firefoxVersion.matches("[0-9]+") && firefoxVersion.length() >= 2) ) {
			int parseInt = Integer.parseInt(firefoxVersion);
			System.out.println( "Int : "+ parseInt );
			
			
			if ( parseInt <= 47 ) {
				// https://developer.mozilla.org/en-US/docs/Mozilla/QA/Marionette
				System.out.println("Marionette Driver");
				
				profile.setPreference("browser.startup.homepage", "about:blank");
				profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
				//Set language
				profile.setPreference("intl.accept_languages", "no,en-us,en");
				//some more prefs:
				profile.setPreference( "app.update.enabled", false);
				profile.setPreference( "browser.tabs.autoHide", true);
				profile.setPreference("xpinstall.signatures.required", false);
				profile.setPreference("toolkit.telemetry.reportingpolicy.firstRun", false);
			} else if ( parseInt > 47 ) {
				// https://github.com/mozilla/geckodriver
				System.out.println("Gecko Driver");
				
				binaryPath = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
				StringBuilder builder = new StringBuilder();
				builder.append( String.format(driverURLFormTomcatRoot+"firefoxdrivers/%s/%s", 
						FF_DriverPack, "geckodriver.exe") );
				
				String driverPath = builder.toString();
				File fireFoxTempExe = File.createTempFile("geckodriver",	null);
					 fireFoxTempExe.setExecutable(true);
				FileUtils.copyURLToFile(new URL(driverPath), fireFoxTempExe);
				
				System.setProperty("webdriver.gecko.driver", fireFoxTempExe.getAbsolutePath() );
				capabilities.setCapability("marionette", true);
			}
			
		}
		
		File file = new File( binaryPath );
		FirefoxBinary binary = new FirefoxBinary(file);
		
		//ProfilesIni myProfile = new ProfilesIni();
		//FirefoxProfile profile = myProfile.getProfile("Yash");
		
		@SuppressWarnings("deprecation")
		WebDriver driver = new FirefoxDriver(binary, profile, capabilities);
		driver.manage().deleteAllCookies(); 
		
		//Maximize browser window
		driver.manage().window().maximize();
		//Go to URL which you want to navigate
		driver.get("https://www.w3schools.com/html/");
		//Set  timeout  for 5 seconds so that the page may load properly within that time
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		
		/*Set<Cookie> cookies = driver.manage().getCookies();
		for(Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}*/
		
		System.out.println("Enter something in console to quit the browser and driver.");
		try {
			System.in.read();
			System.in.read();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		//close firefox browser
		driver.close();
	}
}
