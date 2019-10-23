package com.store.shopping.model;

import javax.validation.constraints.NotNull;

/**
 * Item class
 */

public class Item extends Entity {


	@NotNull(message = "Item name must not be empty")
	private String name;

	@NotNull(message = "Quantity value must not be empty")
	private Integer quantity = 0;
	
	@NotNull(message = "Item value must not be empty")
	private Double value;

	/**
	 * Default constructor.
	 */
	public Item() {
		super();
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param name
	 * @param value
	 */
	public Item(String name, Double value) {
		this(name, value, 0);
	}
	
	/**
	 * Constructor using fields.
	 * 
	 * @param name
	 * @param value
	 */
	public Item(String name, Double value, Integer quantity) {
		super();
		this.name = name;
		this.value = value;
		this.quantity = quantity;
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
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}