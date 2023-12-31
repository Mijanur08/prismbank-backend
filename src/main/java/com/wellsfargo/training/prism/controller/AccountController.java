package com.wellsfargo.training.prism.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Customer;
import com.wellsfargo.training.prism.service.AccountService;
import com.wellsfargo.training.prism.service.CustomerService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/account")
public class AccountController {
	
	@Autowired
	AccountService aService;
	
	@Autowired
	private CustomerService cService;
	
	@PostMapping(value="/approve")
	public ResponseEntity<String> approveAccount(@RequestBody @Validated Account newAccount){
		try {
			Customer c = cService.findAccount(newAccount.getAccountNo());
			if(c == null) 
				throw (new ResourceNotFoundException("Account does not exist"));
			c.setApproved(true);
			c = cService.registerCustomer(c);
			newAccount.setAccountType(c.getAccountType());;
			Account acc = aService.saveAccountDetails(newAccount);

			if(acc == null)
				throw (new Exception("Unable to open Account"));
			return ResponseEntity.ok("Account with account number :"+acc.getAccountNo()+"is approved by admin");

		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body("Failed to approve Account by admin : " +e.getMessage());
		}
	}
	@GetMapping(value="/balance/{accountNo}")
	public float getAccountBalance(@PathVariable(value="accountNo") Long accountNo) throws 
		ResourceNotFoundException{
		 Account acc = aService.getAccountDetails(accountNo).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
		 return acc.getBalance();
	}
	@PutMapping(value="/setbalance")
	public float setAccountBalance(@RequestBody @Validated Account setBal) throws 
		ResourceNotFoundException{
		 Account acc = aService.getAccountDetails(setBal.getAccountNo()).orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
		 acc.setBalance(setBal.getBalance());
		 aService.saveAccountDetails(acc);
		 return acc.getBalance();
	}
	
}
