package com.wellsfargo.training.prism.controller;

import java.time.LocalDateTime;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Transaction;
import com.wellsfargo.training.prism.service.AccountService;
import com.wellsfargo.training.prism.service.TransactionService;



@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService tService;
	
	@Autowired
	private AccountService aService;
	
	@Autowired
	private AccountController aController;
	
	public static class TransactionDetails{
		public Long tid;	
		public String toAccount;
		public String fromAccount;
		public float amount;
		public String type;
		public String mode;
		public String remark;
		
		@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
		public LocalDateTime timestamp;	
		public float balance;
	};
	
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
			Account ac = aService.getAccountDetails(t.getSenderAccount().getAccountNo()).orElse(null);
			t.setSenderAccount(ac);
			
			dummy.setAccountNo(t.getReceiverAccount().getAccountNo());
			dummy.setBalance(receiverAccountBalance+t.getAmount());
			aController.setAccountBalance(dummy);
			ac = aService.getAccountDetails(t.getReceiverAccount().getAccountNo()).orElse(null);
			t.setReceiverAccount(ac);
			
			Transaction successful = tService.saveTransaction(t);
			return ResponseEntity.ok("Transaction Successful and transaction id : "+successful.getTransactionId());
			
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
	public ResponseEntity<List<TransactionDetails>> getTransactionsOfAccount
	(@PathVariable(value="accountNo") Long accountNo){
		try {			
			Account account = aService.getAccountDetails(accountNo).orElse(null);
			if(account == null) throw new ResourceNotFoundException("Account Not Found");
			List<Transaction> creditTransactions = tService.getCreditTransactions(account);
			List<Transaction> debitTransactions = tService.getDebitTransactions(account);
			
			List<TransactionDetails> result = new ArrayList<>();
			
			for(Transaction t : creditTransactions) {
				TransactionDetails td = new TransactionDetails();
				td.tid = t.getTransactionId();
				td.type = "Deposit";
				td.mode = t.getMode();
				td.fromAccount = td.mode.equals("cash")?"": String.valueOf(t.getSenderAccount().getAccountNo());
				td.toAccount = "self";
				td.amount=t.getAmount();
				td.remark=t.getRemarks();
				td.timestamp = t.getTimestamp();
				td.balance = t.getReceiverAccount().getBalance();
				result.add(td);
			}
			for(Transaction t : debitTransactions) {
				TransactionDetails td = new TransactionDetails();
				td.tid = t.getTransactionId();
				td.type = "Withdrawal";
				td.mode = t.getMode();
				td.toAccount = td.mode.equals("cash")?"": String.valueOf(t.getReceiverAccount().getAccountNo());
				td.fromAccount = "self";
				td.amount=t.getAmount();
				td.remark=t.getRemarks();
				td.timestamp = t.getTimestamp();
				td.balance = t.getReceiverAccount().getBalance();
				result.add(td);
			}
			result.sort((o1,o2)
					-> o2.timestamp.compareTo(o1.timestamp));
			return ResponseEntity.ok(result);

		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus. INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
