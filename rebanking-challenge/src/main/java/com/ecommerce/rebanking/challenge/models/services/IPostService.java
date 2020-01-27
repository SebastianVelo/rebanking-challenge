package com.ecommerce.rebanking.challenge.models.services;

import java.util.List;

import com.ecommerce.rebanking.challenge.models.entity.Post;

public interface IPostService {

	public Post save(Post post);
	public void delete(Long id);
	public List<Post> findAll();
	public Post findById(Long id);
	
}
