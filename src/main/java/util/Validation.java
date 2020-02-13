package util;

public class Validation {

	public static boolean isValidPassword(String password) {
		if(password.length() > 6) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isValidEmail(String email) {
		
		if(email.length() == 0) {
			return false;
		}
		
		int idx = email.indexOf("@mango.com");
		
		if(idx == -1) {
			return false;
		}
		
		return true;
		
	}
	
	public static boolean isValidName(String name) {
		return name.length() == 0 ? false : true;
	}
	
}