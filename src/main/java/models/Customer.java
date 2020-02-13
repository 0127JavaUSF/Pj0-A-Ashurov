package models;

/*
	-- customer_id
	-- name
	-- email_addr
	-- password_digest
	-- session_token
*/

public class Customer {
	
	private int customerId;
	private String name;
	private String emailAddr;
	private String sessionToken;

	public Customer(int customerId, String name, String emailAddr, String session_token) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.emailAddr = emailAddr;
		this.sessionToken = session_token;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + ((emailAddr == null) ? 0 : emailAddr.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sessionToken == null) ? 0 : sessionToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId != other.customerId)
			return false;
		if (emailAddr == null) {
			if (other.emailAddr != null)
				return false;
		} else if (!emailAddr.equals(other.emailAddr))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sessionToken == null) {
			if (other.sessionToken != null)
				return false;
		} else if (!sessionToken.equals(other.sessionToken))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Customer [customerId=" + customerId + ", name=" + name + ", emailAddr=" + emailAddr + ", sessionToken="
				+ sessionToken + "]";
	}
	
}