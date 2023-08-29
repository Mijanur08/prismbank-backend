package com.wellsfargo.training.prism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.prism.model.Admin;
import com.wellsfargo.training.prism.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@PostMapping(value="/login")
	public Boolean loginAdmin(@RequestBody @Validated Admin a) {
		Admin admin = adminService.findAdmin(a.getUserid()).orElse(null);
		if(admin == null) return false;
		if(admin.getUserid().equals(a.getUserid()) && 
				admin.getPassword().equals(a.getPassword()))
			return true;
		return false;
	}
	
}
