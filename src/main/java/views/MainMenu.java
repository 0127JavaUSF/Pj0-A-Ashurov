package views;

import java.util.Scanner;

import util.SessionUtil;

public class MainMenu {
	
	public static void initPrompt() {

//		Greeting.header();
		System.out.println("Would you like to [1] Register or [2] Login?");
		System.out.println();
		
		Scanner in = new Scanner(System.in);
		int ch = in.nextInt();
		
		if(ch == 1) {
			System.out.println();
			
			CustomerView.register();
			
			System.out.println();
		} else {
			System.out.println();
			
			CustomerView.login();
			
			System.out.println();
		}
		
	}

	public static void showMainMenu() {
		
		System.out.println("1. User Options");
		System.out.println("2. Account Options");
		System.out.println("3. Logout");
		System.out.println();
		
		Scanner in = new Scanner(System.in);
		
		int ch = in.nextInt();
		
		switch(ch) {
		case 1: 
			CustomerView.showCustomerMenu();
			break;
		case 2:
			AccountView.showAccountMenu();
			break;
		case 3:
			SessionUtil.logout();
			break;
		default:
			break;
		}
		
	}
}