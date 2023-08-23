package com.wellsfargo.training.prism.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private @NonNull String beneficiaryName;
	private String nickName;
	private @NonNull Long accountNo;


}
