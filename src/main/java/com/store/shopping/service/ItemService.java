package com.store.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.store.shopping.model.Item;
import com.store.shopping.repository.ItemRepository;

@Service
public class ItemService extends CRUDServiceImpl<Item> implements CRUDService<Item> {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	@SuppressWarnings("rawtypes")
	protected MongoRepository getRepository() {
		return itemRepository;
	}

}