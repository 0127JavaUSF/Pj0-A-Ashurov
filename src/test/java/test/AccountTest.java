package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Account;

public class AccountTest {

//	public Account(int accountId, double balance, double interest, boolean joint, int accountOwnerId) {}

	Account account = new Account(1, 20.0, 2.0, true, 1);			
	
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
	public void getAccountIdTest() {
//		fail("Not yet implemented");
		assertEquals(1, account.getAccountId());
	}
	
	@Test
	public void getBalanceTest() {
//		fail("Not yet implemented");
//		int bal = (int) account.getBalance();
//		int balTest = (int) 20.00;
		
//		assertEquals(bal, balTest);
		assertEquals(20.00, account.getBalance(), 0.001);
	}
	
	@Test
	public void getInterestTest() {
//		fail("Not yet implemented");
		assertEquals(2.00, account.getInterest(), 0.001);
	}
	
	@Test
	public void isJointTest() {
//		fail("Not yet implemented");
		assertEquals(true, account.isJoint());
	}
	
	@Test
	public void getAccountOwnerIdTest() {
//		fail("Not yet implemented");
		assertEquals(1, account.getAccountOwnerId());
	}	

}
