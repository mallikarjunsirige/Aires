package AiresRest;

import java.util.Enumeration;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login {

	public FirefoxProfile firefoxProfile = new FirefoxProfile();
	public static ProfilesIni allProfiles = new ProfilesIni();
	public static FirefoxProfile profile = allProfiles.getProfile("Selenium");
	public static WebDriver driver = new FirefoxDriver(profile);
	public static Enumeration values;
	public static Hashtable<String, String> headers = new Hashtable<String, String>();
	public static String[] url = new String[4];
	public static String str;
	public static String username = "sirige";

	public void PositiveHeaders() {
		headers.put("AiresAuthorization", "True");
		headers.put("AiresAuthentication", "True");
		headers.put("Domain", "gbtpa");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		driver.get("chrome://restclient/content/restclient.html");
		values = headers.keys();
		driver.findElement(By.xpath("//*[@id='request-url']")).sendKeys(
				"http://172.16.13.183:90/aires/api/AiresRESTService.svc/Login");
		String baseURL = "http://172.16.13.183:90/aires/api/AiresDataService.svc/";
		url[0] = baseURL + "Chemicals";
		url[1] = baseURL + "DeviceTypes";
		url[2] = baseURL + "PPEs";
		// String url4=
		// "http://172.16.13.183:90/aires/api/AiresRESTService.svc/ProjectDetails";
		url[3] = "http://172.16.13.183:90/aires/api/AiresRESTService.svc/Logout";
		// Adding headers
	}

	@BeforeTest
	public void testAaddingHeaders() throws InterruptedException {
		// Adding domain, Aires Authentication and other headers	
		Thread.sleep(3000);
		PositiveHeaders();		
		
		while (values.hasMoreElements()) {

			str = (String) values.nextElement();
			RestClientHelper.clickLinks(driver, "Headers");
			driver.findElement(By.linkText("Custom Header")).click();
			Thread.sleep(3000);
			driver.findElement(By.name("name")).clear();
			driver.findElement(By.name("name")).sendKeys(str);
	
			driver.findElement(By.xpath("//input[@name='value']")).clear();
			driver.findElement(By.xpath("//input[@name='value']")).sendKeys(
					headers.get(str));

			driver.findElement(
					By.xpath("//span[text()='Save to favorite']/../input"))
					.click();
			driver.findElement(
					By.xpath("//span[text()='Save to favorite']/../../input[@value='Okay']"))
					.click();
			Thread.sleep(3000);
		}

		// Adding user name and password header
		driver.findElement(By.xpath("//a[text()='Authentication ']")).click();
		driver.findElement(By.linkText("Basic Authentication")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(
				username);
		driver.findElement(By.name("password")).sendKeys("abcd");
		driver.findElement(By.name("remember")).click();
		driver.findElement(
				By.xpath("//*[@id='modal-basic-authorization']/div[3]/input[1]"))
				.click();
		driver.findElement(By.xpath("//*[@id='request-button']")).click();
		Thread.sleep(2000);

		// fetching access_token from the text area

		driver.findElement(By.xpath("//a[text()='Response Body (Raw)']"))
				.click();
		Thread.sleep(2000);
		String myString = driver.findElement(
				By.xpath("//*[@id='response-body-raw']/pre")).getText();
//		String[] parts = myString.split(",");
//		for (int i = 0; i < parts.length; i++) {
//			System.out.println("MS :" + parts[i]);
//		}
//
//		String token = parts[14];
//		String[] tokenParts = token.split("\"");
//		for (int i = 0; i < tokenParts.length; i++) {
//			System.out.println("MMM:" + tokenParts[i]);
//		}
//		String actualToken = tokenParts[3];
//		System.out.println("MM:" + actualToken);
//		Thread.sleep(5000);
//
		String s = myString.split("AccessToken")[1];
		String s1 = s.substring(3, s.indexOf("\",\"LoginDateTime"));
		System.out.println(s);
		System.out.println(s.indexOf("\",\"LoginDateTime"));
		System.out.println(s1);
		// adding access_token header

		driver.findElement(By.xpath("//a[text()='Headers ']")).click();
		driver.findElement(By.linkText("Custom Header")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='name']")).clear();
		driver.findElement(By.xpath("//input[@name='name']")).sendKeys(
				"access_token");
		driver.findElement(By.xpath("//input[@name='value']")).clear();
		driver.findElement(By.xpath("//input[@name='value']")).sendKeys(
				s1);
		System.out.println("This is token for generation  " + s1);
		driver.findElement(
				By.xpath("//*[@id='modal-custom-header']/div[3]/div/input"))
				.click();
		driver.findElement(
				By.xpath("//*[@id='modal-custom-header']/div[3]/input[1]"))
				.click();
		Thread.sleep(5000);
	}

	@Test
	public void testChemicals() throws InterruptedException {
		RestClientHelper.dataService(driver, url[0]);
		String expResult2 = "{\"value\":[{\"ChemicalId\":1,\"ChemicalName\":\"Carbon dioxide\",\"PELTWA\":\"50\"";
		String actualResult2 = driver.findElement(
				By.xpath("//*[@id='response-body-raw']/pre")).getText();
		Boolean value = actualResult2.contains(expResult2);
		System.out.println("value is" + value);
		Thread.sleep(3000);
	}

	@Test
	public void testDeviceTypes() {

		RestClientHelper.dataService(driver, url[1]);
		String expResult3 = "{\"value\":[{\"DeviceTypeId\":1,\"TypeName\":\"Active\"},[{\"DeviceTypeId\":2,\"TypeName\":\"Passive\"}";
		String actualResult3 = driver.findElement(
				By.xpath("//*[@id='response-body-raw']/pre")).getText();
		Boolean value = actualResult3.contains(expResult3);
		System.out.println("value is" + value);
	}

	@Test
	public void testRemoveHeaders() {
		driver.findElement(By.xpath("//span[@class='request-header-reset']/a"))
				.click();

	}
}

	//
//	public void negativeHeaders(int i) {
//		switch (i)
//		{
//		case 1:
//			headers.put("AiresAuthorization", "aaaa");
//			headers.put("AiresAuthentication", "True");
//			headers.put("Domain", "btpa");
//			headers.put("Content-Type", "application/json");
//			headers.put("Accept", "application/json");
//			break;
//		case 2:
//			headers.put("AiresAuthorization", "True");
//			headers.put("AiresAuthentication", "fdsfds");
//		case 3:
//			headers.put("AiresAuthorization", "True");
//			headers.put("AiresAuthentication", "True");
//			headers.put("Domain", "dsfdsfds");
//		case 4:
//			headers.put("AiresAuthorization", "True");
//			headers.put("AiresAuthentication", "True");
//			headers.put("Domain", "btpa");
//			headers.put("Content-Type", "dsfsdfsdf");
//		case 5:
//			headers.put("AiresAuthorization", "True");
//			headers.put("AiresAuthentication", "True");
//			headers.put("Domain", "btpa");
//			headers.put("Content-Type", "application/json");
//			headers.put("Accept", "dfgfdgvcv");
//		}
//	}
//	public void addHeader(String key, String value)
//	{
//		headers.put(key, value);
//	}
//}
//


// Assert.assertTrue(expResult2.contains("failure"));

// pass url
// click url
// verify text with postive input
// verify text with negative input

// @Test
// public void testLogout() {
//
// }
//
//
//
//
//
// @Test
// public void negativeTesting() {
//
// //Validation for login
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// String expResult1 = "{\"status\":\"Success\"}";
// String actualResult1=
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// Assert.assertEquals(actualResult1, expResult1);
// Assert.assertTrue(expResult1.contains("failure"));
//
// //Validation for chemicals
//
//
//
// //Validation for DeviceTypes
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// String expResult3 =
// "{\"value\":[{\"DeviceTypeId\":1,{\"DeviceTypeId\":4,\"TypeName\":\"Wipe\"}]}}";
// String actualResult3=
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// Assert.assertEquals(actualResult3, expResult3);
// Assert.assertTrue(expResult3.contains("failure"));
//
// //Validation for PPEs
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// String expResult4 =
// "{\"PPEId\":1},{\"PPEId\":8,\"ProtectionEquipmentName\":\"AS\"}";
// String actualResult4=
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// Assert.assertEquals(actualResult4, expResult4);
// Assert.assertTrue(expResult4.contains("failure"));
//
//
// //Validation for logout
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// String expResult5 =
// "{\"response\":\"User logged out successfully\",\"status\":\"Success\"}";
// String actualResult5=
// driver.findElement(By.xpath("//*[@id='response-body-raw']/pre")).getText();
// Assert.assertEquals(actualResult5, expResult5);
// Assert.assertTrue(expResult5.contains("failure"));
//
//
// RestClientHelper.dataService(driver,url[i]);
// Thread.sleep(3000);
// }

// File file = new File("/Users/mallikarjun/Desktop/AiresUrl's.txt");
// file.createNewFile();
//
// FileWriter fileWriter = new FileWriter(file);
// BufferedWriter writer = new BufferedWriter(fileWriter);
// writer.write(Txt+"-----------------------------------------------------------");
//
// //writer.flush();
//
// FileReader fr = new FileReader(file);
// BufferedReader br = new BufferedReader(fr);
// System.out.println(br.readLine());

// RestClientHelper.dataService(driver,url1);
// Thread.sleep(3000);
// RestClientHelper.dataService(driver,url2);
// Thread.sleep(3000);
// RestClientHelper.dataService(driver,url3);
// Thread.sleep(3000);
//
// RestClientHelper.dataService(driver,url5);
// System.out.println("This is token for logout: " + actualToken);
// Thread.sleep(5000);
// RestClientHelper.dataService(driver,url4);

// driver.findElement(By.xpath("//*[@id='request-button']")).click();

// }

// }

