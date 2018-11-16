package com.bridgelabz.login.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.bridgelabz.login.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{

	Mono<User> findByEmail(String email);
	
}
