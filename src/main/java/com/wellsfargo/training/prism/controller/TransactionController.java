package com.wellsfargo.training.prism.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Transaction;
import com.wellsfargo.training.prism.service.TransactionService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService tService;
	
	@Autowired
	private AccountController aController;
	
	@PostMapping(value="/create")
	public ResponseEntity<String> makeTransaction(@RequestBody @Validated Transaction t){
		try {
			float senderAccountBalance = aController.getAccountBalance(t.getSenderAccount().getAccountNo());
			float receiverAccountBalance = aController.getAccountBalance(t.getReceiverAccount().getAccountNo());
			if(t.getAmount()>senderAccountBalance) throw(new Exception("Insufficient Balance"));
			
			Account dummy  = new Account();
			dummy.setAccountNo(t.getSenderAccount().getAccountNo());
			dummy.setBalance(senderAccountBalance-t.getAmount());
			aController.setAccountBalance(dummy);
			dummy.setAccountNo(t.getReceiverAccount().getAccountNo());
			dummy.setBalance(receiverAccountBalance+t.getAmount());
			aController.setAccountBalance(dummy);
			Transaction successful = tService.saveTransaction(t);
			return ResponseEntity.ok("Transaction Successful and transaction id : "+successful.getTransactionId());
			
			
//			catch(Exception e) {
//				return ResponseEntity.badRequest().body("Unable to Complete Transaction : "+e.getMessage());
//			}
			
			
			
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body("Unable to Complete Transaction : "+e.getMessage());
		}
	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<Transaction>> getAllTransactions(){
		try {
			List<Transaction> allTransaction = tService.listAll();
			return ResponseEntity.ok(allTransaction);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping(value="/{accountNo}")
	public ResponseEntity<List<Transaction>> getTransactionsOfAccount(@PathVariable(value="accountNo") Long accountNo){
		
		
		try {
//			List<Transaction> creditTransactions = tService.getCreditTransactions(accountNo);
//			List<Transaction> debitTransactions = tService.getDebitTransactions(accountNo);
//			
//			List<Transaction> transactions;
//			transactions = creditTransactions;
//			if(transactions == null) transactions=debitTransactions;
//			else if(debitTransactions != null) transactions.addAll(debitTransactions);
			List<Transaction> transactions = tService.getTransactionsOfAccount(accountNo);
			
			if(transactions != null) {
				//TODO: Sorting the list according to time.
				return ResponseEntity.ok(transactions);
			}
			return ResponseEntity.badRequest().body(new ArrayList<>());
		}
		catch(Exception e) {
			System.out.println("Hi  I am running");
			return ResponseEntity.status(HttpStatus. INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
