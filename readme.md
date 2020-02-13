# Spirit of the West Inc.

Brief:

	- While the underlying structure isn't unique, the theme for this project 
	is based on an award winning animated movie by Gore Verbinski - Rango.
	- General theme: Imagine times and places where water is scarce and people are 
	awarded to store their water in a central place, bank, either to earn some extra
	water or to securely store it for the future use.
	
	Technologies used:
	
		- Java 
		- PostgreSQL
			 - Relations:
			 	- Customer
			 	- Accounts
			 	- CustomerAccounts
	
General structure:
	
	- Customer Features:

		- User Registration
			- Basic validations: Password length must be at least 7 characters.
			- User password is hashed using BCrypt Java library
			  	
			BCrypt.hashpw(password, BCrypt.gensalt());
				
			- Plain text password is later checked against encrypted version
				- findByCredentials(email, password)
				
				// ... SQL query
				String password_digest = rs.getString("password_digest");
				if(BCrypt.checkpw(password, password_digest)) {
					return CustomerDao.extractCustomer(rs);
				} else {
					System.out.println("You entered an incorrect password. Ambitious.");
				}
				
		- User Login
			- One user logs in, global object keeps track of its session via randomly generated token 
			that persists in the database during that session
				- resetSessionToken() - Makes sure session belongs to the currently logged in user
					- generateSessionToken() - generates a random alpha numeric string of length 18
			- Program keeps track of currently logged in user. 
				- If there is none, then program shows Register or Login option to user
		- User Logout
			- resetSessionToken()
				- reset's the session token for the user
				- removes current user's session
		- Password / Email update
	
	- Account Features:
	
		- Create Account: Single / Joint
		- Close Account 
			- Closes children accounts before closing the parent account
				- Water Back Guarantee
		- Transfer Water to Other Customers
			- To do: Work with multiple accounts.
				- Display account list and let user choose from which account they would like to withdraw
		- Check Balance
		- Add User to Joint Account
			- Checks if user exists
			- Checks if current user is account owner

Future Implementations
	
	- Edge cases when user has several accounts
		- Let the user make a choice
			- i.e. Show a table that contains all of their accounts and let me them choose which account to operate on
	- Remove child account from parent account
		- Simple implementation
			- DELETE query if child account points to parent account
	- Display Accounts
		- Show all accounts owned by user
			- Need to work with: printf / SQL query and extract account from ResultSet (loop) 

Water is the new gasoline (C) - Mayor West. 