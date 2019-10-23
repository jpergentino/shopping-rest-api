package com.store.shopping.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.store.shopping.model.User;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User, String> {
	public Optional<User> findByEmail(String email);
}