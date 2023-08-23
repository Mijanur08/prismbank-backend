package com.wellsfargo.training.prism.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
