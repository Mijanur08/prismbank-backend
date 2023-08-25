package com.wellsfargo.training.prism.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Account;
import com.wellsfargo.training.prism.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
	
	public List<Beneficiary> findByAccount(Account account);

}
