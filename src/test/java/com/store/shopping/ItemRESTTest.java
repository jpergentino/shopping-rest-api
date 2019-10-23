package com.store.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.assertj.core.util.Arrays;
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

import com.store.shopping.model.Item;

/**
 * Item REST tests.
 * 
 * @author pergentino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRESTTest extends AbstractAPITest {
	
	public final static String TEST_URL = BASE_URL +"/item";
	
	private static Item itemTest;
	
	/**
	 * Create an environment to be used by each method in this test.
	 */
	@Before
	public void setupBefore() {
		
		if (!initialized) {
			String name = "First name";
			Double value = Double.valueOf(111.99);
			itemTest = new Item(name, value);
			
			ResponseEntity<Item> response = getRESTTemplate().postForEntity(TEST_URL, itemTest, Item.class);
			itemTest = response.getBody();
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertTrue(itemTest.getId() != null);
			assertEquals(itemTest.getName(), name);
			assertEquals(itemTest.getValue(), value);
			
			initialized = Boolean.TRUE;
		}
		
	}
	
	/**
	 * Clean the created environment.
	 */
	@AfterClass
	public static void setupAfter() {

		getRESTTemplate().delete(TEST_URL +"/"+ itemTest.getId(), Item.class);
		
		// Validate the deleted object
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/"+ itemTest.getId(), Item.class);
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
	public void POSTSaveNonEmptyIDItemTest() {
		Item item = new Item();
		item.setId(UUID.randomUUID().toString());
		item.setName(UUID.randomUUID().toString());
		item.setValue(Double.valueOf(123.45));
		getRESTTemplate().postForEntity(TEST_URL, item, Item.class);
		ResponseEntity<Item> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ item.getId(), Item.class);
		assertEquals(result.getBody().getId(), item.getId());
		assertEquals(result.getBody().getName(), item.getName());
		assertEquals(result.getBody().getValue(), item.getValue());
	}


	@Test
	public void GETItemTest() {
		
		ResponseEntity<Item> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ itemTest.getId(), Item.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertEquals(result.getBody().getId(), itemTest.getId());
	}
	
	@Test
	public void GETItemAllTest() {
		ResponseEntity<Item[]> result = getRESTTemplate().getForEntity(TEST_URL, Item[].class);
		System.out.println(result);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		Item[] items = result.getBody();
		assertNotNull(items);
		assertFalse(Arrays.isNullOrEmpty(items));
	}
	
	@Test
	public void PUTItemTest() {
		
		ResponseEntity<Item> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ itemTest.getId(), Item.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		Item item = result.getBody();
		
		String originalName = item.getName();
		
		
		// First, change the name
		String changedName = "Changed name_"+ System.currentTimeMillis();
		item.setName(changedName); // In this scope, I'll change only the first name.
		
		getRESTTemplate().put(TEST_URL, item);
		
		// Validate the updated values
		result = getRESTTemplate().getForEntity(TEST_URL +"/"+ itemTest.getId(), Item.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		Item updatedItem = result.getBody();
		assertEquals(updatedItem.getId(), itemTest.getId());
		assertEquals(changedName, updatedItem.getName());
		

		// And now, back to the original name
		item.setName(originalName);
		getRESTTemplate().put(TEST_URL, item);
		
		// ... and validate again.
		result = getRESTTemplate().getForEntity(TEST_URL +"/"+ itemTest.getId(), Item.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		updatedItem = result.getBody();
		assertEquals(itemTest.getId(), updatedItem.getId());
		assertEquals(originalName, updatedItem.getName());
		
	}
	
	
	/*
	 * Error tests.
	 * 
	 * The kind of test is represented by the method name. 
	 */
	
	@Test
	public void POSTSaveExistingItemTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, itemTest, Item.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveEmptyItemTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, new Item(), Item.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
	}
	
	
	@Test
	public void POSTSaveNullItemTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, null, Item.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getStatusCode());
		}
	}
	
	@Test
	public void GETUnknownItemTest() {
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/UnknownItem_"+ System.currentTimeMillis(), Item.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	@Test
	public void DELETEUnknownItemTest() {
		try {
			getRESTTemplate().delete(TEST_URL +"/UnknownItem_"+ System.currentTimeMillis(), Item.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	@Test
	public void PUTUnknownItemTest() {
		try {
			Item item = itemTest;
			item.setId(UUID.randomUUID().toString());
			
			getRESTTemplate().put(TEST_URL, item);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	
}