package com.wellsfargo.training.prism.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.prism.model.Transaction;
import com.wellsfargo.training.prism.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {
	@Autowired
	private TransactionRepository tRepo;
	
	public Transaction saveTransaction(Transaction t) {
		return tRepo.save(t);
		}
	
	public Optional<Transaction> getSingleTransaction(Long transactionId) {
		return tRepo.findById(transactionId);
	}
	
	public List<Transaction> listAll() {
		return tRepo.findAll();
	}
	
	public void deleteTransaction(Long transactionId) {
		tRepo.deleteById(transactionId);
	}

}
