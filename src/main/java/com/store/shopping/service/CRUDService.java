package com.store.shopping.service;

import java.util.List;
import java.util.Optional;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Entity;

public interface CRUDService<J extends Entity> {
	
	public J save(J obj) throws RecordExistsException;

	public J update(J obj) throws RecordDoesNotExistsException, RecordExistsException;
	
	public void deleteById(String id) throws RecordDoesNotExistsException;
	
	public Optional<J> findByID(String id);

	public List<J> findAll();

	public boolean existsById(String id);

}
