package com.store.shopping;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.store.shopping.model.User;
import com.store.shopping.service.UserService;

/**
 * Just to test if the SpringBootContext is running.
 * 
 * @author pergentino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringContextTest extends AbstractAPITest {

	@Autowired
	private UserService service;
	
	@Test
	public void testContext() {
		Assert.assertNotNull(service);
		ResponseEntity<User[]> forEntity = getRESTTemplate().getForEntity(BASE_URL +"/user/", User[].class);
		assertNotNull(forEntity.getBody().length);
	}

}