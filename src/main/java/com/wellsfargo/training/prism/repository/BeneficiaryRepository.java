package com.wellsfargo.training.prism.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{

}
