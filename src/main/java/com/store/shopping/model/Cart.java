package com.store.shopping.model;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Cart class
 */

public class Cart extends Entity {


	@NotNull(message = "User must not be empty")
	private User user;

	@NotNull(message = "Total must not be empty")
	private Double total = 0.0;
	
	@NotNull(message = "Items must not be empty")
	private List<Item> items;
	

	/**
	 * Default constructor.
	 */
	public Cart() {
		super();
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param user
	 */
	public Cart(User user, List<Item> items) {
		super();
		this.user = user;
		this.items = items;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
}