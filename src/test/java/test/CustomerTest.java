package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import models.Customer;

public class CustomerTest {
	
	// public Customer(int customerId, String name, String emailAddr, String session_token) {}
	
	Customer customer = new Customer(1, "Rango", "rango@mango.com", "YX6CAFP0XO42B8NRY9");

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
	public void getEmailAddrTest() {
//		fail("Not yet implemented");
		assertEquals("rango@mango.com", customer.getEmailAddr());	
	}
	
	@Test
	public void getCustomerIdTest() {
//		fail("Not yet implemented");
		assertEquals(1, customer.getCustomerId());
	}
	
	@Test
	public void getCustomerNameTest() {
//		fail("Not yet implemented");
		assertEquals("Rango", customer.getName());
	}

}
