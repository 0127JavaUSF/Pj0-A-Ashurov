package views;

import java.util.Scanner;

import dao.CustomerDao;
import util.SessionUtil;
import util.Validation;

public class CustomerView {
	
//	 1. Update email
//	 2. Update Password

	public static void showCustomerMenu() {
		System.out.println();
		System.out.println("1. Update Email");
		System.out.println("2. Update Password");
		System.out.println("3. Go back");
		System.out.println();
		
		Scanner in = new Scanner(System.in);
		
		switch(in.nextInt()) {
		case 1:
			CustomerView.updateEmail();
			CustomerView.showCustomerMenu();
			break;
		case 2: 
			CustomerView.updatePassword();
			CustomerView.showCustomerMenu();
			break;
		case 3: 
			MainMenu.showMainMenu();
			System.out.println();
			break;
		default:
			break;
		}
		
	}
	

	public static void login() {
		System.out.println("Enter your email and password to login: ");
		System.out.println();
		Scanner in = new Scanner(System.in);
		SessionUtil.login(in.nextLine(), in.nextLine());
		System.out.println();
	}
	
	public static void register() {
		System.out.println("Enter your name, email, and password: ");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		String email = in.nextLine();
		CustomerDao.register(name, email, in.nextLine());
		System.out.println("Customer account has been created. Please login with your details.");
	}

	public static void updateEmail() {
		
		String email = "";
		int i = 0;
		boolean flag = false;
		
		while(!flag && i < 5) {
			System.out.println("Enter your new email: ");
			Scanner in = new Scanner(System.in);
			email = in.nextLine();
			
			if(Validation.isValidEmail(email)) {
				flag = true;
				CustomerDao.updateEmail(email);
			}
			i++;
		}
		// two ways to exit the WHILE loop: User run out of tries or email is valid
		// flag = true means EMAIL was valid. Otherwise, user ran out of tries
		if(flag == false) {
			// meaning: user has run out of tries
			System.out.println("Please try again later.");	
		}
	}
	
	public static void updatePassword() {
		
		int i = 0;
		boolean flag = true;
		while(i < 5) {
			System.out.println("Enter your new password: ");
			Scanner in = new Scanner(System.in);
			String password = in.nextLine();
			
			if(Validation.isValidPassword(password)) {
				CustomerDao.updatePassword(password);
				flag = false;
				break;
			}
			i++;
		}
		if(flag == true) {
			System.out.println("Please try again later.");	
		}
	}
	
}

