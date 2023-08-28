package com.wellsfargo.training.prism.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wellsfargo.training.prism.exception.ResourceNotFoundException;
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.service.AccountService;

@SpringBootTest
class AccountControllerTest {
	
	Account account;
	
	@MockBean
	private AccountService accountService;
	
	@Autowired
	private AccountController accountController;

	@BeforeEach
	void setUp() throws Exception {
		account = new Account();
	}

	@AfterEach
	void tearDown() throws Exception {
		account = null;
	}

	@Test
	void testApproveAccount() {
		
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		when(accountService.saveAccountDetails(any(Account.class))).thenReturn(account);
		ResponseEntity<String> re = accountController.approveAccount(account);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Account with account number :"+account.getAccountNo()+"is approved by admin", re.getBody());
		verify(accountService,times(1)).saveAccountDetails(any(Account.class));
	}

	@Test
	void testGetAccountBalance() throws ResourceNotFoundException{
		
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		when(accountService.getAccountDetails(151000001L)).thenReturn(Optional.of(account));
		float re = accountController.getAccountBalance(151000001L);
		assertEquals(50000, account.getBalance());
		verify(accountService,times(1)).getAccountDetails(151000001L);
		
	}

	@Test
	void testSetAccountBalance() throws ResourceNotFoundException{
		
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		when(accountService.getAccountDetails(151000001L)).thenReturn(Optional.of(account));
		float re = accountController.setAccountBalance(account);
		assertEquals(50000, account.getBalance());
		verify(accountService,times(1)).getAccountDetails(151000001L);
		
	}

}
