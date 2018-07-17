
package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
//import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	public WalletRepo repo = new WalletRepoImpl();
	// Customer customer;

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {

		Customer customer = new Customer(name, mobileNo, new Wallet(amount));
		// System.out.println(customer);

		if (validate(customer.getMobileNo())) {
			boolean f;
			f = repo.save(customer);
			// System.out.println(f);

		}

		else {
			throw new InvalidInputException("Invalid details");
			// return null;

		}
		return customer;
	}

	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);
		if (customer != null)
			return customer;
		else
			return null;
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		Customer c1 = repo.findOne(sourceMobileNo);

		if (c1 != null) {
			Customer c2 = repo.findOne(targetMobileNo);
			if (c2 != null) {
				c2 = depositAmount(targetMobileNo, amount);
				if (c2 != null) {
					c1 = withdrawAmount(sourceMobileNo, amount);
				} else {
					throw new InsufficientBalanceException("Balance Insufficient");
				}

			} else
				throw new InvalidInputException("Target account holder not found");

		} else
			throw new InvalidInputException("Source account holder not found");

		return c1;

	}

	WalletRepo repo1 = new WalletRepoImpl();

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer cust = repo.findOne(mobileNo);
		Wallet wallet = new Wallet();
		// String mobile=cust1.getMobileNo();

		if (cust != null) {
			BigDecimal amt = cust.getWallet().getBalance();
			amt = amt.add(amount);

			wallet.setBalance(amt);
			cust.setWallet(wallet);
			// repo1.getData().put(mobileNo, cust);
			repo1.update(cust);
			return cust;
		}

		else
			throw new InvalidInputException("invalid mob no");

	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer cust = repo.findOne(mobileNo);
		BigDecimal prevAmt = null;
		Wallet wallet = new Wallet();

		if (cust != null)
			prevAmt = cust.getWallet().getBalance();
		int result = prevAmt.compareTo(amount);
		if (result == 1 || result == 0)

		{
			prevAmt = prevAmt.subtract(amount);
			wallet.setBalance(prevAmt);
			cust.setWallet(wallet);
			// repo1.getData().put(mobileNo, cust);
			repo1.update(cust);
			return cust;

		} else
			throw new InsufficientBalanceException(
					"You don't have the required balance to withdraw,Please enter a minimum balance to continue");

	}

	public boolean validate(String phoneno) {

		Pattern p = Pattern.compile("[7-9]?[0-9]{10}");
		Matcher m = p.matcher(phoneno);

		if (m.matches())
			return true;
		else
			return false;
	}

	/*
	 * public void acceptDetails(Customer customer) { Scanner scan = new
	 * Scanner(System.in); String str = customer.getMobileNo(); while (true) //
	 * Validate phone number if (validate(str)) { break; } else {
	 * System.err.println("Phone number invalid");
	 * System.out.println("Enter valid phone number");
	 * customer.setMobileNo(scan.next()); } }
	 */
}