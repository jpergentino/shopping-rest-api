package com.store.shopping.exception;

import com.store.shopping.model.Entity;

/**
 * Exception class to represents an existing data.
 * 
 * @author pergentino
 *
 */
public class RecordExistsException extends Exception {

	private static final long serialVersionUID = -42692770690584645L;
	private Entity entity;
	
	public RecordExistsException(Entity entity) {
		super("The data already exists in our database.");
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}

}
