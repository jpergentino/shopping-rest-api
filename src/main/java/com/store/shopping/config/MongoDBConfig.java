package com.store.shopping.config;

import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.store.shopping.model.Cart;
import com.store.shopping.model.Item;
import com.store.shopping.model.User;
import com.store.shopping.service.CartService;
import com.store.shopping.service.ItemService;
import com.store.shopping.service.UserService;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

@Configuration
public class MongoDBConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MongoDBConfig.class);
	
	private static final String MONGODB_URL = "localhost";
	private static final String MONGODB_DATABASE_NAME = "embeded_db";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	
    @SuppressWarnings("deprecation")
	@Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGODB_URL);
        return new MongoTemplate(mongo.getObject(), MONGODB_DATABASE_NAME);
    }
	
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
	
	private int randomQuantity() {
		return new Random().nextInt(10) + 1;
	}
	

}
