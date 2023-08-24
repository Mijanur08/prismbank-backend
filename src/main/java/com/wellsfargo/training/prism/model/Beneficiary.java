package com.wellsfargo.training.prism.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor //for default constructor
@Getter
@Setter
@Entity
@Table(name="beneficiary")
public class Beneficiary {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bid;
	@Column(name="beneficiary_name")
	private @NonNull String beneficiaryName;
	@Column(name="account_no")
	private @NonNull Long accountNo;

	private @NonNull String relation;
	@Column(name ="nickname")
	private String nickName;

	@ManyToOne
	@JoinColumn(name="beneficiary_of")
	private Account account;



}
