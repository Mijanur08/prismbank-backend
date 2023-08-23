package com.wellsfargo.training.prism.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.prism.model.Beneficiary;
import com.wellsfargo.training.prism.repository.BeneficiaryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BeneficiaryService {
	
	@Autowired
	private BeneficiaryRepository bRepo;
	
	public Beneficiary saveBeneficiary(Beneficiary b) {
		return bRepo.save(b);
	}
	
	public Optional<Beneficiary> getSingleBeneficiary(Long beneficiaryId) {
		return bRepo.findById(beneficiaryId);
	}
	
	public List<Beneficiary> listAll() {
		return bRepo.findAll();
	}
	
	public void deleteBeneficiary(Long beneficiaryId) {
		bRepo.deleteById(beneficiaryId);
	}

}
