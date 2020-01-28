package com.ecommerce.rebanking.challenge.models.services;

import java.util.List;

import com.ecommerce.rebanking.challenge.models.entity.Phone;

public interface IPhoneService {
	
	public Phone save(Phone phone);
	public void delete(Phone id);
	public List<Phone> findAll();
	public Phone findById(Phone id);
	public Phone findByDevice(String device);
	
}
