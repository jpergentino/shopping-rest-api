package com.store.shopping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.shopping.model.Item;

/**
 * A repository {@link Item} interface.
 * 
 * @author pergentino
 */

@Repository
@Transactional
public interface ItemRepository extends MongoRepository<Item, String> {
	
	/**
	 * Find an {@link Item} ordered by name.
	 * 
	 * @return an {@link Item}.
	 */

	public Optional<Item> findByName(String name);
}