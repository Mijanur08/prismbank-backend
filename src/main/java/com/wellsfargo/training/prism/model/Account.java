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
	
//	@OneToMany
//	@JoinColumn(name="beneficiary_id")
//	private Beneficiary beneficiaries;
	
}
