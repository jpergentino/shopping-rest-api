package com.store.shopping.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

@Configuration
public class MongoDBConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MongoDBConfig.class);
	
	private static final String MONGODB_URL = "localhost";
	private static final String MONGODB_DATABASE_NAME = "embeded_db";
	
    @SuppressWarnings("deprecation")
	@Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGODB_URL);
        return new MongoTemplate(mongo.getObject(), MONGODB_DATABASE_NAME);
    }	

}
