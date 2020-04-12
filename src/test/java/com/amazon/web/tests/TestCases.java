package com.amazon.web.tests;

import java.io.IOException;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.amazon.web.pageobjects.CheckoutProcess;
import com.amazon.web.pageobjects.Common;
import com.amazon.web.pageobjects.CreateAccount;
import com.amazon.web.pageobjects.Login;

public class TestCases extends TestBaseClass {

    CreateAccount createAccount_object;
	Login login_object;
	CheckoutProcess checkoutProcess_object;
	private static final Logger LOGGER = Logger.getLogger(TestCases.class.getName());
	String actual_homePageTitle; String actual_signINPageTitle;
	String actual_PaymentPageTitle;
	
	@BeforeClass
	 public void init() {
		TestBaseClass.setup(Common.AMAZONCOM_WEBBASEURL);
	}
	
	@Test(priority = 1,enabled=true)
	public void checkoutProcessForGuestUser() throws InterruptedException, IOException {
		LOGGER.info("Scenario 1 is running");
		checkoutProcess_object = new CheckoutProcess(driver);
		actual_PaymentPageTitle = checkoutProcess_object.verify_checkOutProcessfor_GuestUser();
		Assert.assertEquals(actual_PaymentPageTitle, Common.EXPECTED_PAYMENTPAGETITLE);
		driver.get(Common.AMAZONCOM_WEBBASEURL);
		login_object = new Login(driver);
		actual_signINPageTitle=login_object.verify_SignOut();
		Assert.assertEquals(actual_homePageTitle, Common.EXPECTED_SIGNINPAGETITLE);
		
	}
	
	@Test(priority = 2,enabled=true)
	public void checkoutProcessForLoginUser() throws IOException, InterruptedException {
		LOGGER.info("Scenario 2 is running");
		login_object = new Login(driver);
		actual_homePageTitle=login_object.verify_SignIn();
		Assert.assertEquals(actual_homePageTitle, Common.EXPECTED_HOMEPAGETITLE);
		checkoutProcess_object= new CheckoutProcess(driver);
		actual_PaymentPageTitle=checkoutProcess_object.verify_checkOutProcessfor_LoginUser();
		Assert.assertEquals(actual_PaymentPageTitle, Common.EXPECTED_PAYMENTPAGETITLE);
	}
	 


	

	

}
