package com.wellsfargo.training.prism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Customer;
import com.wellsfargo.training.prism.model.InternetBankingUser;
import com.wellsfargo.training.prism.service.CustomerService;
import com.wellsfargo.training.prism.service.InternetBankingService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/ib")
public class InternetBankingController {
	@Autowired
	private InternetBankingService IBService;
	@Autowired
	private CustomerService cService;
	
	@PostMapping(value="/register")
	public ResponseEntity<String> createIBUser(@RequestBody @Validated InternetBankingUser ibu){
		Long accountNumber = ibu.getAccountNumber();
		Customer c = cService.findAccount(accountNumber);
		if(c!=null) {
				
			ibu.setEmail(c.getEmail());
			InternetBankingUser registeredUser = IBService.registerUser(ibu);
			
			if(registeredUser != null)
				return ResponseEntity.ok("You are successfully registered for Internet Banking ");
			
			return ResponseEntity.badRequest().body("Registration Failed");
		}
		return ResponseEntity.badRequest().body("Account does not exist");
	}
	@PostMapping(value="/login")
	public Boolean loginCustomer(@RequestBody @Validated InternetBankingUser ibu) throws ResourceNotFoundException{
		Boolean a = false;

		String userid = ibu.getEmail();
		String password = ibu.getPassword();
		InternetBankingUser login= IBService.loginCustomer(userid).orElseThrow(() ->
		new ResourceNotFoundException("Customer not found for this userid :: "));
		
		if(userid.equals(login.getEmail()) && password.equals(login.getPassword()))
		{
			a=true;

		}

		return a;
	}
}
