package com.wellsfargo.training.prism.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Beneficiary;
import com.wellsfargo.training.prism.service.BeneficiaryService;

@SpringBootTest
class BeneficiaryControllerTest {
	
	Beneficiary beneficiary;
	
	@MockBean
	private BeneficiaryService bService;
	
	@Autowired
	private BeneficiaryController beneficiaryController;

	@BeforeEach
	void setUp() throws Exception {
		beneficiary = new Beneficiary();
	}

	@AfterEach
	void tearDown() throws Exception {
		beneficiary = null;
	}

	@Test
	void testSaveBeneficiary() {
		
		beneficiary.setBeneficiaryName("Akshat Jain");
		beneficiary.setAccountNo(151000050L);
		beneficiary.setRelation("Brother");
		beneficiary.setNickName("Akku");
		
		Account account = new Account();
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		beneficiary.setAccount(account);
		
		when(bService.saveBeneficiary(any(Beneficiary.class))).thenReturn(beneficiary);
		ResponseEntity<String> re = beneficiaryController.saveBeneficiary(beneficiary, beneficiary.getAccount().getAccountNo());
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Beneficiary added successfull with beneficiary id : "+beneficiary.getBid(), re.getBody());
		verify(bService,times(1)).saveBeneficiary(any(Beneficiary.class));
	}

	@Test
	void testGetAllBeneficiary() throws ResourceNotFoundException{
		
		List<Beneficiary> allBeneficiary = new ArrayList<>();
		
		Beneficiary beneficiary1 = new Beneficiary();
		beneficiary1.setBeneficiaryName("Akshat Jain");
		beneficiary1.setAccountNo(151000000L);
		beneficiary1.setRelation("Brother");
		beneficiary1.setNickName("Akku");
		
		Account account = new Account();
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		Beneficiary beneficiary2 = new Beneficiary();
		beneficiary2.setBeneficiaryName("Shrushti Deshmukh");
		beneficiary2.setAccountNo(151000002L);
		beneficiary2.setRelation("Sister");
		beneficiary2.setNickName("Shree");
		
		beneficiary1.setAccount(account);
		beneficiary2.setAccount(account);
		
		allBeneficiary.add(beneficiary1);
		allBeneficiary.add(beneficiary2);
		

	}

	@Test
	void testDeleteBeneficiary() throws ResourceNotFoundException{
		
		Beneficiary existingBeneficiary = new Beneficiary();
		
		existingBeneficiary.setBid(1L);
		existingBeneficiary.setBeneficiaryName("AkshatJain");
		existingBeneficiary.setAccountNo(151000050L);
		existingBeneficiary.setRelation("Brother");
		existingBeneficiary.setNickName("Akku");
		
		Account account = new Account();
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		existingBeneficiary.setAccount(account);
		
		when(bService.getSingleBeneficiary(1L)).thenReturn(Optional.of(existingBeneficiary));
		doNothing().when(bService).deleteBeneficiary(1L);
		Map<String,Boolean> response = beneficiaryController.deleteBeneficiary(1L);
		assertTrue(response.containsKey("deleted"));
		assertTrue(response.get("deleted"));
		verify(bService,times(1)).deleteBeneficiary(1L);
	}

	@Test
	void testUpdateBeneficiary() throws ResourceNotFoundException{
		
		Beneficiary existingBeneficiary = new Beneficiary();
		Beneficiary updatedBeneficiary = new Beneficiary();
		
		existingBeneficiary.setBid(1L);
		existingBeneficiary.setBeneficiaryName("AkshatJain");
		existingBeneficiary.setAccountNo(151000050L);
		existingBeneficiary.setRelation("Brother");
		existingBeneficiary.setNickName("Akku");
		
		Account account = new Account();
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		existingBeneficiary.setAccount(account);
		
		updatedBeneficiary.setBid(1L);
		updatedBeneficiary.setBeneficiaryName("AkshatJain");
		updatedBeneficiary.setAccountNo(151000050L);
		updatedBeneficiary.setRelation("Father");
		updatedBeneficiary.setNickName("Akku");
		
		updatedBeneficiary.setAccount(account);
		
		when(bService.getSingleBeneficiary(1L)).thenReturn(Optional.of(existingBeneficiary));
		when(bService.saveBeneficiary(any(Beneficiary.class))).thenReturn(updatedBeneficiary);
		ResponseEntity<String> re = beneficiaryController.updateBeneficiary(updatedBeneficiary);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		verify(bService,times(1)).getSingleBeneficiary(1L);
	}

	@Test
	void testGetSingleBeneficiary() {
		
		Beneficiary mockBeneficiary = new Beneficiary();
		
		mockBeneficiary.setBid(1L);
		mockBeneficiary.setBeneficiaryName("AkshatJain");
		mockBeneficiary.setAccountNo(151000050L);
		mockBeneficiary.setRelation("Brother");
		mockBeneficiary.setNickName("Akku");
		
		Account account = new Account();
		account.setAccountNo(151000001L);
		account.setBalance(50000);
		account.setAccountType("Savings");
		
		mockBeneficiary.setAccount(account);
		
		when(bService.getSingleBeneficiary(1L)).thenReturn(Optional.of(mockBeneficiary));
		ResponseEntity<Beneficiary> re = beneficiaryController.getSingleBeneficiary(1L);
		assertEquals(HttpStatus.OK,re.getStatusCode());
		assertEquals("Akshat"+"Jain", re.getBody().getBeneficiaryName());
		assertEquals(151000050L, re.getBody().getAccountNo());
		assertEquals("Brother", re.getBody().getRelation());
		assertEquals("Akku", re.getBody().getNickName());
		assertEquals(151000001L, re.getBody().getAccount().getAccountNo());
		assertEquals(50000, re.getBody().getAccount().getBalance());
		assertEquals("Savings", re.getBody().getAccount().getAccountType());
		verify(bService,times(1)).getSingleBeneficiary(1L);
	}

}
