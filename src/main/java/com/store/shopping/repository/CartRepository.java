package com.store.shopping.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.shopping.model.Cart;

/**
 * A repository {@link Cart} interface.
 * 
 * @author pergentino
 */
@Repository
@Transactional
public interface CartRepository extends MongoRepository<Cart, String> {

	/**
	 * Find all {@link Cart} ordered by total value in an ascending mode.
	 * 
	 * @return a list of {@link Cart}.
	 */
	public List<Cart> findByOrderByTotalAsc();
}