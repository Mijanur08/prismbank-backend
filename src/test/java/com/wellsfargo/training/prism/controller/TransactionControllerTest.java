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
	void testMakeTransaction() {
		
		
		Account account1 = new Account();
		Account account2 = new Account();
		transaction.setSenderAccount(account1);
		transaction.setReceiverAccount(account2);
		transaction.setMode("RTGS");
		transaction.setRemark("Donation");
		transaction.setAmount(1000);
		LocalDateTime now = LocalDateTime.now();
		transaction.setTimestamp(now);
		
		when(tService.saveTransaction(any(Transaction.class))).thenReturn(transaction);
		ResponseEntity<String> re = transactionController.makeTransaction(transactionDetails);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Transaction Successful and transaction id : "+transaction.getTransactionId(), re.getBody());
		verify(tService,times(1)).saveTransaction(any(Transaction.class));

		
	}

	@Test
	void testCashDepositByAdmin() {
		fail("Not yet implemented");
	}

	@Test
	void testCashWithdrawByAdmin() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllTransactions() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTransactionsOfAccount() {
		fail("Not yet implemented");
	}

}
