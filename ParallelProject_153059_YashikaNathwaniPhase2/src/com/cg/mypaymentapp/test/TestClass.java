package com.cg.mypaymentapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.dbutil.DBUtil;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class TestClass {

	private static WalletService service;

	@BeforeClass
	public static void initialize() {

		service = new WalletServiceImpl();
	}

	@Before
	public void initData() {

		service.createAccount("Amit", "9900112212", new BigDecimal(9000));
		service.createAccount("Ajay", "9963242422", new BigDecimal(6000));
		service.createAccount("Yogini", "9922950519", new BigDecimal(7000));

	}

	@Test(expected = NullPointerException.class)
	public void testCreateAccount() {
		service.createAccount(null, null, null);
	}

	@Test
	public void testcreateAccount2() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertNotEquals("Yaami", cust.getName());

	}

	@Test
	public void testcreateAccount4() // positive test, it will pass
	{

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertEquals("9877998833", cust.getMobileNo());

	}

	@Test
	public void testcreateAccount5() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertEquals(new BigDecimal(6000), cust.getWallet().getBalance());

	}

	@Test
	public void testShowBalance() {

		Customer cust1 = service.showBalance("9900112212");
		String actual = cust1.getMobileNo();

		assertEquals("9900112212", actual);
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount1() {
		service.createAccount("Amit", "9900", new BigDecimal(9000.00));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount2() {
		service.createAccount("Amit", "9901", new BigDecimal(9000.00));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount3() {
		service.createAccount(null, "9900", new BigDecimal(9000.00));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount4() {
		service.createAccount("Lisa", "9900", new BigDecimal(8000.00));
	}

	@Test
	public void testcreateAccount7() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertNotEquals("9877998835", cust.getMobileNo());

	}

	@Test
	public void testcreateAccount8() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertNotNull(cust.getMobileNo());

	}

	@Test
	public void testcreateAccount9() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertNotNull(cust.getName());

	}

	@Test
	public void testcreateAccount10() {

		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));

		assertNotNull(cust.getWallet().getBalance());

	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawAmount() {
		Customer cust = new Customer();
		// cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		cust = service.withdrawAmount("9963242422", new BigDecimal(70000.00));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawAmount1() {
		Customer cust = new Customer();
		// cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		cust = service.withdrawAmount("9922950519", new BigDecimal(10000.00));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawAmount2() {
		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		cust = service.withdrawAmount("9877998833", new BigDecimal(70000.00));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testFundAmount1() {
		Customer cust = new Customer();
		
		cust = service.fundTransfer("9900112212", "9877998833", new BigDecimal(10000.00));
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testFundAmount2() {
		Customer cust = new Customer();
		cust = service.createAccount("Yashu", "9877998833", new BigDecimal(6000));
		cust = service.fundTransfer("9900112212", "9877998833", new BigDecimal(70000.00));
	}

	/*
	 * @Test(expected = InsufficientBalanceException.class) public void
	 * testFundAmount3() { Customer cust = new Customer(); // cust =
	 * service.createAccount("Yashu", "9877998833", new BigDecimal(6000)); cust =
	 * service.fundTransfer("9877998833", "9963242422", new BigDecimal(70000.00)); }
	 */

	@AfterClass
	public static void testAfter() {
		try (Connection con = DBUtil.getConnnection()) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("truncate table customer");
		} catch (Exception e) {

		}
	}
}
