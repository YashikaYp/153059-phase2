package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.InsetsUIResource;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.dbutil.DBUtil;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo {

	public WalletRepoImpl() {
		// super();
	}

	public boolean save(Customer customer) {

		try (Connection con = DBUtil.getConnnection()) {
			PreparedStatement pstmt = con.prepareStatement("insert into customer values(?,?,?)");
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getMobileNo());
			pstmt.setBigDecimal(3, customer.getWallet().getBalance());
			pstmt.executeUpdate();

		} catch (Exception e) {

			return false;

		}
		return true;
	}

	public Customer findOne(String mobileNo) {
		Customer customer = null;
		try (Connection con = DBUtil.getConnnection()) {
			PreparedStatement pstmt = con.prepareStatement("select * from Customer where phone_no=?");
			pstmt.setString(1, mobileNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next() == false) {
				return null;
			}
			Wallet wallet = new Wallet(rs.getBigDecimal(3));
			customer = new Customer(rs.getString(1), rs.getString(2), wallet);
		} catch (Exception e) {
			return null;
			// e.printStackTrace();
		}
		return customer;
	}

	public boolean update(Customer customer) {
		try (Connection con = DBUtil.getConnnection()) {
			String mobile = customer.getMobileNo();
			BigDecimal balance = customer.getWallet().getBalance();

			PreparedStatement pstmt = con.prepareStatement("update customer set balance=? where phone_no=?");
			pstmt.setBigDecimal(1, balance);
			pstmt.setString(2, mobile);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// e.printStackTrace();

			return false;

		}
		return true;
	}
}
