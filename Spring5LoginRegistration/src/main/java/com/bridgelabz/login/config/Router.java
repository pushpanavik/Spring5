package com.bridgelabz.login.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bridgelabz.login.controller.NoteHandler;
import com.bridgelabz.login.controller.UserHandler;
import com.bridgelabz.login.model.User;

import reactor.core.publisher.TopicProcessor;

@Configuration
@ComponentScan("com.bridgelabz.login")
public class Router {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler, NoteHandler noteHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/helloworld").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
						userHandler::helloWorld)
				.andRoute(RequestPredicates.POST("/register").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						userHandler::register)
				.andRoute(RequestPredicates.GET("/activateuser/{token:.+}"), userHandler::activateuser)
				.andRoute(RequestPredicates.POST("/login").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						userHandler::login)
				.andRoute(RequestPredicates.POST("/forgotpassword"), userHandler::forgotPassword)
//				.andRoute(RequestPredicates.GET("/resetpwd/{token:.+}"), userHandler::resetpwd)
				.andRoute(RequestPredicates.POST("/resetpassword"), userHandler::resetPassword)
				.andRoute(RequestPredicates.POST("/createNote"), noteHandler::createNote)
				.andRoute(RequestPredicates.DELETE("/deleteNote/{id}"),noteHandler::deleteNote)
				.andRoute(RequestPredicates.GET("/getNotesOfUser"), noteHandler::getNotesOfUser)
				.andRoute(RequestPredicates.PUT("/updateNote"), noteHandler::updateNote)
				.andRoute(RequestPredicates.GET("/getuser/{id}"), userHandler::getUser)
				.andRoute(RequestPredicates.GET("/getallusers")
						.and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), userHandler::getAllUser)
				;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
	public TopicProcessor<User> userRegister() {
		return TopicProcessor.<User>create();
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	
}
