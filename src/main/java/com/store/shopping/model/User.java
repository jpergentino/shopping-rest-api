package com.store.shopping.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * User class
 */

public class User extends Entity {


	@NotNull(message = "User name must not be empty")
	private String name;

	@NotNull(message = "User email must not be empty")
	@Email(message = "User email is invalid")
	@Indexed(unique = true)
	private String email;

	/**
	 * Default constructor.
	 */
	public User() {
		super();
	}

	/**
	 * Constructor using all fields.
	 * 
	 * @param name
	 * @param email
	 */
	public User(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}