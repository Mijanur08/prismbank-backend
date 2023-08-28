package com.wellsfargo.training.prism.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.prism.model.Customer;
import com.wellsfargo.training.prism.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerRepository cRepo;
	
	public Customer registerCustomer(Customer c) {
		return cRepo.save(c);
	}
	public Customer findAccount(Long accountNumber) {
		Customer searchResult = cRepo.findById(accountNumber).orElse(null);
		return searchResult;
	}
	public Optional<Customer> getSingleCustomer(Long accountNo){
		return cRepo.findById(accountNo);
	}
	public void deleteCustomer(Long accountNo) {
		cRepo.deleteById(accountNo);
	}
	public List<Customer> listAll() {
		return cRepo.findAll();
	}

}
