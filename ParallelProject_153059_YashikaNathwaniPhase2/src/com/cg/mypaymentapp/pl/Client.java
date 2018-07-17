package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.security.Provider.Service;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	private WalletService service;

	public Client() {
		service = new WalletServiceImpl();
	}

	public void menu() {
		System.out.println("****WELCOME TO PAYMENT WALLET****");
		System.out.println("To proceed please : Choose from the options below");
		System.out.println("1) Create Account");
		System.out.println("2) Show Balance ");
		System.out.println("3) Deposit Money ");
		System.out.println("4) Withdraw Money ");
		System.out.println("5) Fund Transfer ");

		System.out.println("0) Exit");
		Customer customer = new Customer();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter choice");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			System.out.println("CREATE ACCOUNT");
			System.out.println("Enter your name");
			String name = sc.next();
			System.out.println("Enter your mobile number");
			String mobile = sc.next();
			System.out.println("Enter initial amount");
			BigDecimal amount = sc.nextBigDecimal();

			customer = service.createAccount(name, mobile, amount);
			
			try {
				if (customer != null) {
					System.out.println(customer);
					System.out.println("Congratulations! Accout created successfully");
				} else
					throw new InvalidInputException("");
			} catch (InvalidInputException e) {
				e.getMessage();
			}
			break;
		case 2:
			System.out.println("SHOW BALANCE");
			System.out.println("Enter mobile number");
			String ph = sc.next();
			try {
				Customer cust = service.showBalance(ph);
				System.out.println(cust);

				}
			 catch (Exception e) {
				System.out.println(e.getMessage());
			}
			

			break;
			
		case 3:
			System.out.println("WELCOME TO DEPOSIT MONEY");
			System.out.println("ENter the mobile into which you want to deposit");
			try
			{
			String mobile11 = sc.next();
			System.out.println("Enter amount");
			BigDecimal amount11 = sc.nextBigDecimal();
			Customer c = service.depositAmount(mobile11, amount11);
			System.out.println(c);
			
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			break;

		case 4:
			System.out.println("Withdraw amount");
			System.out.println("Enter the mobile number to withdraw");
			try
			{
			String no = sc.next();
			System.out.println("Enter the amount u want to withdraw");
			BigDecimal amt2 = sc.nextBigDecimal();
			Customer c1 = service.withdrawAmount(no, amt2);
			System.out.println(c1);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		case 5:
			System.out.println("Fund Trasfer Portal");
			System.out.println("Enter the number you want to withdraw from");
			try
			{
			String m1 = sc.next();
			System.out.println("Enter the number you want to transfer money to");
			String m2 = sc.next();
			System.out.println("Enter the amount");
			BigDecimal transferAmt = sc.nextBigDecimal();
			Customer c2 = service.fundTransfer(m1, m2, transferAmt);
			System.out.println(c2);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;
		case 0:
			System.out.println("Exited successfully");
			System.exit(0);
			break;
		default:
			System.out.println("Enter valid choice");
		}
	}

	public static void main(String[] args) {

		Client client = new Client();
		while (true)
			client.menu();

	}
}
