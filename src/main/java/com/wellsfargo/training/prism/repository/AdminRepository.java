package com.wellsfargo.training.prism.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.prism.model.Admin;



public interface AdminRepository extends JpaRepository<Admin, String> {

}
