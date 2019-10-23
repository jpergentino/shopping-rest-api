package com.store.shopping;

import org.junit.BeforeClass;
import org.springframework.web.client.RestTemplate;


public abstract class AbstractAPITest {

	protected final static String BASE_URL = new String("http://localhost:8080");
	
	/**
	 * Workaround to simulate the use of "static" {@link BeforeClass} approach.
	 */
	protected boolean initialized = Boolean.FALSE;
	
	public static RestTemplate getRESTTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
}