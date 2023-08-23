package com.wellsfargo.training.prism.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="internet_banking_user")
public class InternetBankingUser {
	@Id
	@Column(name ="account_number")
	private @NonNull Long accountNumber;
	
	private @NonNull String password;
	
	@Column(name = "transaction_password")
	private @NonNull String transactionPassword;
	
	private String email;

	public InternetBankingUser() {
		// TODO Auto-generated constructor stub
	}
	
	/*public InternetBankingUser(Long accountNumber, String password,String transactionPassword, String email) {
		this.accountNumber = accountNumber;
		this.password = password;
		this.transactionPassword = transactionPassword;
		this.email = email;
	}*/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		System.out.println("First :"+password);
		Base64.Encoder encoder = Base64.getEncoder();  // encrypt password in database field
        String normalString = password;
        System.out.println(password +" "+"normal :"+normalString);
        String encodedString = encoder.encodeToString(
        normalString.getBytes(StandardCharsets.UTF_8) );
        this.password = encodedString;
//		this.password = password;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String password) {
		System.out.println("First :"+password);
		Base64.Encoder encoder = Base64.getEncoder();  // encrypt password in database field
        String normalString = password;
        System.out.println(password +" "+"normal :"+normalString);
        String encodedString = encoder.encodeToString(
        normalString.getBytes(StandardCharsets.UTF_8) );
		this.transactionPassword = encodedString;
	}
	
	
}
