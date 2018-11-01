package com.github.automation.selenium3;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 
 * Alert IGNORE « https://github.com/mozilla/geckodriver/issues/617
 * @author yashwanth.m
 *
 */
public class Selenium_3 {
	public static void main(String[] args) throws IOException {
		String browserName = "firefox", firefoxVersion = "56", FF_DriverPack = "v0.19.0";
		
		FirefoxProfile profile = new FirefoxProfile(); // about:support - Troubleshooting Information
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		
		String binaryPath = "D:\\BrowsersCustomInstalation\\FF_43\\firefox.exe";
		// https://stackoverflow.com/a/10575676/5081877
		if( firefoxVersion != null  && !"".equalsIgnoreCase( firefoxVersion ) && browserName.contains("firefox")
				&& (firefoxVersion.matches("[0-9]+") && firefoxVersion.length() >= 2) ) {
			int parseInt = Integer.parseInt(firefoxVersion);
			System.out.println( "Int : "+ parseInt );
			
			profile.setPreference("browser.startup.homepage", "about:blank");
			
			if ( parseInt <= 47 ) {
				// https://developer.mozilla.org/en-US/docs/Mozilla/QA/Marionette
				System.out.println("Marionette Driver « INFO	Enabled via --marionette");
				profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
				//Set language
				profile.setPreference("intl.accept_languages", "no,en-us,en");
				
				profile.setPreference("xpinstall.signatures.required", false);
				profile.setPreference("toolkit.telemetry.reportingpolicy.firstRun", false);
				profile.setPreference("intl.accept_languages", "no,en-us,en");
				profile.setPreference( "app.update.enabled", false);
				profile.setPreference( "browser.tabs.autoHide", true);
				profile.setAcceptUntrustedCertificates( true );
				profile.setAssumeUntrustedCertificateIssuer( true );
				//profile.setEnableNativeEvents( true );
				profile.setPreference("browser.link.open_newwindow.disabled_in_fullscreen", true);
				//some more prefs:
				
			} else if ( parseInt > 47 ) {
				// https://github.com/mozilla/geckodriver
				System.out.println("Gecko Driver");
				profile.setPreference("browser.preferences.search", false);
			
				/*StringBuilder builder = new StringBuilder();
				builder.append( String.format(driverURLFormTomcatRoot+"firefoxdrivers/%s/%s", FF_DriverPack, "geckodriver.exe") );
				String driverPath = builder.toString();
				File fireFoxTempExe = File.createTempFile("geckodriver",	null);
					 fireFoxTempExe.setExecutable(true);
				FileUtils.copyURLToFile(new URL(driverPath), fireFoxTempExe);
				String driver = fireFoxTempExe.getAbsolutePath();*/
				
				String driver = "D:/Yashwanth/D Drive/YashGt/SeleniumDriverAutomation/Drivers/FireFox/v0.19.1/geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", driver );
				// mozrunner::runner	INFO	Running command: 
				// "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe" "-marionette" "-profile" "C:\\Users\\YASHWA~1.M\\AppData\\Local\\Temp\\rust_mozprofile.BfSwaXD37gxH"
				capabilities.setCapability("marionette", true);
			}
			
		}
		
		File file = new File( binaryPath );
		FirefoxBinary binary = new FirefoxBinary(file);
		
		//ProfilesIni myProfile = new ProfilesIni();
		//FirefoxProfile profile = myProfile.getProfile("Yash");
		
		WebDriver driver = new FirefoxDriver(binary, profile, capabilities);
		driver.manage().deleteAllCookies(); 
		
		//Maximize browser window
		driver.manage().window().maximize();
		//Set  timeout  for 5 seconds so that the page may load properly within that time
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		
		//Go to URL which you want to navigate
		//driver.get("https://www.w3schools.com/html/");
		fnd(driver);
		
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
	public static void fnd( WebDriver driver ) {
		driver.get("https://www.fnp.com/gift/chocolate-cake?pos=1");
		
		WebElement areaPIN = driver.findElement(By.id("destlookup"));
		areaPIN.sendKeys("500084");
		areaPIN.click();
		
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		
		String options = "//div[@class='pac-container pac-logo' and @style='width: 254px; position: absolute; left: 1028px; top: 336px;']/div[1]";
		WebElement seelect1 = driver.findElement(By.xpath( options ));
		seelect1.click();
		
		WebElement today = driver.findElement(By.xpath("//a[@class='ui-state-default ui-state-highlight ui-state-active']"));
		today.click();
		
		WebElement freeDelivery = driver.findElement(By.xpath("//a[@data-ga-title='Standard Delivery']"));
		freeDelivery.click();
		
		WebElement timeSlot = driver.findElement(By.xpath("//span[.='Standard Delivery - Free']/following::ul[1]/li[1]/a[1]"));
		timeSlot.click();
		
	}
}
