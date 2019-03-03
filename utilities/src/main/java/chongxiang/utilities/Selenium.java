package chongxiang.utilities;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * This class provides various methods related to Selenium WebDriver
 * 
 * @author chongxiang
 * created time: 2019-02-27
 */

public class Selenium {

	public enum BrowserTypes {Chrome, IE, Firefox, iOS_10_Safari_6, iOS_11_Safari_7, iOS_12_Safari_7};
	
	Utilities utitlies = new Utilities();
	
	private BrowserTypes BrowserType;
	public void setBrowserType(BrowserTypes BrowserType) {
		this.BrowserType = BrowserType;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	/*
	 * 1. Dependency: Selenuim WebDriver
	   <dependency>
    		<groupId>org.seleniumhq.selenium</groupId>
    		<artifactId>selenium-java</artifactId>
    		<version>3.141.5</version>
		</dependency>
	 * 2. <driver> parameter needs to be initialized before this method is called
	 * 3. <by> parameter is to be the element that needs be verified. For example, By.id("user_name")
	 */
	public static boolean isElementExist(WebDriver driver, By by)throws Exception {
		try {
			if(driver.findElements(by).size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * 1. <dependency>
		    <groupId>io.appium</groupId>
		    <artifactId>java-client</artifactId>
		    <version>7.0.0</version>
		</dependency>
	 * 2. InitializeBrowserDriver returns a Selenium WebDriver object for pre-set type of browserTypes.
	 * 3. Set driver location in config.properties
	 */
	@SuppressWarnings("rawtypes")
	public WebDriver initializeBrowserDriver()throws Exception{
		WebDriver driver;
		String exePath = "";
		
		switch(this.BrowserType) {
		
			case Chrome : {
				exePath = utitlies.getProperty("chromedriverlocation");
				System.setProperty("webdriver.chrome.driver", exePath);
				driver = new ChromeDriver();
				break;
			}
			case IE : {
				exePath = utitlies.getProperty("iedriverlocation");
				System.setProperty("webdriver.gecko.driver", exePath);
				driver = new InternetExplorerDriver();
				break;
			}
			case Firefox : {
				exePath = utitlies.getProperty("geckodriverlocation");
				System.setProperty("webdriver.gecko.driver", exePath);
				driver = new FirefoxDriver();
				break;
			}
			case iOS_10_Safari_6: {
			
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.3");
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");

				URL url = new URL("http://127.0.0.1:4723/wd/hub");
				
				driver = new IOSDriver(url, capabilities);
				
				break; 
			}
			default:{
				exePath = utitlies.getProperty("chromedriverlocation");
				System.setProperty("webdriver.chrome.driver", exePath);
				driver = new ChromeDriver();
				break;
			}
		}
		
		
		return driver;
	}
}
