package com.store.shopping.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Cart;
import com.store.shopping.service.CartService;

/**
 * RestAPI Cart Controller.
 * 
 * Responsible to expose the REST methods according to Open API specification.
 * 
 * @author pergentino
 */

@RestController
@RequestMapping(CartController.PATH)
@CrossOrigin
public class CartController {

	static final String PATH = "/cart";

	@Autowired
	private CartService cartService;

	/**
	 * Method responsible to be exposed as a REST interface to add a new cart.
	 * 
	 * @param cart an object with values to add.
	 * @return the inserted object, {@link HttpStatus} 405 if an unexpected error occur.
	 * @throws RecordExistsException if an error occurs. 
	 */
	@PostMapping
	public ResponseEntity<Cart> addCart(@Valid @RequestBody Cart cart) throws RecordExistsException {
		return ResponseEntity.ok(cartService.save(cart));
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover a list of carts.
	 * 
	 * @return a {@link List} of {@link Cart} objects.
	 */
	@GetMapping
	public ResponseEntity<List<Cart>> getAll() {
		return ResponseEntity.ok(cartService.findAll());
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover a list of carts ordered by total.
	 * 
	 * @return a {@link List} of {@link Cart} objects.
	 */
	@GetMapping(value = "/sorted")
	public ResponseEntity<List<Cart>> getAllSortedByTotal() {
		return ResponseEntity.ok(cartService.findAllOrderedByTotal());
	}
	
	/**
	 * Method responsible to be exposed as a REST interface to recover an cart.
	 * 
	 * @param id the id.
	 * @return the recovered object is present, {@link HttpStatus} 404 otherwise.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cart> getCartByID(@PathVariable(value = "id", required = true) String id) {
		Optional<Cart> cart = cartService.findByID(id);
		if (cart.isPresent())
			return ResponseEntity.ok(cart.get());
		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Method responsible to be exposed as a REST interface to delete an cart.
	 * 
	 * @param id the id.
	 * @return a {@link HttpStatus} 404 if the cart is not present in database, {@link HttpStatus} 200 otherwise.
	 * @throws RecordDoesNotExistsException if an error occurs. 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCartByID(@PathVariable(value = "id", required = true) String id) throws RecordDoesNotExistsException {
		cartService.deleteById(id);
		return ResponseEntity.ok().build();
	}

}