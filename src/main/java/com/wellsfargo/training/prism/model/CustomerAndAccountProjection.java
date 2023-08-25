package com.wellsfargo.training.prism.model;

public class CustomerAndAccountProjection {
	
	private Long accountNo;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	private String accountType;
	private float balance;
	
	public CustomerAndAccountProjection() {
		// TODO Auto-generated constructor stub
	}

	public CustomerAndAccountProjection(Long accountNo, String firstName, String lastName, String email, String phoneNo,
			String accountType, float balance) {
		this.accountNo = accountNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.accountType = accountType;
		this.balance = balance;
	}

	public Long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
