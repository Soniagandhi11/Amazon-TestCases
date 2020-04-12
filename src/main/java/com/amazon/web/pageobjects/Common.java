package com.amazon.web.pageobjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Common {

	public static final String CONFIGFILEPATH = "\\src\\test\\resources\\config\\config.properties";
	public static final String AMAZONCOM_WEBBASEURL = "https://www.amazon.com/";
	public static final String EXPECTED_HOMEPAGETITLE = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
	public static final String EXPECTED_SIGNINPAGETITLE = "Amazon Sign-In";
	public static final String EXPECTED_PAYMENTPAGETITLE = "Select a Payment Method - Amazon.com Checkout";
	public static final String NEW_USER = "new user";
	public static final String NEW_USER_AUTHENTICATION_MAIL_SUBJECT ="Verify your new Amazon account";
	public static final String EXISTING_USER = "existing user";
	public static final String EXISTING_USER_AUTHENTICATION_MAIL_SUBJECT ="Amazon Authentication";
	
	
	static Properties prop;
	static String password;
	

	public static Properties get_PropertiesFilesData() throws IOException {
		prop = new Properties();
		InputStream inStream = new FileInputStream(System.getProperty("user.dir") + Common.CONFIGFILEPATH);
		prop.load(inStream);
		return prop;
	}

	public static String getPassword_AfterDecode() {
		byte[] decodedBytes = Base64.getDecoder().decode(prop.getProperty("password"));
		return (password = new String(decodedBytes));

	}
	
	public static boolean isElementPresent(WebDriver driver, By element) {
		try {
			if (!(driver.findElement(element).getSize() == null)) {
				return true;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	
	
    

	
	

}
