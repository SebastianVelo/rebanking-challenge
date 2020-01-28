package com.ecommerce.rebanking.challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.rebanking.challenge.models.entity.Phone;
import com.ecommerce.rebanking.challenge.models.services.IPhoneService;

@RestController
@RequestMapping("/api")
public class PhoneRestController {

	@Autowired
	private IPhoneService phoneService;
		
	@GetMapping("/phones")
	public ResponseEntity<List<Phone>> index() {
		return ResponseEntity.ok(phoneService.findAll());
	}
	
	@GetMapping("/phones/{device}")
	public ResponseEntity<Phone> findByDevice(@PathVariable String device) throws Exception {
		return ResponseEntity.ok(phoneService.findByDevice(device));
	}
}
