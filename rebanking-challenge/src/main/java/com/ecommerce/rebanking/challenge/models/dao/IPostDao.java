package com.ecommerce.rebanking.challenge.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.rebanking.challenge.models.entity.Post;

public interface IPostDao extends CrudRepository<Post, Long> {
	
}
