package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Account;
import models.Customer;
import util.Validation;

public class ValidationTest {
	
//	Customer customer = new Customer(1, "Rango", "rango@mango.com", "YX6CAFP0XO42B8NRY9");
//	Account account = new Account(1, 20.0, 2.0, true, 1);			
//	Validation val = new Validation();

	String emailWrong = "rango@gmail.com";
	String emailCorrect = "rango@mango.com";
	
	String passwordWrong = "hello2";
	String passwordCorrect = "SallyMae666";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void isValidEmailTest() {
//		fail("Not yet implemented");
		assertEquals(false, Validation.isValidEmail(emailWrong));
		assertEquals(true, Validation.isValidEmail(emailCorrect));
	}
	
	@Test
	public void isValidPasswordTest() {
//		fail("Not yet implemented");
		assertEquals(false, Validation.isValidPassword(passwordWrong));
		assertEquals(true, Validation.isValidPassword(passwordCorrect));
	} 

}
