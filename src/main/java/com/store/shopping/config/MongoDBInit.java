package com.store.shopping.config;

import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.store.shopping.model.Cart;
import com.store.shopping.model.Item;
import com.store.shopping.model.User;
import com.store.shopping.service.CartService;
import com.store.shopping.service.ItemService;
import com.store.shopping.service.UserService;

/**
 * A class for creating the database environment.
 * 
 * @author pergentino
 */
@Configuration
public class MongoDBInit {
	
	private static final Logger log = LoggerFactory.getLogger(MongoDBInit.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	
	/**
	 * Create init data.
	 */
	@PostConstruct
	public void createData() throws Throwable {
		log.info("Creating users on database...");
		userService.save(new User("Pergentino", "jpergentino@gmail.com"));
		userService.save(new User("User 1", "user1@aninvalidemail.com"));
		userService.save(new User("User 2", "user2@aninvalidemail.com"));
		userService.save(new User("User 3", "user3@aninvalidemail.com"));
	
		log.info("Creating items on database...");
		itemService.save(new Item("Samsung Galaxy", 999.10));
		itemService.save(new Item("Xiaomi Mi 9", 1234.56));
		itemService.save(new Item("iPhone X", 2345.67));
	
		log.info("Creating carts on database...");
		cartService.save(new Cart(userService.findAll().get(0), itemService.findAll().stream().peek(i -> i.setQuantity(randomQuantity())).collect(Collectors.toList())));
		cartService.save(new Cart(userService.findAll().get(0), itemService.findAll().stream().filter(x -> x.getValue() < 1000.00).peek(i -> i.setQuantity(randomQuantity())).collect(Collectors.toList())));
		cartService.save(new Cart(userService.findAll().get(0), itemService.findAll().stream().filter(x -> x.getValue() < 1500.00).peek(i -> i.setQuantity(randomQuantity())).collect(Collectors.toList())));
	}
	
	/**
	 * @return a random int value.
	 */
	private int randomQuantity() {
		return new Random().nextInt(10) + 1;
	}
	

}
