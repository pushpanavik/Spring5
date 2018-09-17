package com.example.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.spring.controller.UserHandler;

@Configuration
@ComponentScan("com.example.spring")
public class RouterConfiguration {


	@Bean
	public RouterFunction<ServerResponse> route(UserHandler userHandler){
		return RouterFunctions.route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_HTML)), userHandler::hello)
			   .andRoute(RequestPredicates.POST("/registerUser").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::registerUser)
			   .andRoute(RequestPredicates.GET("/listUser").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::listUser)
			   .andRoute(RequestPredicates.GET("/getUser/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
			  ;
	}
}
