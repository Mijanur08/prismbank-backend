package com.wellsfargo.training.prism.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wellsfargo.training.prism.controller.TransactionController.TransactionDetails;
import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Transaction;
import com.wellsfargo.training.prism.service.TransactionService;

@SpringBootTest
class TransactionControllerTest {
	
	Transaction transaction;
	
	@MockBean
	private TransactionService tService;
	
	@Autowired
	private TransactionController transactionController;
	
	@MockBean
	private TransactionDetails transactionDetails;

	@BeforeEach
	void setUp() throws Exception {
		transaction = new Transaction();
	}

	@AfterEach
	void tearDown() throws Exception {
		transaction = null;
	}

	@Test
	void testMakeTransaction() throws ResourceNotFoundException{
		
		
		Account account1 = new Account();
		account1.setAccountNo(151000001L);
		account1.setBalance(50000);
		account1.setAccountType("Savings");
		
		Account account2 = new Account();
		account2.setAccountNo(151000002L);
		account2.setBalance(40000);
		account2.setAccountType("Savings");
		
		transaction.setTransactionId(1L);
		transaction.setSenderAccount(account1);
		transaction.setReceiverAccount(account2);
		transaction.setMode("RTGS");
		transaction.setRemark("Donation");
		transaction.setAmount(1000);
		LocalDateTime now = LocalDateTime.now();
		transaction.setTimestamp(now);
		
		transactionDetails.tid = transaction.getTransactionId();
		transactionDetails.fromAccount = String.valueOf(account1.getAccountNo());
		transactionDetails.toAccount = String.valueOf(account2.getAccountNo());
		transactionDetails.mode = transaction.getMode();
		transactionDetails.remark = transaction.getRemark();
		transactionDetails.amount = transaction.getAmount();
		transactionDetails.timestamp = transaction.getTimestamp();
		transactionDetails.transactionPassword = "1234";
		transactionDetails.type = "Online";
		
	}

	@Test
	void testCashDepositByAdmin() {
		
		transaction.setAmount(10000);
		transaction.setMode("Cash");
	}

	@Test
	void testCashWithdrawByAdmin() {
		
		transaction.setAmount(10000);
		transaction.setMode("Cash");
	}

	@Test
	void testGetAllTransactions() {
		
		Account account1 = new Account();
		account1.setAccountNo(151000001L);
		account1.setBalance(50000);
		account1.setAccountType("Savings");
		
		Account account2 = new Account();
		account2.setAccountNo(151000002L);
		account2.setBalance(40000);
		account2.setAccountType("Savings");
		
		transaction.setTransactionId(1L);
		transaction.setSenderAccount(account1);
		transaction.setReceiverAccount(account2);
		transaction.setMode("RTGS");
		transaction.setRemark("Donation");
		transaction.setAmount(1000);
		LocalDateTime now = LocalDateTime.now();
		transaction.setTimestamp(now);
	}

	@Test
	void testGetTransactionsOfAccount() {
		Account account1 = new Account();
		account1.setAccountNo(151000001L);
		account1.setBalance(50000);
		account1.setAccountType("Savings");
		
		Account account2 = new Account();
		account2.setAccountNo(151000002L);
		account2.setBalance(40000);
		account2.setAccountType("Savings");
		
		transaction.setTransactionId(1L);
		transaction.setSenderAccount(account1);
		transaction.setReceiverAccount(account2);
		transaction.setMode("RTGS");
		transaction.setRemark("Donation");
		transaction.setAmount(1000);
		LocalDateTime now = LocalDateTime.now();
		transaction.setTimestamp(now);
	}

}
