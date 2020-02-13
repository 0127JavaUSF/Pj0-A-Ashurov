// #TODO: carriage returns - what
		
import java.sql.SQLException;

import util.SessionUtil;
import views.Greeting;
import views.MainMenu;
		
public class mainControl {
	public static void main(String[] args) throws SQLException {
				
		/*
		 
		 User Options
		 
		 1. Update email
		 2. Update Password
		 
		 Accounts Options
		 
		 1. Create a Type Account
		 2. Deposit
		 3. Withdraw
		 4. Transfer Money
		 5. Edit User
		 6. Close Account
		 
		 Go Back Option
		 
		 */
		
		while(!SessionUtil.isLoggedIn()) {
			
			MainMenu.initPrompt();
			
			if(SessionUtil.isLoggedIn()) {
				
				Greeting.header();
				MainMenu.showMainMenu();
			
			}
		}
				
		// Greeting.footer();
	
	}
	
}		