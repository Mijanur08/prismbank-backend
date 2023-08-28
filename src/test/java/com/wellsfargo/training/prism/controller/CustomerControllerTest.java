package com.wellsfargo.training.prism.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.wellsfargo.training.prism.model.Address;
import com.wellsfargo.training.prism.model.Customer;
import com.wellsfargo.training.prism.service.CustomerService;

@SpringBootTest
class CustomerControllerTest {
	
	Customer customer;
	
	@MockBean
	private CustomerService cService;
	
	@Autowired
	private CustomerController customerController;

	@BeforeEach
	void setUp() throws Exception {
		customer = new Customer();
	}

	@AfterEach
	void tearDown() throws Exception {
		customer=null;
	}

	@Test
	void testCreateAccount() throws ParseException{
		
		customer.setSalutation("MR.");
		customer.setFirstName("Dhiraj");
		customer.setMiddleName("Kumar");
		customer.setLastName("Mishra");
		customer.setFatherName("Adhir Mishra");
		customer.setAadharNumber("805543897644");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = new Date(df.parse("1990-01-20").getTime());
		customer.setDob(dob);
		customer.setEmail("dhiraj@gmail.com");
		customer.setPhoneNo("9967328044");
		customer.setAccountType("Savings");
		customer.setApproved(false);
		
		Address resAddress = new Address();
		resAddress.setLine1("MG Road");
		resAddress.setLine2("College Street");
		resAddress.setLandmark("Presidency College");
		resAddress.setCity("Kolkata");
		resAddress.setState("West Bengal");
		resAddress.setPincode("700456");
		
		Address perAddress = new Address();
		perAddress.setLine1("MG Road");
		perAddress.setLine2("College Street");
		perAddress.setLandmark("Presidency College");
		perAddress.setCity("Kolkata");
		perAddress.setState("West Bengal");
		perAddress.setPincode("700456");
		
		customer.setResAddress(resAddress);
		customer.setPerAddress(perAddress);
		
		when(cService.registerCustomer(any(Customer.class))).thenReturn(customer);
		
		ResponseEntity<String> re = customerController.createAccount(customer);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Your account is created and account number is : "+customer.getAccountNo(), re.getBody());
		
		verify(cService,times(1)).registerCustomer(any(Customer.class));
		
	}

	@Test
	void testGetAllCustomers() throws ParseException{
		
		List<Customer> mockCustomerInfo = new ArrayList<>();
		
		Customer customerInfo1 = new Customer();
		customerInfo1.setSalutation("MR.");
		customerInfo1.setFirstName("Dhiraj");
		customerInfo1.setMiddleName("Kumar");
		customerInfo1.setLastName("Mishra");
		customerInfo1.setFatherName("Adhir Mishra");
		customerInfo1.setAadharNumber("805543897644");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob1 = new Date(df.parse("1990-01-20").getTime());
		customerInfo1.setDob(dob1);
		customerInfo1.setEmail("dhiraj@gmail.com");
		customerInfo1.setPhoneNo("9967328044");
		customerInfo1.setAccountType("Savings");
		customerInfo1.setApproved(false);
		
		Address resAddress1 = new Address();
		resAddress1.setLine1("MG Road");
		resAddress1.setLine2("College Street");
		resAddress1.setLandmark("Presidency College");
		resAddress1.setCity("Kolkata");
		resAddress1.setState("West Bengal");
		resAddress1.setPincode("700456");
		
		Address perAddress1 = new Address();
		perAddress1.setLine1("MG Road");
		perAddress1.setLine2("College Street");
		perAddress1.setLandmark("Presidency College");
		perAddress1.setCity("Kolkata");
		perAddress1.setState("West Bengal");
		perAddress1.setPincode("700456");
		
		customerInfo1.setResAddress(resAddress1);
		customerInfo1.setPerAddress(perAddress1);
		
		Customer customerInfo2 = new Customer();
		customerInfo2.setSalutation("MS.");
		customerInfo2.setFirstName("Rina");
		customerInfo2.setMiddleName("Das");
		customerInfo2.setLastName("Sarkar");
		customerInfo2.setFatherName("Gautam Sarkar");
		customerInfo2.setAadharNumber("227890541289");
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob2 = new Date(df.parse("1969-11-19").getTime());
		customerInfo2.setDob(dob2);
		customerInfo2.setEmail("rina@gmail.com");
		customerInfo2.setPhoneNo("9330474866");
		customerInfo2.setAccountType("Savings");
		customerInfo2.setApproved(false);
		
		Address resAddress2 = new Address();
		resAddress2.setLine1("A/5 Red Cross");
		resAddress2.setLine2("Serampore");
		resAddress2.setLandmark("Serampore College");
		resAddress2.setCity("Hooghly");
		resAddress2.setState("West Bengal");
		resAddress2.setPincode("714435");
		
		Address perAddress2 = new Address();
		perAddress2.setLine1("A/5 Red Cross");
		perAddress2.setLine2("Serampore");
		perAddress2.setLandmark("Serampore College");
		perAddress2.setCity("Hooghly");
		perAddress2.setState("West Bengal");
		perAddress2.setPincode("714435");
		
		customerInfo2.setResAddress(resAddress2);
		customerInfo2.setPerAddress(perAddress2);
		
		mockCustomerInfo.add(customerInfo1);
		mockCustomerInfo.add(customerInfo2);
		
		when(cService.listAll()).thenReturn(mockCustomerInfo);
		List<Customer> re = customerController.getAllCustomers();	
		
		assertEquals(2, re.size());
		assertEquals("Dhiraj", re.get(0).getFirstName());
		assertEquals("Rina", re.get(1).getFirstName());
		
		verify(cService,times(1)).listAll();
	}

