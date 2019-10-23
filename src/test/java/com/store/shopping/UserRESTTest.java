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

import com.store.shopping.model.User;

/**
 * User REST tests.
 * 
 * @author pergentino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UserRESTTest extends AbstractAPITest {
	
	public final static String TEST_URL = BASE_URL +"/user";
	
	private static User userTest;
	
	/**
	 * Create an environment to be used by each method in this test.
	 */
	@Before
	public void setupBefore() {
		
		if (!initialized) {
			String name = "First name";
			String email = "newuser_"+ System.currentTimeMillis() +"@email.com";
			userTest = new User(name, email);
			
			ResponseEntity<User> response = getRESTTemplate().postForEntity(TEST_URL, userTest, User.class);
			userTest = response.getBody();
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertTrue(userTest.getId() != null);
			assertEquals(userTest.getName(), name);
			assertEquals(userTest.getEmail(), email);
			
			initialized = Boolean.TRUE;
		}
		
	}
	
	/**
	 * Clean the created environment.
	 */
	@AfterClass
	public static void setupAfter() {

		getRESTTemplate().delete(TEST_URL +"/"+ userTest.getId(), User.class);
		
		// Validate the deleted object
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/"+ userTest.getId(), User.class);
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
	public void POSTSaveNonEmptyIDUserTest() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName(UUID.randomUUID().toString());
		user.setEmail(UUID.randomUUID().toString() +"@email.com");
		getRESTTemplate().postForEntity(TEST_URL, user, User.class);
		ResponseEntity<User> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ user.getId(), User.class);
		assertEquals(result.getBody().getId(), user.getId());
		assertEquals(result.getBody().getName(), user.getName());
		assertEquals(result.getBody().getEmail(), user.getEmail());
	}


	@Test
	public void GETUserTest() {
		
		ResponseEntity<User> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ userTest.getId(), User.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertEquals(result.getBody().getId(), userTest.getId());
	}
	
	@Test
	public void GETUserAllTest() {
		ResponseEntity<User[]> result = getRESTTemplate().getForEntity(TEST_URL, User[].class);
		System.out.println(result);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		User[] users = result.getBody();
		assertNotNull(users);
		assertFalse(Arrays.isNullOrEmpty(users));
	}
	
	@Test
	public void PUTUserTest() {
		
		ResponseEntity<User> result = getRESTTemplate().getForEntity(TEST_URL +"/"+ userTest.getId(), User.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		User user = result.getBody();
		
		String originalName = user.getName();
		
		
		// First, change the name
		String changedName = "Changed name_"+ System.currentTimeMillis();
		user.setName(changedName); // In this scope, I'll change only the first name.
		
		getRESTTemplate().put(TEST_URL, user);
		
		// Validate the updated values
		result = getRESTTemplate().getForEntity(TEST_URL +"/"+ userTest.getId(), User.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		User updatedUser = result.getBody();
		assertEquals(updatedUser.getId(), userTest.getId());
		assertEquals(changedName, updatedUser.getName());
		

		// And now, back to the original name
		user.setName(originalName);
		getRESTTemplate().put(TEST_URL, user);
		
		// ... and validate again.
		result = getRESTTemplate().getForEntity(TEST_URL +"/"+ userTest.getId(), User.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		updatedUser = result.getBody();
		assertEquals(userTest.getId(), updatedUser.getId());
		assertEquals(originalName, updatedUser.getName());
		
	}
	
	
	/*
	 * Error tests.
	 * 
	 * The kind of test is represented by the method name. 
	 */
	
	@Test
	public void POSTSaveExistingUserTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, userTest, User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.METHOD_NOT_ALLOWED, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveEmptyUserTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, new User(), User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
	}
	
	
	@Test
	public void POSTSaveNullUserTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, null, User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getStatusCode());
		}
	}
	
	@Test
	public void POSTSaveInvalidEmailUserTest() {
		try {
			getRESTTemplate().postForEntity(TEST_URL, new User(UUID.randomUUID().toString(), "wrongemail.email.com" ), User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
		}
	}
	
	@Test
	public void GETUnknownUserTest() {
		try {
			getRESTTemplate().getForEntity(TEST_URL +"/UnknownUser_"+ System.currentTimeMillis(), User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}

	@Test
	public void DELETEUnknownUserTest() {
		try {
			getRESTTemplate().delete(TEST_URL +"/UnknownUser_"+ System.currentTimeMillis(), User.class);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	@Test
	public void PUTUnknownUserTest() {
		try {
			User user = userTest;
			user.setId(UUID.randomUUID().toString());
			
			getRESTTemplate().put(TEST_URL, user);
			fail();
		} catch (HttpStatusCodeException e) {
			// An alternative to expected exception approach, to validate the error code without create a new attribute.
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	
}