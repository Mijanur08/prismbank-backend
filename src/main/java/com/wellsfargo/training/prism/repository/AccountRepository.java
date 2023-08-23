package com.wellsfargo.training.prism.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
