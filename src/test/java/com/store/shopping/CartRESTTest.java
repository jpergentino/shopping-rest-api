package com.store.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;

import com.store.shopping.model.Cart;
import com.store.shopping.model.Item;
import com.store.shopping.model.User;

/**
 * Cart REST tests.
 * 
 * @author pergentino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CartRESTTest extends AbstractAPITest {
	
	public final static String TEST_URL = BASE_URL +"/cart";
	
	private static Cart cartTest;
	
	/**
	 * Create an environment to be used by each method in this test.
	 */
	@Before
	public void setupBefore() {
		
		if (!initialized) {
			
			User firstUserResult = getRESTTemplate().getForEntity(UserRESTTest.TEST_URL, User[].class).getBody()[0];
			
			Item[] itemsResult = getRESTTemplate().getForEntity(ItemRESTTest.TEST_URL, Item[].class).getBody();
			
			cartTest = new Cart(firstUserResult, Arrays.asList(itemsResult));
			cartTest.getItems().stream().peek(i -> i.setQuantity(new Random().nextInt(10) + 1)).collect(Collectors.toList());
			
			ResponseEntity<Cart> response = getRESTTemplate().postForEntity(TEST_URL, cartTest, Cart.class);
			cartTest = response.getBody();
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertTrue(cartTest.getId() != null);
			assertEquals(firstUserResult.getId(), cartTest.getUser().getId());
			assertEquals(itemsResult.length, cartTest.getItems().size());
			
			initialized = Boolean.TRUE;
		}
		
	}
	
	/**
	 * Clean the created environment.
	 */
	@AfterClass
	public static void setupAfter() {

		getRESTTemplate().delete(TEST_URL +"/"+ cartTest.getId(), Cart.class);
		
		// Validate the deleted object
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/"+ cartTest.getId(), Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	/*
	 * Success tests.
	 * 
	 * The kind of test is represented by the method name.
	 */
	
	@Test
	public void POSTSaveNonEmptyIDCartTest() {
		Cart cart = new Cart();
		cart.setId(UUID.randomUUID().toString());
		cart.setUser(cartTest.getUser());
		cart.setItems(cartTest.getItems());
		getRESTTemplate().postForEntity(TEST_URL, cart, Cart.class);
		ResponseEntity<Cart> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ cart.getId(), Cart.class);
		assertTrue(cartTest.getId() != null);
		assertEquals(cart.getId(), result.getBody().getId(), cart.getId());
		assertEquals(cart.getUser().getId(), result.getBody().getUser().getId());
		assertEquals(cart.getItems().size(), result.getBody().getItems().size());
	}


	@Test
	public void GETCartTest() {
		
		ResponseEntity<Cart> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ cartTest.getId(), Cart.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertEquals(cartTest.getId(), result.getBody().getId());
		assertEquals(cartTest.getUser().getId(), result.getBody().getUser().getId());
		assertEquals(cartTest.getItems().size(), result.getBody().getItems().size());
	}
	
	@Test
	public void GETCartAllTest() {
		ResponseEntity<Cart[]> result = getRESTTemplate().getForEntity(TEST_URL, Cart[].class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		Cart[] carts = result.getBody();
		assertNotNull(carts);
		assertFalse(org.assertj.core.util.Arrays.isNullOrEmpty(carts));
	}
	
	@Test
	public void GETCartAllOrderedByTotalTest() {
		ResponseEntity<Cart[]> result = getRESTTemplate().getForEntity(TEST_URL +"/sorted", Cart[].class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		Cart[] carts = result.getBody();
		assertNotNull(carts);
		assertFalse(org.assertj.core.util.Arrays.isNullOrEmpty(carts));
	}
	
	
	/*
	 * Error tests.
	 * 
	 * The kind of test is represented by the method name. 
	 */
	
	@Test
	public void POSTSaveExistingCartTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, cartTest, Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveEmptyCartTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, new Cart(), Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
	}
	
	
	@Test
	public void POSTSaveNullCartTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, null, Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveEmptyItemsCartTest() {
		try {
			Cart cart = new Cart();
			cart.setId(UUID.randomUUID().toString());
			cart.setUser(cartTest.getUser());
			getRESTTemplate().postForEntity(TEST_URL, cart, Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveItemWithZeroQuantityCartTest() {
		try {
			Cart cart = new Cart();
			cart.setId(UUID.randomUUID().toString());
			cart.setUser(cartTest.getUser());
			cart.setItems(Arrays.asList(new Item("A unique name_"+ System.currentTimeMillis(), 10.0)));
			getRESTTemplate().postForEntity(TEST_URL, cart, Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
		}
	}
	
	
	
	@Test
	public void GETUnknownCartTest() {
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/UnknownCart_"+ System.currentTimeMillis(), Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	@Test
	public void DELETEUnknownCartTest() {
		try {
			getRESTTemplate().delete(TEST_URL +"/UnknownCart_"+ System.currentTimeMillis(), Cart.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
}