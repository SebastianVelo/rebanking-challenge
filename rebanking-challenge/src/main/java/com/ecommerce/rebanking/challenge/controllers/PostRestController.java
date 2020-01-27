package com.ecommerce.rebanking.challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.rebanking.challenge.models.entity.Phone;
import com.ecommerce.rebanking.challenge.models.entity.Post;
import com.ecommerce.rebanking.challenge.models.services.IPhoneService;
import com.ecommerce.rebanking.challenge.models.services.IPostService;

@RestController
@RequestMapping("/api")
public class PostRestController {

	@Autowired
	private IPostService postService;

	@Autowired
	private IPhoneService phoneService;
	
	@GetMapping("/posts")
	public List<Post> index() {
		return postService.findAll();
	}
	
	@PostMapping("/posts/{title}/{description}/{price}/{device}")
	public Post save(@PathVariable String title, @PathVariable String description, @PathVariable float price, @PathVariable String device) throws Exception {
		Phone phone = phoneService.findByDevice(device);	
		return postService.save(new Post(title, description, price, phone));
	}

	@PostMapping("/posts/{id}")
	public void delete(@PathVariable Long id) throws Exception {
		postService.delete(id);
	}

	
	
}
