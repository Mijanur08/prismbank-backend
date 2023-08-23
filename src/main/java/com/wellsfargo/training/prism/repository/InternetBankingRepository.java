package com.wellsfargo.training.prism.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.wellsfargo.training.prism.model.InternetBankingUser;

public interface InternetBankingRepository extends JpaRepository<InternetBankingUser, Long> {
	//Custom Method to fetch record/object based on email field - non id field.
		public Optional<InternetBankingUser> findByEmail(String email);
}
