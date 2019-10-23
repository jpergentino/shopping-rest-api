package com.store.shopping.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestAPI Index Controller.
 * 
 * Just to present an welcome message :-)
 * 
 * @author pergentino
 */

@RestController
@RequestMapping({"/", ""})
public class IndexController {

	/**
	 * Just a welcome message.
	 */
	@GetMapping
	public String index() {
		return "Welcome!";
	}

}