package com.amazon.web.pageobjects;

import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAccount {

	WebDriver driver;
	Properties prop;
	WebDriverWait wait;

	// xpath
	@FindBy(xpath = "//span[@class='a-button a-button-span12 a-button-base']//a[@id='createAccountSubmit']")
	WebElement create_Account;
	@FindBy(xpath = "//input[@name='customerName'][@id='ap_customer_name']")
	WebElement name;
	@FindBy(xpath = "//input[@name='email']")
	WebElement email;
	@FindBy(xpath = "//input[@id='ap_password']")
	WebElement password;
	@FindBy(xpath = "//input[@name='passwordCheck']")
	WebElement reenter_Password;
	@FindBy(xpath = "//input[@class='a-button-input']")
	WebElement create_AmazonAccount;

	// Constructor
	public CreateAccount(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		prop = Common.get_PropertiesFilesData();
	}

    // This function is used to create the account
	public String verify_createAccount() {
		create_Account.click();
		name.sendKeys(prop.getProperty("name"));
		email.sendKeys(prop.getProperty("email"));
		password.sendKeys(prop.getProperty("password"));
		reenter_Password.sendKeys(prop.getProperty("password"));
		create_AmazonAccount.click();
		return (driver.getTitle());

	}

}
