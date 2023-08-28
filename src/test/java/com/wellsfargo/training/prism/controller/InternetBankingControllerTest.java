package com.wellsfargo.training.prism.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
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
import com.wellsfargo.training.prism.model.InternetBankingUser;
import com.wellsfargo.training.prism.service.InternetBankingService;

@SpringBootTest
class InternetBankingControllerTest {
	
	InternetBankingUser user;
	
	@MockBean
	private InternetBankingService IBService;
	
	@Autowired
	private InternetBankingController userController;

	@BeforeEach
	void setUp() throws Exception {
		user = new InternetBankingUser();
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}

	@Test
	void testCreateIBUser() {
		
		user.setAccountNumber(151000001L);
		user.setEmail("nisha@gmail.com");
		user.setPassword("nisha123");
		user.setTransactionPassword("nisha1234");
		
		when(IBService.registerUser(any(InternetBankingUser.class))).thenReturn(user);
		ResponseEntity<String> re = userController.createIBUser(user);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("You are successfully registered for Internet Banking ", re.getBody());
		verify(IBService,times(1)).registerUser(any(InternetBankingUser.class));
	}

	@Test
	void testLoginCustomer() throws ResourceNotFoundException{
		
		user.setEmail("nisha@gmail.com");
		user.setPassword("nisha123");
		
		when(IBService.loginCustomer("nisha@gmail.com")).thenReturn(Optional.of(user));
		
		InternetBankingUser ib = IBService.loginCustomer("nisha@gmail.com").get();
		assertEquals(ib.getEmail(), user.getEmail());
		assertEquals(ib.getPassword(), user.getPassword());
		
		verify(IBService,times(1)).loginCustomer("nisha@gmail.com");
	}

}
