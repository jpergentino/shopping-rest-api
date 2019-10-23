package com.store.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.User;
import com.store.shopping.repository.UserRepository;

@Service
public class UserService extends CRUDServiceImpl<User> implements CRUDService<User> {

	@Autowired
	private UserRepository userRepository;

	@Override
	@SuppressWarnings("rawtypes")
	protected MongoRepository getRepository() {
		return userRepository;
	}

	@Override
	public User save(User obj) throws RecordExistsException {

		if (userRepository.findByEmail(obj.getEmail()).isPresent()) {
			throw new RecordExistsException(obj);
		}

		return super.save(obj);
	}

}