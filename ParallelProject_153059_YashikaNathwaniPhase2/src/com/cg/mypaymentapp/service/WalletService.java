package com.cg.mypaymentapp.service;
import java.math.BigDecimal;

import com.cg.mypaymentapp.beans.Customer;


public interface WalletService {
public Customer createAccount(String name ,String mobileno, BigDecimal amount);
public Customer showBalance (String mobileno); //show the customer details and its balance
public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount); // check whether both source and target exists; return source customer object and its balance
public Customer depositAmount (String mobileNo,BigDecimal amount ); //mobile number exists or not
public Customer withdrawAmount(String mobileNo, BigDecimal amount);
//public void acceptDetails(Customer customer);
}
