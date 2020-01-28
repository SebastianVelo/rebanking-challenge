package com.ecommerce.rebanking.challenge.models.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.rebanking.challenge.models.dao.IPostDao;
import com.ecommerce.rebanking.challenge.models.entity.Post;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private IPostDao postDao;
	private Logger logger = LogManager.getLogger(PostServiceImpl.class);;

	@Override
	@Transactional
	public Post save(Post post) {
		return postDao.save(post);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		postDao.delete(findById(id));
	}

	@Override
	@Transactional
	public Post update(Long id, Optional<String> title, Optional<String> description, Optional<Float> price,
			Long phone) {
		Post post = findById(id);
		if(title.isPresent()) {
			post.setTitle(title.get());
			logger.info("title: " + title.get());
		}
		if(description.isPresent()) {
			post.setDescription(description.get());
			logger.info("description: " + description.get());
		}
		if(price.isPresent()) {
			post.setPrice(price.get());
			logger.info("price: $" + price.get());
		}
		if(phone != null) {
			post.setPhone(phone);
			logger.info("phone: " + phone);
		}
		return postDao.save(post);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Post> findAll() {
		List<Post> posts = postDao.findAll();
		logger.info("Response PostDao findAll" + (posts == null ? "Not found" : posts));
		return posts;
	}

	@Override
	@Transactional(readOnly = true)
	public Post findById(Long id) {
		Post post = postDao.findById(id).get();
		logger.info("Response PostDao findById" + (post == null ? "Not found" : post));
		return post;
	}

}
