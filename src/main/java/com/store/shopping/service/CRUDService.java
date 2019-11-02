package com.store.shopping.service;

import java.util.List;
import java.util.Optional;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Entity;

/**
 * An interface to define the CRUD commons methods. 
 * 
 * @author pergentino
 *
 * @param <J> the used {@link Entity}
 */
public interface CRUDService<J extends Entity> {
	
	/**
	 * Save the object.
	 * 
	 * @param obj the object.
	 * @return the saved object.
	 * @throws RecordExistsException if an error occur.
	 */
	public J save(J obj) throws RecordExistsException;

	/**
	 * Update the object.
	 * 
	 * @param obj the object.
	 * @return the updated object.
	 * @throws RecordDoesNotExistsException if an error occur.
	 * @throws RecordExistsException if an error occur.
	 */
	public J update(J obj) throws RecordDoesNotExistsException, RecordExistsException;
	
	/**
	 * Delete the entity.
	 * 
	 * @param id to delete.
	 * @throws RecordDoesNotExistsException if an error occur.
	 */
	public void deleteById(String id) throws RecordDoesNotExistsException;
	
	/**
	 * Find an entity according to ID.
	 * @param id to find.
	 * @return the entity.
	 */
	public Optional<J> findByID(String id);

	/**
	 * Find all entities.
	 * 
	 * @return {@link List} of all entities.
	 */
	public List<J> findAll();

	/**
	 * Validate if the entity already exists.
	 * 
	 * @param id to find.
	 * @return true if it exists, false otherwise.
	 */
	public boolean existsById(String id);

}
