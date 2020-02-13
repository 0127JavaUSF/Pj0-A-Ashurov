package dao;

/*
 * Login (C)
 * Register (C)
 * findByCredentials (C)
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import models.Customer;
import util.ConnectionUtil;
import util.SessionUtil;

public class CustomerDao {
	
	public static Customer currentCustomer;

	public static ResultSet findByEmail(String email) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM customers WHERE email_addr = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			// LESSON: You can't call NEXT and then return RS. Calling NEXT 2 times, will result to NULL being shown as value returned
			return ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Customer findByCredentials(String email, String password) {
		// user with such email was found. Now, let's check if password is correct
		try {
			ResultSet rs = CustomerDao.findByEmail(email);
			if(rs.next()) {
				String password_digest = rs.getString("password_digest");
				if(BCrypt.checkpw(password, password_digest)) {
					// password is correct. Return the User object
					return CustomerDao.extractCustomer(rs);
				} else {
					System.out.println("You entered an incorrect password. Ambitious.");
				}
			} 
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Customer register(String name, String email, String password) {
		// hash password with randomly generated salt [BCrypt class]
		String passwordDigest = BCrypt.hashpw(password, BCrypt.gensalt());
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO customers(name, email_addr, password_digest) VALUES(?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2,  email);
			ps.setString(3,  passwordDigest);
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Customer extractCustomer(ResultSet rs) throws SQLException {
		Customer newCust = new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email_addr"), rs.getString("session_token"));
		return newCust;
	}
		
	public static void updateEmail(String email) {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE customers SET email_addr = ? WHERE email_addr = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2,  customer.getEmailAddr());
			
			int res = ps.executeUpdate();
			
			if(res != 0) {
				System.out.println("Email has been updated to: " + email);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updatePassword(String password) {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			
			String passwordDigest = BCrypt.hashpw(password, BCrypt.gensalt());
					
			String sql = "UPDATE customers SET password_digest = ? WHERE email_addr = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, passwordDigest);
			ps.setString(2,  customer.getEmailAddr());
			
			int res = ps.executeUpdate();
			
			if(res != 0) {
				System.out.println("Password has been updated. Do not forget it!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}