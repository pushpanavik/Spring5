package com.bridgelabz.login.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bridgelabz.login.model.Note;

import reactor.core.publisher.Flux;

public interface NoteRepository extends ReactiveMongoRepository<Note, String>{

	Flux<Note> findByUserId(String userId);
	
	
}
