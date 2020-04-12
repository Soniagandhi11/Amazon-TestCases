package com.amazon.web.pageobjects;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	WebDriver driver;
	Properties prop;
	Actions action;
	WebDriverWait wait;
	MailReader mail_reader;

	// xpath
	@FindBy(xpath = "//div[@class='glow-toaster-footer']//span[@class='a-button-inner']//input[@data-action-type='DISMISS']")
	WebElement address_Dismiss;
	@FindBy(xpath = "//span[@class='a-button a-button-span12 a-button-primary cvf-widget-btn cvf-widget-btn-verify']") WebElement otp_verify;
	@FindBy(xpath = "//input[@name='email'][@id='ap_email']") WebElement signin_email;
	@FindBy(xpath = "//input[@class='a-button-input']") WebElement signin_Continue;
	@FindBy(xpath = "//input[@name='password']") WebElement signin_password;
	@FindBy(xpath = "//input[@class='a-button-input']") WebElement signIn;
	static @FindBy(xpath = "//input[@class='a-input-text a-span12 cvf-widget-input cvf-widget-input-code']") WebElement otp_element;
	@FindBy(xpath = "//a[@id='ap-account-fixup-phone-skip-link']") WebElement notNow;
    By location_toaster=By.xpath("//div[@class='a-section glow-toaster glow-toaster-theme-default glow-toaster-slot-default nav-coreFlyout nav-flyout']");
	static By otp_window= By.xpath("//div[@class='a-box-inner a-padding-extra-large']");
	By add_mobileNoForm= By.xpath("//div[@class='a-box-inner a-padding-extra-large']//form[@id='auth-account-fixup-phone-form']");
	By nav=By.xpath("//span[contains(@class,'nav-line-1')][contains(text(),'Hello,')]");
	By menu_signIn=By.xpath("//div[@class='nav-flyout-content']//span[@class='nav-action-inner'][contains(text(),'Sign in')]");
	By menu_signOut=By.xpath("//span[contains(text(),'Sign Out')][@class='nav-text']");
	
	
	
	
	// Constructor
	public Login(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		prop = Common.get_PropertiesFilesData();
		action= new Actions(driver);
		wait= new WebDriverWait(driver,30);

	}

	// This function is used to sign out from system
	public String verify_SignOut() {
		action.moveToElement(driver.findElement(nav)).perform();
		if(!Common.isElementPresent(driver,menu_signOut)) 
		{ action.moveToElement(driver.findElement(nav)).perform();}
		 driver.findElement(menu_signOut).click(); 
		return (driver.getTitle());
	}

	// This function is used for sign-in
	public String verify_SignIn() {
		if(Common.isElementPresent(driver,location_toaster)) {
		address_Dismiss.click(); }
		if(Common.isElementPresent(driver,nav)){
		action.moveToElement(driver.findElement(nav)).perform();}
		if(Common.isElementPresent(driver,menu_signIn)) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(menu_signIn))).click();}
		signin_email.sendKeys(prop.getProperty("email"));
		signin_Continue.click();
		signin_password.sendKeys(prop.getProperty("password"));
		signIn.click();
		if(Common.isElementPresent(driver,add_mobileNoForm)) {
			notNow.click();
		}
		if(Common.isElementPresent(driver,otp_window)){
		// calling the mail reader method to fetch the otp from mail
		mail_reader = new MailReader();
		String otpvalue = mail_reader.mailReader(Common.EXISTING_USER);
		otp_element.sendKeys(otpvalue);
		otp_verify.click();
		}
		return (driver.getTitle());
	}

}
