package com.wellsfargo.training.prism.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public Optional<Customer> findByEmail(String email);


}
