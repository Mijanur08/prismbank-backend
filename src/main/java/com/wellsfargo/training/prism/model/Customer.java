package com.wellsfargo.training.prism.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="customers")
public class Customer {
	@Id
	@SequenceGenerator(name="account_no_seq", initialValue=151000000, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="account_no_seq")
	@Column(name="account_number")
	private Long accountNo;
	
	
	private String salutation;
	
	@Column(name="first_name")
	private @NonNull String firstName;
	
	@Column(name="middle_name")
	private  String middleName;
	
	@Column(name="last_name")
	private @NonNull String lastName;
	
	@Column(name="father_name")
	private String fatherName;
	
	@Column(name="aadhar_number", unique=true)
	private @NonNull String aadharNumber; 
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private @NonNull Date dob;
	
	@Column(unique= true)
	private @NonNull String email;
	
	
	@Column(name="phone", unique=true)
	private @NonNull String phoneNo;
	
	@Column(name="account_type")
	private @NonNull String accountType;
	
	private boolean approved;
	
	 /*
     * Modeling with foreign key relationship in JPA.
     * Place @OneToOne on the primary class entity field Dealer.
     * Place @JoinColumn to configure foreign key column dealer_id in the Address class that maps to the primary key
     * column of Dealer class. 
     */
	@OneToOne( cascade = CascadeType.ALL)
	@JoinColumn(name="residential_address")
	private Address resAddress;
	
	@OneToOne( cascade = CascadeType.ALL)
	@JoinColumn(name="permanent_address")
	private Address perAddress;

	public Customer() {
		this.approved = false;
	}
	
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="acct_no")
//	private Account account;
//	
	
	
}
