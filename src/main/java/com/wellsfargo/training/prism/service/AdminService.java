package com.wellsfargo.training.prism.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.prism.model.Admin;
import com.wellsfargo.training.prism.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService {
	@Autowired
	private AdminRepository adminRepo;
	
	public Optional<Admin> findAdmin(String userid){
		return adminRepo.findById(userid);
	}

}
