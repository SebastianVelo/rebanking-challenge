package com.ecommerce.rebanking.challenge.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.rebanking.challenge.models.entity.Phone;

public interface IPhoneDao extends CrudRepository<Phone, Long> {

	Phone findByDevice(String device);
	
}
