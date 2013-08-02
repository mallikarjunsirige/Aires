package AiresRest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RestClientHelper {
	
	public static void addHeader(String name, String value)
		
	{
		WebDriver driver = new FirefoxDriver();
		driver.get("chrome://restclient/content/restclient.html");
	}	
		

		
public static void dataService(WebDriver driver,String urlname) {
	
	driver.findElement(By.xpath("//*[@id='request-url']")).clear();
	driver.findElement(By.xpath("//*[@id='request-url']")).sendKeys(urlname);
	//driver.findElement(By.xpath("//*[@id='request-url']")).sendKeys("http://172.16.13.183:90/aires/api/AiresDataService.svc/Chemicals");
	driver.findElement(By.xpath("//*[@id='request-button']")).click();
	driver.findElement(By.xpath("//a[text()='Response Body (Raw)']")).click();
	

//	String iteration2 = driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
//	System.out.println("running iteration2"+iteration2);
	System.out.println("running test name:"+urlname);

	}

public static void clickLinks(WebDriver driver, String linkName)
{
	driver.findElement(By.xpath("//a[text()='" + linkName +" ']")).click();
}
}

//	public static void actionOnElementByType(WebDriver driver, String action, String type, String identifier, String value)
//
//	{
//		if (action == "keyIn")
//		{
//			if ( type == "xpath")
//				driver.findElement(By.xpath(identifier)).sendKeys(value);
//			else if( type == "name")
//				driver.findElement(By.name(identifier)).sendKeys(value);
//			else if (type == "linkText")
//				driver.findElement(By.linkText(identifier)).sendKeys(value);
//		}
//		else if (action == "clear")
//		{
//			if ( type == "xpath")
//				driver.findElement(By.xpath(identifier)).clear();
//			else if( type == "name")
//				driver.findElement(By.name(identifier)).clear();
//			else if (type == "linkText")
//				driver.findElement(By.linkText(identifier)).clear();;
//		}
//		else if (action == "click")
//		{
//			if ( type == "xpath")
//				driver.findElement(By.xpath(identifier)).click();
//			else if( type == "name")
//				driver.findElement(By.name(identifier)).click();
//			else if (type == "linkText")
//				driver.findElement(By.linkText(identifier)).click();;
//		}
//
