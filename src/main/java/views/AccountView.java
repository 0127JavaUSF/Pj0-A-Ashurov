package views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dao.AccountDao;
import dao.CustomerDao;
import models.Account;
import models.Customer;
import util.SessionUtil;

public class AccountView {
//	 1. Create a Type Account
//	 2. Deposit
//	 3. Withdraw
//	 4. Transfer Money
//	 5. Close Account

	public static void showAccountMenu() {
		System.out.println();
		System.out.println("1. Create a Checking / Saving Account");
		System.out.println("2. Deposit Money");
		System.out.println("3. Withdraw Money");
		System.out.println("4. Transfer Money");
		System.out.println("5. Check Balance");
		System.out.println("6. Close Account");
		System.out.println("7. Add User to Joint Account");
		System.out.println("8. Go back");
		System.out.println();
		
		Scanner in = new Scanner(System.in);
		int ch = in.nextInt();
				
		switch(ch) {
		case 1:
			AccountView.createAccount();
			AccountView.showAccountMenu();
			break;
		case 2:
			AccountView.deposit();
			AccountView.showAccountMenu();
			break;
		case 3:
			AccountView.withdraw();
			AccountView.showAccountMenu();
			break;
		case 4:
			AccountView.transfer();
			AccountView.showAccountMenu();
			break;
		case 5:
			AccountView.checkBalance();
			AccountView.showAccountMenu();
			break;
		case 6:
			AccountView.closeAccount();
			MainMenu.showMainMenu();
			break;
		case 7:
			AccountView.addUserToJointAccount();
			MainMenu.showMainMenu();
			break;
		case 8:
			MainMenu.showMainMenu();
			System.out.println();
			break;
		default:
			break;			
		}
		
	}
	
	public static void createAccount() {
		
		System.out.println("Hi " + SessionUtil.getCurrentCustomer().getName() + "! We are glad that you want to join us.");
		System.out.println("Is this a [1] Single or [2] Joint account?");
		
		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();
		
		if(choice == 1 || choice == 2) {
			boolean joint = false;
			
			if(choice == 2) {
				joint = true;
			}

			System.out.println("Choose the type of account you want: ");
			System.out.println("1. RESERVE. Reserve your water in our safe water bank.");
			System.out.println("2. DIVIDEND. Earn water each month for being our loyal customer.");
		
			in = new Scanner(System.in);
			choice = in.nextInt();
			String type;
			double interest;
			if(choice == 1) {
				type = "RESERVE";
				interest = 0.0;
			} else {
				type = "DIVIDEND";
				interest = 2.5;
			}
			System.out.println("How many liters would you like to deposit? Minimum is 50 Liters to open an account.");
			in = new Scanner(System.in);
			double balance = in.nextDouble();
			AccountDao.createAccount(balance, interest, type, joint, SessionUtil.getCurrentCustomer().getCustomerId());		
			System.out.println("You have successfully opened a " + type + " account with " + "the Spirit of the West Inc.");
		}
	}
		
	public static void addUserToJointAccount() {
		
		Scanner in;
		
		System.out.println("Enter the email address of the user you would like to add.");
		
		in = new Scanner(System.in);
		String email = in.nextLine();
		
		try {
			// this is account owner
			Account account = AccountDao.findAccountByCredentials(SessionUtil.getCurrentCustomer().getCustomerId());
			
			ResultSet rs = CustomerDao.findByEmail(email);
			
			if(rs.next()) {
				// about to add this user
				int id = rs.getInt("customer_id");
				String name = rs.getString("name");
				AccountDao.createAccount(id, account.getAccountId());				
				System.out.println("You have successfully added " + name + " to your account.");
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAccount() {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		Account account = AccountDao.findAccountByCredentials(customer.getCustomerId());
		if(account.getAccountOwnerId() == customer.getCustomerId()) {
			if(account.getBalance() > 0) {
				System.out.println("Do not forget to pick up " + account.getBalance() + "L of water from the front desk!");
				System.out.println("Sad to see you go " + customer.getName());
				AccountDao.closeAccount(customer, account.getAccountId());
			}
		} else {
			System.out.println("You cannot close this account since you are not the owner.");
		}
	}
	
	public static void deposit() {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		System.out.println("Enter the amount of today's deposit: ");
		Scanner in = new Scanner(System.in);
		double amount = in.nextDouble();
		if(amount <= 0) {
			System.out.println("Deposit amount should be greater than 0L");
		} else {
			AccountDao.updateBalance(customer, amount);
			System.out.println("You have deposited " + amount + "L of water to your account.");
		}		
	}
	
	public static void withdraw() {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		System.out.println("How much would you like to withdraw today: ");
		Scanner in = new Scanner(System.in);
		double amount = in.nextDouble();
		Account account = AccountDao.findAccountByCredentials(customer.getCustomerId());
		if(account.getBalance() < amount) {
			System.out.println("You can not withdraw more than you have!");
			System.out.println("Currently, you have " + account.getBalance() + "L of water left.");
		} else {
			AccountDao.updateBalance(customer, -1*amount);		
			System.out.println("You have withdrawn " + amount + "L of water from your account.");
		}
		
	}	
	
	public static void checkBalance() {
		
		Customer customer = SessionUtil.getCurrentCustomer();
		Account account = AccountDao.findAccountByCredentials(customer.getCustomerId());
		
		System.out.println(AccountDao.showBalance(account) + " L of water is in your account!");
		
	}
	
	public static void transfer() {
		
		System.out.println("Enter email address and amount that you want to transfer.");
		Scanner in = new Scanner(System.in);
		
		Customer customer = SessionUtil.getCurrentCustomer();
		
		String emailTo = in.nextLine();
		double amount = in.nextDouble();
		
		// AccountDao.transferTo(emailFrom, emailTo, amount);
		AccountDao.transferTo(customer.getEmailAddr(), emailTo, amount);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}