package com.store.shopping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.shopping.model.User;

/**
 * A repository {@link User} interface.
 * 
 * @author pergentino
 */

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User, String> {
	
	/**
	 * Find an {@link User} by name.
	 * 
	 * @return an {@link User}.
	 */
	public Optional<User> findByEmail(String email);
}