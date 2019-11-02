package com.store.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.store.shopping.exception.RecordExistsException;
import com.store.shopping.model.Cart;
import com.store.shopping.model.Item;
import com.store.shopping.repository.CartRepository;

/**
 * The service layer that represents the {@link Cart} entity. 
 * 
 * @author pergentino
 */
@Service
public class CartService extends CRUDServiceImpl<Cart> implements CRUDService<Cart> {
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	@SuppressWarnings("rawtypes")
	protected MongoRepository getRepository() {
		return cartRepository;
	}
	
	public List<Cart> findAllOrderedByTotal() {
		return cartRepository.findByOrderByTotalAsc();
	}

	@Override
	public Cart save(Cart obj) throws RecordExistsException {
		
		Optional<Item> emptyItem = obj.getItems().stream().filter(item -> item.getQuantity() <= 0).findFirst();
		Assert.isTrue(!emptyItem.isPresent(), "There is an empty item in cart.");
		
		obj.setTotal(obj.getItems().stream().mapToDouble(Item::getValue).sum());
		
		return super.save(obj);
	}
	
	

}