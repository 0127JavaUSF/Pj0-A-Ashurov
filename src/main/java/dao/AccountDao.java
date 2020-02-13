package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Account;
import models.Customer;
import util.ConnectionUtil;
import util.SessionUtil;

public class AccountDao {
	
	public static Account extractAccount(ResultSet rs) {
		try {
			return new Account(
					rs.getInt("account_id"), 
					rs.getDouble("balance"), 
					rs.getDouble("interest"), 
					rs.getBoolean("joint"),
					rs.getInt("account_owner"));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Account findAccountByCredentials(int accountOwnerId) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts where account_id = ("
					+ "SELECT account_id FROM customer_accounts WHERE customer_id = ? LIMIT 1) LIMIT 1";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, accountOwnerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Account account = AccountDao.extractAccount(rs);
				//	System.out.println("Here " + account);
				return account;
			} else {
				System.out.println("Account with such details was not found. Wrong place.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// redundant
	public static double showBalance(Account account) {
		return account.getBalance();
	}
	
	// both deposit and withdraw
	public static void updateBalance(Customer customer, double amount) {
		Account account = AccountDao.findAccountByCredentials(customer.getCustomerId());
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, account.getAccountId());
			ps.executeUpdate();	
		} catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void customerAccountsEntry(int customerId, int accountId) {
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO customer_accounts(customer_id, account_id) VALUES(?, ?) ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, customerId);
			ps.setInt(2, accountId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// at some point "I want be under rango@gmail.com's account"
	public static Account createAccount(double balance, double interest, String type, boolean joint, int accountOwnerId) {
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO accounts(balance, interest, type, joint, account_owner) VALUES (?, ?, ?, ?, ?) RETURNING *";
					
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setDouble(1, balance);
			ps.setDouble(2, interest);
			ps.setString(3, type);
			ps.setBoolean(4, joint);
			ps.setInt(5,  accountOwnerId);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				// just do it
				AccountDao.customerAccountsEntry(rs.getInt("account_owner"), rs.getInt("account_id"));
				return AccountDao.extractAccount(rs);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	
	}
	
	// to get accountId. You find user by email then get their id and find account
	// that belongs to that id (accountOwnerId), then give its accountId as a parameter to this. Terrible.
	public static Account createAccount(int customerId, int accountId ) {
		
		// int customerId, int accountId
		// find the accountId that belongs to "email"
		// we can find Customer. Then get their 
		AccountDao.customerAccountsEntry(customerId, accountId);
		return AccountDao.findAccountByCredentials(customerId);
		
	}
	
	public static void closeAccount(Customer customer, int accountId) {
		// IF they are Owner
		
		// delete rows in CustomerAccounts table that refer to this accountId
		// then delete the row in Accounts Table
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
//			Account tmpAcc = AccountDao.findAccountByCredentials(customer.getCustomerId());
//			if(tmpAcc.getAccountOwnerId() == customer.getCustomerId()) {
//				// they are the owner. They can close the account.
//				double amount = tmpAcc.getBalance();
//				System.out.println("");
//			}
			
			String sql = "DELETE FROM customer_accounts WHERE account_id = ?";
			String sql2 = "DELETE FROM accounts WHERE account_id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, accountId);
			
			ps.executeUpdate();
			
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			ps2.setInt(1, accountId);
			int rs = ps2.executeUpdate();
			if(rs != 0) {
				System.out.println("Account is now closed. Come back.");		
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void transferTo(String emailFrom, String emailTo, double amount) {
		
		try {
			
			int customerFromId = -1;
			int customerToId = -1;
			String name = "";
			
			ResultSet rs = CustomerDao.findByEmail(emailFrom);
			ResultSet rs2 = CustomerDao.findByEmail(emailTo);
			
			if(rs.next()) {
				customerFromId = rs.getInt("customer_id"); 
			}
			
			if(rs2.next()) {
				customerToId = rs2.getInt("customer_id");	
				name = rs2.getString("name");
			}
			
			
			Account account = AccountDao.findAccountByCredentials(customerFromId);
			Account accountTo = AccountDao.findAccountByCredentials(customerToId);
	
			if(account.getAccountOwnerId() == SessionUtil.getCurrentCustomer().getCustomerId()) {
				// this is the owner. They can transfer
				if(account.getBalance() >= amount) {
					try(Connection connection = ConnectionUtil.getConnection()) {
//						System.out.println(account.toString());
//						System.out.println(accountTo.toString());
						String sql = "BEGIN;"
								+ "UPDATE accounts SET balance = balance + ? WHERE account_id = ?;"
								+ "UPDATE accounts SET balance = balance - ? WHERE account_id = ?;"
								+ "COMMIT;";
						
						PreparedStatement ps = connection.prepareStatement(sql);
						
						ps.setDouble(1, amount);
						ps.setInt(2, accountTo.getAccountId());
						
						ps.setDouble(3,  amount);
						ps.setInt(4, account.getAccountId());
						
						int res = ps.executeUpdate();
						System.out.println("You have transfered " + amount + "L of water to " + name + ". Responsible.");		

					} catch(SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Your balance is not enough to make this happen. Bound.");
				}
			} else {
				System.out.println("You do not have permission to make this transfer. Weird.");
			}			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}