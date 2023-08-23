package com.wellsfargo.training.prism.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
	@Autowired
	AccountRepository aRepo;
	
	public Account saveAccountDetails(Account c) {
		return aRepo.save(c);
	}
	
	public Optional<Account> getAccountDetails(Long accountNo){
		return aRepo.findById(accountNo);
	}
	public void deleteAccount(Long accountNo) {
		Account existingAccount = aRepo.findById(accountNo).orElse(null);
		if(existingAccount != null)
		 aRepo.deleteById(accountNo);
	}
	public List<Account> listAllAccount() {
		return aRepo.findAll();
	}
}