	@Test
	void testUpdateAccount() throws ResourceNotFoundException, ParseException{
		
		Customer existingCustomer = new Customer();
		Customer updatedCustomer = new Customer();
		
		existingCustomer.setAccountNo(151000001L);
		existingCustomer.setSalutation("MR.");
		existingCustomer.setFirstName("Dhiraj");
		existingCustomer.setMiddleName("Kumar");
		existingCustomer.setLastName("Mishra");
		existingCustomer.setFatherName("Adhir Mishra");
		existingCustomer.setAadharNumber("805543897644");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob1 = new Date(df.parse("1990-01-20").getTime());
		existingCustomer.setDob(dob1);
		existingCustomer.setEmail("dhiraj@gmail.com");
		existingCustomer.setPhoneNo("9967328044");
		existingCustomer.setAccountType("Savings");
		existingCustomer.setApproved(true);
		
		Address resAddress1 = new Address();
		resAddress1.setLine1("MG Road");
		resAddress1.setLine2("College Street");
		resAddress1.setLandmark("Presidency College");
		resAddress1.setCity("Kolkata");
		resAddress1.setState("West Bengal");
		resAddress1.setPincode("700456");
		
		Address perAddress1 = new Address();
		perAddress1.setLine1("MG Road");
		perAddress1.setLine2("College Street");
		perAddress1.setLandmark("Presidency College");
		perAddress1.setCity("Kolkata");
		perAddress1.setState("West Bengal");
		perAddress1.setPincode("700456");
		
		existingCustomer.setResAddress(resAddress1);
		existingCustomer.setPerAddress(perAddress1);
		
		updatedCustomer.setAccountNo(151000001L);
		updatedCustomer.setSalutation("MR.");
		updatedCustomer.setFirstName("Dhiraj");
		updatedCustomer.setMiddleName("Kumar");
		updatedCustomer.setLastName("Mishra");
		updatedCustomer.setFatherName("Adhir Mishra");
		updatedCustomer.setAadharNumber("805543897644");
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//Date dob2 = new Date(df.parse("1990-01-20").getTime());
		updatedCustomer.setDob(dob1);
		updatedCustomer.setEmail("dhiraj@gmail.com");
		updatedCustomer.setPhoneNo("9967328066");
		updatedCustomer.setAccountType("Savings");
		updatedCustomer.setApproved(true);
		
//		Address resAddress2 = new Address();
//		resAddress2.setLine1("MG Road");
//		resAddress2.setLine2("College Street");
//		resAddress2.setLandmark("Presidency College");
//		resAddress2.setCity("Kolkata");
//		resAddress2.setState("West Bengal");
//		resAddress2.setPincode("700456");
//		
//		Address perAddress2 = new Address();
//		perAddress2.setLine1("MG Road");
//		perAddress2.setLine2("College Street");
//		perAddress2.setLandmark("Presidency College");
//		perAddress2.setCity("Kolkata");
//		perAddress2.setState("West Bengal");
//		perAddress2.setPincode("700456");
		
		updatedCustomer.setResAddress(resAddress1);
		updatedCustomer.setPerAddress(perAddress1);
		
		when(cService.getSingleCustomer(151000001L)).thenReturn(Optional.of(existingCustomer));
		when(cService.registerCustomer(any(Customer.class))).thenReturn(updatedCustomer);
		
		ResponseEntity<Customer> re = customerController.updateAccount(151000001L, updatedCustomer);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("MR.", re.getBody().getSalutation());
		assertEquals("Dhiraj", re.getBody().getFirstName());
		assertEquals("Kumar", re.getBody().getMiddleName());
		assertEquals("Mishra", re.getBody().getLastName());
		assertEquals("Adhir Mishra", re.getBody().getFatherName());
		assertEquals("805543897644", re.getBody().getAadharNumber());
		assertEquals(dob1, re.getBody().getDob());
		assertEquals("dhiraj@gmail.com", re.getBody().getEmail());
		assertEquals("9967328066", re.getBody().getPhoneNo());
		assertEquals("Savings", re.getBody().getAccountType());
		assertEquals(true, re.getBody().isApproved());
		
		assertEquals("MG Road", re.getBody().getResAddress().getLine1());
		assertEquals("College Street", re.getBody().getResAddress().getLine2());
		assertEquals("Presidency College", re.getBody().getResAddress().getLandmark());
		assertEquals("Kolkata", re.getBody().getResAddress().getCity());
		assertEquals("West Bengal", re.getBody().getResAddress().getState());
		assertEquals("700456", re.getBody().getResAddress().getPincode());
		
		assertEquals("MG Road", re.getBody().getPerAddress().getLine1());
		assertEquals("College Street", re.getBody().getPerAddress().getLine2());
		assertEquals("Presidency College", re.getBody().getPerAddress().getLandmark());
		assertEquals("Kolkata", re.getBody().getPerAddress().getCity());
		assertEquals("West Bengal", re.getBody().getPerAddress().getState());
		assertEquals("700456", re.getBody().getPerAddress().getPincode());
		
		verify(cService,times(1)).getSingleCustomer(151000001L);
		
		
	}

