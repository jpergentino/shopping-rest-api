package com.store.shopping.model;

import org.springframework.data.annotation.Id;

/**
 * Abstract entity class
 */

public abstract class Entity {
	
	@Id
	protected String id;

	/**
	 * Default constructor.
	 */
	public Entity() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}	
	
}