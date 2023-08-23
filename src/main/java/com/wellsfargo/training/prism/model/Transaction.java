//package com.wellsfargo.training.prism.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//import lombok.Setter;
//
//@Entity
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "transactions")
//public class Transaction {
//	@Id
//	@SequenceGenerator(name="transaction_no_seq", initialValue=100000000, allocationSize=1)
//	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="transaction_no_seq")
//	private Long transactionId;
//	
//	private Long senderAccount;
//	
//	private Long receiverAccount;
//	
//	private @NonNull String mode;
//	private @NonNull String remarks;
//	private float amount;
//}
