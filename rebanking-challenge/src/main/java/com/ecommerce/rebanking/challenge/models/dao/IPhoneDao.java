package com.ecommerce.rebanking.challenge.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.rebanking.challenge.models.entity.Phone;

public interface IPhoneDao extends CrudRepository<Phone, Phone> {

	Phone findByDevice(String device);
	List<Phone> findAll();
	
}
