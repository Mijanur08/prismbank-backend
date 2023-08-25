package com.wellsfargo.training.prism.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
	@Id
	@SequenceGenerator(name="transaction_no_seq", initialValue=100000000, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="transaction_no_seq")
	@Column(name ="transaction_id")
	private Long transactionId;
	
	@ManyToOne
	@JoinColumn(name = "sender_account")
	private Account senderAccount;
	
	@ManyToOne
	@JoinColumn(name = "receiver_account")
	private Account receiverAccount;
	
	private @NonNull String mode;
	private @NonNull String remarks;
	private float amount;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();
}
