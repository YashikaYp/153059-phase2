package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;

import com.cg.mypaymentapp.beans.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);

	public Customer findOne(String mobileNo);
	public boolean update(Customer customer);

	//public Customer depositAmount(String mobileNo, BigDecimal amount);

	//public Customer withdrawAmount(String mobileNo, BigDecimal amount);

}
