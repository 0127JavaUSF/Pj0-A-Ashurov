package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import dao.CustomerDao;
import models.Customer;

public class SessionUtil {

	private static Customer currentCustomer;
	private static String sessionToken;
	
	public SessionUtil() {
		super();
	}
	
	public static String generateSessionToken() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	// has token but needs to be reset to avoid collision with SessionUtil.sessionToken
	public static String resetSessionToken(Customer customer) {
		String tkn = SessionUtil.generateSessionToken();
		customer.setSessionToken(tkn);
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE customers SET session_token = ? WHERE email_addr = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, customer.getSessionToken());
			ps.setString(2, customer.getEmailAddr());
			int rs = ps.executeUpdate();
			if(rs != 0) {
				return customer.getSessionToken();	
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void login(String email, String password) {
		Customer customer = CustomerDao.findByCredentials(email, password);
		if(customer != null) {
			// sets the Session's token = customer's token that was reset
			SessionUtil.setSessionToken(SessionUtil.resetSessionToken(customer));
			if(SessionUtil.getSessionToken() == customer.getSessionToken()) {
				SessionUtil.setCurrentCustomer(customer);
			} else {
				System.out.println("Token mismatch. Something went wrong.");
			}
		} else {
			System.out.println("Customer with these details was not found.");
		}
	}
	
	public static boolean isLoggedIn() {
		if(SessionUtil.sessionToken == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void logout() {
		SessionUtil.resetSessionToken(SessionUtil.currentCustomer);
		SessionUtil.setCurrentCustomer(null);
		SessionUtil.setSessionToken(null);
	}	
	
	public static Customer getCurrentCustomer() {
		return SessionUtil.currentCustomer;
	}
	
	public static String getSessionToken() {
		return SessionUtil.sessionToken;
	}

	public static void setSessionToken(String sessionToken) {
		SessionUtil.sessionToken = sessionToken;
	}
	
	public static void setCurrentCustomer(Customer customer) {
		SessionUtil.currentCustomer = customer;
	}	
}