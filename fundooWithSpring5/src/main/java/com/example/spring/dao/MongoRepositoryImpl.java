package com.example.spring.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.model.User;

import reactor.core.publisher.Mono;

@Repository
public interface MongoRepositoryImpl extends ReactiveMongoRepository<User, String>{

	Mono<User> findByEmail(String email);
	
}
