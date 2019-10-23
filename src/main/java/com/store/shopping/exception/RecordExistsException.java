package com.store.shopping.exception;

import com.store.shopping.model.Entity;

public class RecordExistsException extends Exception {

	private static final long serialVersionUID = -42692770690584645L;
	private Entity entity;
	
	public RecordExistsException(Entity entity) {
		super("The data already exists in our database.");
		this.entity = entity;
	}
	
	public RecordExistsException(Entity entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return this.entity;
	}

}
