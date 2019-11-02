package com.store.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Entity;

/**
 * The CRUD abstract service layer abstract class with implemented CRUD methods
 * to be reusable by other entities service's. 
 * 
 * @author pergentino
 *
 * @param <J> the {@link Entity}.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class CRUDServiceImpl<J extends Entity> implements CRUDService<J> {
	
	protected abstract MongoRepository getRepository();
	
	@Override
	public J save(J obj) throws RecordExistsException {
		if (existsById(obj.getId())) {
			throw new RecordExistsException(obj);
		}
		return (J) getRepository().save(obj);
	}

	@Override
	public J update(J obj) throws RecordDoesNotExistsException, RecordExistsException {
		if (!existsById(obj.getId())) {
			throw new RecordDoesNotExistsException(obj.getId());
		}

		return (J) getRepository().save(obj);
	}

	@Override
	public void deleteById(String id) throws RecordDoesNotExistsException {
		if (!existsById(id)) {
			throw new RecordDoesNotExistsException(id);
		}
		getRepository().deleteById(id);
	}

	@Override
	public Optional<J> findByID(String id) {
		return (Optional<J>) getRepository().findById(id);
	}

	@Override
	public boolean existsById(String id) {
		return (id != null ? getRepository().existsById(id) : Boolean.FALSE);
	}
	
	@Override
	public List<J> findAll() {
		return getRepository().findAll();
	}
	
}