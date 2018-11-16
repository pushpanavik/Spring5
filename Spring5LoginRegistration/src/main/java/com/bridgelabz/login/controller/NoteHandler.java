package com.bridgelabz.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bridgelabz.login.dto.CreateNoteDto;
import com.bridgelabz.login.model.Note;
import com.bridgelabz.login.service.INoteService;
import com.bridgelabz.login.utility.VerifyJwtToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NoteHandler {
	
	@Autowired
	private INoteService noteService;

	public Mono<ServerResponse> createNote(ServerRequest request) {
		String token = request.headers().header("token").get(0);
		System.out.println(token);
		Mono<Note> monoNote = noteService.createNote(request.bodyToMono(CreateNoteDto.class), token);
		return ServerResponse.ok().body(monoNote,Note.class);
	}
	
	public Mono<ServerResponse> deleteNote(ServerRequest request){

	Mono<String> note= noteService.deleteNote(request.pathVariable("id"));
	return note.flatMap(string->{
		return ServerResponse.ok().body(BodyInserters.fromObject(string));
	});
	}
	
	public Mono<ServerResponse> getNotesOfUser(ServerRequest req){
				
		Flux<Note> note=noteService.getNotesOfUser(req.headers().header("token").get(0));
		return ServerResponse.ok().body(note,Note.class);
	}
	
	public Mono<ServerResponse> updateNote(ServerRequest req){
		Mono<Note> mononote=req.bodyToMono(Note.class);
		Mono<Note> note=noteService.updateNote(mononote,req.headers().header("token").get(0));
		return ServerResponse.ok().body(mononote,Note.class);
	}
}
