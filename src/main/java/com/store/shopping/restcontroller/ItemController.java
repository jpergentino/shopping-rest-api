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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.shopping.exception.RecordDoesNotExistsException;
import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Item;
import com.store.shopping.service.ItemService;

/**
 * RestAPI Item Controller.
 * 
 * Responsible to expose the REST methods according to Open API specification.
 * 
 * @author pergentino
 */

@RestController
@RequestMapping(ItemController.PATH)
@CrossOrigin
public class ItemController {

	static final String PATH = "/item";

	@Autowired
	private ItemService itemService;

	/**
	 * Method responsible to be exposed as a REST interface to add a new item.
	 * 
	 * @param item an object with values to add.
	 * @return the inserted object, {@link HttpStatus} 405 if an unexpected error occur.
	 * @throws RecordExistsException if an error occurs. 
	 */
	@PostMapping
	public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) throws RecordExistsException {
		return ResponseEntity.ok(itemService.save(item));
	}

	/**
	 * Method responsible to be exposed as a REST interface to update an existing item.
	 * 
	 * @param item an object with values to update.
	 * @return the updated object if it is present, {@link HttpStatus} 404 otherwise.
	 * @throws RecordExistsException if an error occurs.
	 * @throws RecordDoesNotExistsException if an error occurs. 
	 */
	@PutMapping
	public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) throws RecordDoesNotExistsException, RecordExistsException  {
		return ResponseEntity.ok(itemService.update(item));
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover a list of items.
	 * 
	 * @return a {@link List} of {@link Item} objects.
	 */
	@GetMapping
	public ResponseEntity<List<Item>> getAll() {
		return ResponseEntity.ok(itemService.findAll());
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover an item.
	 * 
	 * @param id the id.
	 * @return the recovered object is present, {@link HttpStatus} 404 otherwise.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Item> getItemByID(@PathVariable(value = "id", required = true) String id) {
		Optional<Item> item = itemService.findByID(id);
		if (item.isPresent())
			return ResponseEntity.ok(item.get());
		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Method responsible to be exposed as a REST interface to delete an item.
	 * 
	 * @param id the id.
	 * @return a {@link HttpStatus} 404 if the item is not present in database, {@link HttpStatus} 200 otherwise.
	 * @throws RecordDoesNotExistsException if an error occurs. 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteItemByID(@PathVariable(value = "id", required = true) String id) throws RecordDoesNotExistsException {
		itemService.deleteById(id);
		return ResponseEntity.ok().build();

	}

}