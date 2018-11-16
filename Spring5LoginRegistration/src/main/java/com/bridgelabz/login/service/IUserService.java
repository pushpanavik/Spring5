package com.bridgelabz.login.service;

import java.util.List;

import org.springframework.web.reactive.function.server.ServerRequest;

import com.bridgelabz.login.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {

	Mono<User> register(Mono<User> monoUser, ServerRequest request);
	
	Mono<User> activateRegisteredUser(String token);
	
	Mono<String> login(Mono<User> monoUser);
	
	Mono<User> forgotPassword(Mono<User> monoUser);

	Mono<User> getUser(String id);

	Flux<User> getAllUsers();

	Mono<User> resetUserpwd(String token);

	Mono<User> resetPassword(Mono<User> bodyToMono,List<String> list);
}
