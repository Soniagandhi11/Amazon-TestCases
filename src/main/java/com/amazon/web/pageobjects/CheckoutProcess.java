package com.amazon.web.pageobjects;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutProcess {
	static WebDriver driver;
	Properties prop;
	WebDriverWait wait;
	CreateAccount createaccount_object;
	MailReader mail_reader = new MailReader();

	// xpath
	@FindBy(xpath = "//input[@id='twotabsearchtextbox'][@name='field-keywords']")
	WebElement search_Textbox;
	@FindBy(xpath = "//span[@class='a-size-base a-color-base'][contains(text(),'Intel Core i7')]")
	WebElement processor;
	@FindBy(xpath = "//span[@class='a-size-base a-color-base'][contains(text(),'8 GB')]")
	WebElement RAM_8GB;
	@FindBy(xpath = "//span[@class='a-size-base a-color-base'][contains(text(),'16 GB')]")
	WebElement RAM_16GB;
	@FindBy(xpath = "//span[contains(text(),'13 to 13.9 Inches')]")
	WebElement display_Size;
	@FindBy(xpath = "//span[@class='a-dropdown-prompt']")
	WebElement drop_Down;
	@FindBy(xpath = "//a[@class='a-dropdown-link'][contains(text(),'Price: Low to High')]")
	WebElement price_lowtoHigh;
	@FindBy(xpath = "//span[@class='celwidget slot=SEARCH_RESULTS template=SEARCH_RESULTS widgetId=search-results index=0']//span[@class='rush-component']")
	WebElement product_Laptop;
	@FindBy(xpath = "//input[@id='buy-now-button'][@name='submit.buy-now']")
	WebElement buy_Now;
	@FindBy(xpath = "//span[@class='a-button a-button-span12 a-button-primary cvf-widget-btn cvf-widget-btn-verify']")
	WebElement otp_verify;
	@FindBy(xpath = "//input[@id='enterAddressAddressLine1']")
	WebElement address_line1;
	@FindBy(xpath = "//input[@id='enterAddressCity']")
	WebElement address_city;
	@FindBy(xpath = "//input[@id='enterAddressStateOrRegion']")
	WebElement address_state;
	@FindBy(xpath = "//input[@id='enterAddressPostalCode']")
	WebElement address_zipcode;
	@FindBy(xpath = "//input[@id='enterAddressPhoneNumber']")
	WebElement address_phonenumber;
	@FindBy(xpath = "//input[@class='a-button-text submit-button-with-name'][@name='shipToThisAddress']")
	WebElement address_continueButton;
	By deliver_toAddress = By.xpath("//input[@name='useSelectedAddress'][@class='a-button-text submit-button-with-name']");
	By add_LaptopProtectionPopup = By.xpath("//div[@class='a-popover-wrapper']");
	@FindBy(xpath = "//button[@id='siNoCoverage-announce'][@class='a-button-text']")
	WebElement noThanks;
	@FindBy(xpath ="//div[@class='ship-to-this-address a-button a-button-primary a-button-span12 a-spacing-medium']//a[@class='a-declarative a-button-text ']")
	WebElement deliever_tothisAddress;
	
	
	// Constructor
	public CheckoutProcess(WebDriver driver) throws IOException {
		CheckoutProcess.driver = driver;
		prop = Common.get_PropertiesFilesData();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		createaccount_object = new CreateAccount(driver);
		PageFactory.initElements(driver, Login.class);

	}

	// This function is used for verify the checkout Process for Guest user
	public String verify_checkOutProcessfor_GuestUser() throws InterruptedException, IOException {

		// Calling the searching function to search the items
		this.searchitems_BasedonSearchCriteria();
		// Calling the create account method to create the new account
		createaccount_object.verify_createAccount();
		if (!driver.findElements(Login.otp_window).isEmpty()) {
			// calling the mail reader method to fetch the otp from mail
			String otpvalue = mail_reader.mailReader(Common.NEW_USER);
			Login.otp_element.sendKeys(otpvalue);
			otp_verify.click();
		}

		// filling the address details
		address_line1.sendKeys(prop.getProperty("addressline1"));
		address_city.sendKeys(prop.getProperty("city"));
		address_state.sendKeys(prop.getProperty("state"));
		address_zipcode.sendKeys(prop.getProperty("postalCode"));
		address_phonenumber.sendKeys(prop.getProperty("mobileNo"));
		address_continueButton.click();
		if (Common.isElementPresent(driver, deliver_toAddress)) {
			driver.findElement(deliver_toAddress).click();
		}
		return (driver.getTitle());

	}

	// This function is used for verify the checkout Process of item for Login user
	public String verify_checkOutProcessfor_LoginUser() throws IOException, InterruptedException {
		
		// Calling the searching function to search the items
		this.searchitems_BasedonSearchCriteria();
		if (!driver.findElements(add_LaptopProtectionPopup).isEmpty()) {
			wait.until(ExpectedConditions.elementToBeClickable(noThanks)).click();
		}
		deliever_tothisAddress.click();
		return(driver.getTitle());

	}

	// This function is used for searching of items basis on Search criteria
	public void searchitems_BasedonSearchCriteria() throws InterruptedException {
		search_Textbox.sendKeys(prop.getProperty("searchKeyword"));
		Thread.sleep(1000);
		search_Textbox.sendKeys(Keys.ENTER);
		processor.click();
		RAM_8GB.click();
		RAM_16GB.click();
		display_Size.click();
		drop_Down.click();
		price_lowtoHigh.click();
		product_Laptop.click();
		buy_Now.click();
	}

}
