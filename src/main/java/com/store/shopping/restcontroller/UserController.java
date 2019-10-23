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
import com.store.shopping.model.User;
import com.store.shopping.service.UserService;

/**
 * RestAPI User Controller.
 * 
 * Responsible to expose the REST methods according to Open API specification.
 * 
 * @author pergentino
 */

@RestController
@RequestMapping(UserController.PATH)
@CrossOrigin
public class UserController {

	static final String PATH = "/user";

	@Autowired
	private UserService userService;

	/**
	 * Method responsible to be exposed as a REST interface to add a new user.
	 * 
	 * @param user an object with values to add.
	 * @return the inserted object, {@link HttpStatus} 405 if an unexpected error occur.
	 * @throws RecordExistsException if an error occurs. 
	 */
	@PostMapping
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) throws RecordExistsException {
		return ResponseEntity.ok(userService.save(user));
	}

	/**
	 * Method responsible to be exposed as a REST interface to update an existing user.
	 * 
	 * @param user an object with values to update.
	 * @return the updated object if it is present, {@link HttpStatus} 404 otherwise.
	 * @throws RecordExistsException if an error occurs.
	 * @throws RecordDoesNotExistsException if an error occurs. 
	 */
	@PutMapping
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws RecordDoesNotExistsException, RecordExistsException  {
		return ResponseEntity.ok(userService.update(user));
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover a list of users.
	 * 
	 * @return a {@link List} of {@link User} objects.
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.ok(userService.findAll());
	}

	/**
	 * Method responsible to be exposed as a REST interface to recover an user.
	 * 
	 * @param id the id.
	 * @return the recovered object is present, {@link HttpStatus} 404 otherwise.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable(value = "id", required = true) String id) {
		Optional<User> user = userService.findByID(id);
		if (user.isPresent())
			return ResponseEntity.ok(user.get());
		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Method responsible to be exposed as a REST interface to delete an user.
	 * 
	 * @param id the id.
	 * @return a {@link HttpStatus} 404 if the user is not present in database, {@link HttpStatus} 200 otherwise.
	 * @throws RecordDoesNotExistsException if an error occurs. 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUserByID(@PathVariable(value = "id", required = true) String id) throws RecordDoesNotExistsException {
		userService.deleteById(id);
		return ResponseEntity.ok().build();

	}

}