	@Test
	void testGetCustomerById() throws ResourceNotFoundException, ParseException{
		
		Customer mockCustomer = new Customer();
		
		mockCustomer.setAccountNo(151000001L);
		mockCustomer.setSalutation("MR.");
		mockCustomer.setFirstName("Dhiraj");
		mockCustomer.setMiddleName("Kumar");
		mockCustomer.setLastName("Mishra");
		mockCustomer.setFatherName("Adhir Mishra");
		mockCustomer.setAadharNumber("805543897644");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = new Date(df.parse("1990-01-20").getTime());
		mockCustomer.setDob(dob);
		mockCustomer.setEmail("dhiraj@gmail.com");
		mockCustomer.setPhoneNo("9967328044");
		mockCustomer.setAccountType("Savings");
		mockCustomer.setApproved(false);
		
		Address resAddress = new Address();
		resAddress.setLine1("MG Road");
		resAddress.setLine2("College Street");
		resAddress.setLandmark("Presidency College");
		resAddress.setCity("Kolkata");
		resAddress.setState("West Bengal");
		resAddress.setPincode("700456");
		
		Address perAddress = new Address();
		perAddress.setLine1("MG Road");
		perAddress.setLine2("College Street");
		perAddress.setLandmark("Presidency College");
		perAddress.setCity("Kolkata");
		perAddress.setState("West Bengal");
		perAddress.setPincode("700456");
		
		mockCustomer.setResAddress(resAddress);
		mockCustomer.setPerAddress(perAddress);
		
		when(cService.getSingleCustomer(151000001L)).thenReturn(Optional.of(mockCustomer));
		
		ResponseEntity<Customer> re = customerController.getCustomerById(151000001L);
		assertEquals(HttpStatus.OK,re.getStatusCode());
		
		assertEquals("MR.", re.getBody().getSalutation());
		assertEquals("Dhiraj", re.getBody().getFirstName());
		assertEquals("Kumar", re.getBody().getMiddleName());
		assertEquals("Mishra", re.getBody().getLastName());
		assertEquals("Adhir Mishra", re.getBody().getFatherName());
		assertEquals("805543897644", re.getBody().getAadharNumber());
		assertEquals(dob, re.getBody().getDob());
		assertEquals("dhiraj@gmail.com", re.getBody().getEmail());
		assertEquals("9967328044", re.getBody().getPhoneNo());
		assertEquals("Savings", re.getBody().getAccountType());
		assertEquals(false, re.getBody().isApproved());
		
		assertEquals("MG Road", re.getBody().getResAddress().getLine1());
		assertEquals("College Street", re.getBody().getResAddress().getLine2());
		assertEquals("Presidency College", re.getBody().getResAddress().getLandmark());
		assertEquals("Kolkata", re.getBody().getResAddress().getCity());
		assertEquals("West Bengal", re.getBody().getResAddress().getState());
		assertEquals("700456", re.getBody().getResAddress().getPincode());
		
		assertEquals("MG Road", re.getBody().getPerAddress().getLine1());
		assertEquals("College Street", re.getBody().getPerAddress().getLine2());
		assertEquals("Presidency College", re.getBody().getPerAddress().getLandmark());
		assertEquals("Kolkata", re.getBody().getPerAddress().getCity());
		assertEquals("West Bengal", re.getBody().getPerAddress().getState());
		assertEquals("700456", re.getBody().getPerAddress().getPincode());
		
		verify(cService,times(1)).getSingleCustomer(151000001L);
		
	}

//
//	@Test
//	void testDeleteCustomer() throws ResourceNotFoundException, ParseException{
//		
//		Customer existingCustomer = new Customer();
//		
//		existingCustomer.setAccountNo(151000001L);
//		existingCustomer.setSalutation("MR.");
//		existingCustomer.setFirstName("Dhiraj");
//		existingCustomer.setMiddleName("Kumar");
//		existingCustomer.setLastName("Mishra");
//		existingCustomer.setFatherName("Adhir Mishra");
//		existingCustomer.setAadharNumber("805543897644");
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date dob1 = new Date(df.parse("1990-01-20").getTime());
//		existingCustomer.setDob(dob1);
//		existingCustomer.setEmail("dhiraj@gmail.com");
//		existingCustomer.setPhoneNo("9967328044");
//		existingCustomer.setAccountType("Savings");
//		existingCustomer.setApproved(true);
//		
//		Address resAddress = new Address();
//		resAddress.setLine1("MG Road");
//		resAddress.setLine2("College Street");
//		resAddress.setLandmark("Presidency College");
//		resAddress.setCity("Kolkata");
//		resAddress.setState("West Bengal");
//		resAddress.setPincode("700456");
//		
//		Address perAddress = new Address();
//		perAddress.setLine1("MG Road");
//		perAddress.setLine2("College Street");
//		perAddress.setLandmark("Presidency College");
//		perAddress.setCity("Kolkata");
//		perAddress.setState("West Bengal");
//		perAddress.setPincode("700456");
//		
//		existingCustomer.setResAddress(resAddress);
//		existingCustomer.setPerAddress(perAddress);
//		
//		when(cService.getSingleCustomer(151000001L)).thenReturn(Optional.of(existingCustomer));
//		doNothing().when(cService).deleteCustomer(151000001L);
//		
//		Map<String,Boolean> response=customerController.deleteCustomer(151000001L);
//		
//		assertTrue(response.containsKey("Customer Deleted"));
//		assertTrue(response.get("Customer Deleted"));
//		
//		verify(cService,times(1)).getSingleCustomer(151000001L);
//		verify(cService, times(1)).deleteCustomer(151000001L);	
//		
//	}

}
