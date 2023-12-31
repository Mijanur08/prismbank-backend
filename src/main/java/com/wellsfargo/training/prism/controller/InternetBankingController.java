package com.wellsfargo.training.prism.controller;

import java.util.HashMap;
import java.util.Map;

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
		try {
			Long accountNumber = ibu.getAccountNumber();
			Customer c = cService.findAccount(accountNumber);
			if(c==null) throw (new ResourceNotFoundException("Account does not exist"));
					
				ibu.setEmail(c.getEmail());
				InternetBankingUser registeredUser = IBService.registerUser(ibu);
				
				if(registeredUser == null) throw(new Exception("Issues with server"));
					
			return ResponseEntity.ok("You are successfully registered for Internet Banking ");
				
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body("Registration Failed : "+e.getMessage());
			
		}
	}
	@PostMapping(value="/login")
	public Map<String,Long> loginCustomer(@RequestBody @Validated InternetBankingUser ibu) throws ResourceNotFoundException{

		Map<String, Long> response = new HashMap<>();
        Long flag = 0L;
		String userid = ibu.getEmail();
		String password = ibu.getPassword();
		InternetBankingUser login= IBService.loginCustomer(userid).orElseThrow(() ->
		new ResourceNotFoundException("Customer not found for this userid :: "));
		
		if(userid.equals(login.getEmail()) && password.equals(login.getPassword()))
		{

			flag = 1L;


		}
		response.put("login", flag);
		response.put("accountNo",login.getAccountNumber() );


		return response;
	}

}
