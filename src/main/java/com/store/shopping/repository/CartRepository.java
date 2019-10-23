package com.store.shopping.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.shopping.model.Cart;

@Repository
@Transactional
public interface CartRepository extends MongoRepository<Cart, String> {
	public List<Cart> findByOrderByTotalAsc();
}