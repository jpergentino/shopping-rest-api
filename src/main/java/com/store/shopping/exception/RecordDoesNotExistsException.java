package com.store.shopping.exception;

/**
 * Exception class to represents a non-existing data.
 * 
 * @author pergentino
 *
 */
public class RecordDoesNotExistsException extends Exception {

	private static final long serialVersionUID = -42692770690584645L;
	private String id;
	
	public RecordDoesNotExistsException(String id) {
		super("The data does not exists in our database.");
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
