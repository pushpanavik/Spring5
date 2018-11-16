package com.bridgelabz.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bridgelabz.login.model.User;
import com.bridgelabz.login.service.IUserService;
import com.bridgelabz.login.utility.CustomResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	private IUserService userService;

	public Mono<ServerResponse> helloWorld(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Hello World..!!!"), String.class);
	}

	public Mono<ServerResponse> register(ServerRequest request){
		Mono<User> monoUser=request.bodyToMono(User.class);
		
		return ServerResponse.ok().body(userService.register(monoUser,request), User.class);
	}
	
	public Mono<ServerResponse> activateuser(ServerRequest req){
		return ServerResponse.ok().body(userService.activateRegisteredUser(req.pathVariable("token")),User.class);

	}
	
	public Mono<ServerResponse> login(ServerRequest req){
		Mono<String> monoUser=userService.login(req.bodyToMono(User.class));
		
		CustomResponse customResponse=new CustomResponse();
		return monoUser.flatMap(user->{
			customResponse.setMessage("User Successfully logged In");
			customResponse.setHeaders(user);
			customResponse.setStatus(200);
			return ServerResponse.ok().body(Mono.just(customResponse),CustomResponse.class);
		});	
	}
	
	public Mono<ServerResponse> forgotPassword(ServerRequest request){
		Mono<User> monoUser=userService.forgotPassword(request.bodyToMono(User.class));
		
		CustomResponse cuResponse=new CustomResponse();
		return monoUser.flatMap(user ->{
			cuResponse.setMessage("check ur mail to change password");
			cuResponse.setStatus(5);
			return ServerResponse.ok().body(Mono.just(cuResponse),CustomResponse.class);
		});	
	}
	
	public Mono<ServerResponse> resetpwd(ServerRequest req){
		return ServerResponse.ok().body(userService.resetUserpwd(req.pathVariable("token")),User.class);
	}
	
	public Mono<ServerResponse> resetPassword(ServerRequest request){
		
		return userService.resetPassword(request.bodyToMono(User.class), request.headers().header("token"))
		.flatMap(user -> {
			CustomResponse res=new CustomResponse();
			res.setMessage("ur password is updated successfully");
			res.setStatus(5);
			res.setHeaders(request.headers().header("token"));
			return ServerResponse.ok().body(Mono.just(res),CustomResponse.class);
		});		
	}
	
	public Mono<ServerResponse> getUser(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<User> user1 = userService.getUser(id);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(user1, User.class);
	}

	
	public Mono<ServerResponse> getAllUser(ServerRequest request) {
		Flux<User> user1 = userService.getAllUsers().log().map(u -> {
			System.out.println(u.getUserId());
			return u;
		});

		System.out.println("done");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(user1, User.class);
	}
	}
