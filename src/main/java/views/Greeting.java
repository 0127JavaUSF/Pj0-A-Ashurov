package views;

import java.util.Calendar;

import util.SessionUtil;

public class Greeting {
	
	public static void header() {
	
			Calendar calendar = Calendar.getInstance();
			System.out.println(calendar.getTime());
			System.out.println("************************************************");
			System.out.println("**** Welcome to the Spirit of the West Inc. ****");
			System.out.println("************************************************");
			if(SessionUtil.isLoggedIn()) {
				System.out.println("Hello " + SessionUtil.getCurrentCustomer().getName() + ",");	
			}
			System.out.println();

	}
	
	public static void footer() {
		System.out.println("Water is the new gasoline (C)");
	}
	
}
