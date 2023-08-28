package com.wellsfargo.training.prism.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Account {
	@Id
	@Column(name="account_number")
	private Long accountNo;
	
	private  float balance;
	

	private @NonNull String accountType;
	
	public Account( Account a) {
		this.accountNo = a.getAccountNo();
		this.accountType = a.getAccountType();
		this.balance = a.getBalance();
	}
	
}
