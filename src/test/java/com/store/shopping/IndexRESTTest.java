package com.store.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Index tests.
 * 
 * @author pergentino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class IndexRESTTest extends AbstractAPITest {
	
	/*
	 * Success tests.
	 * 
	 * The kind of test is represented by the method name.
	 */

	@Test
	public void GETIndexTest() {
		
		ResponseEntity<String> result = getRESTTemplate().getForEntity(BASE_URL, String.class);
		
		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertEquals("Welcome!", result.getBody());
	}
}