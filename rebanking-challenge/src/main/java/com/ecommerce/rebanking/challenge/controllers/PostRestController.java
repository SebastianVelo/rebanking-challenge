package com.ecommerce.rebanking.challenge.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<Post>> index() {
		return ResponseEntity.ok(postService.findAll());
	}

	@PostMapping("/posts/{title}/{description}/{price}/{device}")
	public ResponseEntity<Post> save(@PathVariable String title, @PathVariable String description,
			@PathVariable float price, @PathVariable String device) {
		Phone phone = phoneService.findByDevice(device);
		return ResponseEntity.ok(postService.save(new Post(title, description, price, phone)));
	}

	@PutMapping("/posts/{id}/{title}/{description}/{price}/{device}")
	public ResponseEntity<Post> update(@PathVariable Long id, @PathVariable Optional<String> title,
			@PathVariable Optional<String> description, @PathVariable Optional<Float> price,
			@PathVariable Optional<String> device) throws Exception {
		Phone phone = null;
		if(device.isPresent()) {
			phone = phoneService.findByDevice(device.get());
		}
		return ResponseEntity.ok(postService.update(id, title, description, price, phone));
	}

	@PostMapping("/posts/{id}")
	public void delete(@PathVariable Long id) throws Exception {
		postService.delete(id);
	}

}
