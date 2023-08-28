package com.wellsfargo.training.prism.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Customer;
import com.wellsfargo.training.prism.service.CustomerService;
import com.wellsfargo.training.prism.service.InternetBankingService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/account")
public class CustomerController {
	@Autowired
	private CustomerService cService;
	
	@Autowired 
	private InternetBankingService ibService;
	
	@PostMapping(value="/create")
	public ResponseEntity<String> createAccount(@RequestBody @Validated Customer c){
		Customer accountHolder = cService.registerCustomer(c);
		
		if(accountHolder != null)
		return ResponseEntity.ok("Your account is created and account number is : "+accountHolder.getAccountNo());
		
		return ResponseEntity.badRequest().body("Failed to open Account");
	}
	@GetMapping(value="/customers")
	public List<Customer> getAllCustomers(){
		return cService.listAll();
	}
	@PutMapping(value="/customer/{accountNo}")
	public ResponseEntity<Customer> updateAccount(@PathVariable(value="accountNo")
	Long accountNo, @Validated @RequestBody Customer c) throws ResourceNotFoundException{
		Customer prevAccountDetails= cService.getSingleCustomer(accountNo).
				orElseThrow(()-> new 
						ResourceNotFoundException("Account Not Found for this Account Number"+accountNo));
					prevAccountDetails.setFirstName(c.getFirstName());
					prevAccountDetails.setMiddleName(c.getMiddleName());
					prevAccountDetails.setLastName(c.getLastName());
					prevAccountDetails.setFatherName(c.getFatherName());
					prevAccountDetails.setSalutation(c.getSalutation());
					prevAccountDetails.setEmail(c.getEmail());
					prevAccountDetails.setPhoneNo(c.getPhoneNo());
					prevAccountDetails.setAadharNumber(c.getAadharNumber());
					prevAccountDetails.setDob(c.getDob());
					prevAccountDetails.setAccountType(c.getAccountType());
					prevAccountDetails.setResAddress(c.getResAddress());
					prevAccountDetails.setPerAddress(c.getPerAddress());
					
					final Customer updatedAccount = cService.registerCustomer(prevAccountDetails);
					return ResponseEntity.ok().body(updatedAccount);
	}
	
	@GetMapping(value="/customer/{accountNo}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value="accountNo") Long accountNo)
		throws ResourceNotFoundException {
			Customer c = cService.getSingleCustomer(accountNo).
					orElseThrow(() -> new ResourceNotFoundException("Customer not found for this account number " + accountNo));
			return ResponseEntity.ok().body(c);
	}

	
  @DeleteMapping(value="/customer/{accountNo}")
	public Map<String,Boolean> deleteCustomer(@PathVariable(value="accountNo") Long accountNo)
	    throws ResourceNotFoundException {
		cService.getSingleCustomer(accountNo).
				orElseThrow(() -> new ResourceNotFoundException("Customer not found for this account number " + accountNo));
		cService.deleteCustomer(accountNo);
		ibService.deleteIBUser(accountNo);
		//aService.deleteAccount(accountNo);
		
		
		Map<String,Boolean> response=new HashMap<>();
		response.put("Customer Deleted", Boolean.TRUE);
		return response;
	}

}
