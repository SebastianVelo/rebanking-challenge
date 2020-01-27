package com.ecommerce.rebanking.challenge.models.services;

import java.util.List;
import java.util.Optional;

import com.ecommerce.rebanking.challenge.models.entity.Phone;
import com.ecommerce.rebanking.challenge.models.entity.Post;

public interface IPostService {

	public Post save(Post post);

	public void delete(Long id);

	public List<Post> findAll();

	public Post findById(Long id);

	public Post update(Long id, Optional<String> title, Optional<String> description, Optional<Float> price,
			Phone phone);

}
