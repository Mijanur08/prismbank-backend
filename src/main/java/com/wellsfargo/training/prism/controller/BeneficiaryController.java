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
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Beneficiary;
import com.wellsfargo.training.prism.service.AccountService;
import com.wellsfargo.training.prism.service.BeneficiaryService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/account")
public class BeneficiaryController {
	
	@Autowired
	AccountService aService;
	
	@Autowired
	private BeneficiaryService bService;
	

	@PostMapping(value="/addbeneficiary/{accountNo}")
	public ResponseEntity<String> saveBeneficiary(@Validated @RequestBody Beneficiary b, 
			@PathVariable(value="accountNo") Long accountNo) {
		try {
			Account account = aService.getAccountDetails(accountNo).orElse(null);
			if(account == null) throw (new ResourceNotFoundException("Account does not Exist"));
			b.setAccount(account);
			Beneficiary addBeneficiary = bService.saveBeneficiary(b);
				
			
			if(addBeneficiary == null)
				throw(new  Exception("Issues with server"));
			return ResponseEntity.ok("Beneficiary added successfull with beneficiary id : " + addBeneficiary.getBid());
		}
		catch(Exception e) {			
			return ResponseEntity.badRequest().body("failed to add beneficiary : "+e.getMessage());
		}

	}
	
	@GetMapping(value="/getbeneficiary/{accountNo}")
	public List<Beneficiary> getAllBeneficiary(@PathVariable(value="accountNo") Long accountNo) {
		Account account  = aService.getAccountDetails(accountNo).orElse(null);
		return bService.listAllAddedBeneficiary(account);
	}
	@DeleteMapping(value = "/beneficiary/{bid}")
	public Map<String, Boolean> deleteBeneficiary(@PathVariable(value = "bid") Long bid){
			Map<String, Boolean> mp = new HashMap<>();
			bService.deleteBeneficiary(bid);
			mp.put("deleted", true);
			return mp;
	}
	@PutMapping(value = "/updatebeneficiary")
	public ResponseEntity<String> updateBeneficiary(@Validated @RequestBody Beneficiary b){
		try{
			Beneficiary ben = bService.getSingleBeneficiary(b.getBid()).orElse(null);
			if(ben == null) throw( new ResourceNotFoundException("Beneficiary does not exist"));
			ben.setAccountNo(b.getAccountNo());
			ben.setBeneficiaryName(b.getBeneficiaryName());
			ben.setNickName(b.getNickName());
			ben.setRelation(b.getRelation());
			ben = bService.saveBeneficiary(ben);
			
			if(ben == null) throw (new Exception("Server issues"));
			return ResponseEntity.ok("Successfully Updated Beneficiary");
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body("Unable to Update : "+e.getMessage());
		}
	}
	@GetMapping(value="/beneficiary/{bid}")
	public ResponseEntity<Beneficiary> getSingleBeneficiary(@PathVariable(value = "bid") Long bid){
		try {
			Beneficiary b = bService.getSingleBeneficiary(bid).orElse(null);
			if(b == null) throw( new ResourceNotFoundException("Beneficiary does not exist"));
			return ResponseEntity.ok(b);
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	

}
