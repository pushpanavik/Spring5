package com.bridgelabz.login.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.login.dto.CreateNoteDto;
import com.bridgelabz.login.dto.NoteDto;
import com.bridgelabz.login.model.Note;
import com.bridgelabz.login.repository.NoteRepository;
import com.bridgelabz.login.utility.VerifyJwtToken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NoteService implements INoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Mono<Note> createNote(Mono<CreateNoteDto> monoNote, String token) {
 		String userId = VerifyJwtToken.getId(token);
		
		return monoNote.flatMap(createNoteDto -> {
		
			NoteDto noteDto = modelMapper.map(createNoteDto, NoteDto.class);
			noteDto.setCreatedAt(LocalDateTime.now());
			noteDto.setUpdatedAt(LocalDateTime.now());
			Note note = modelMapper.map(noteDto, Note.class);
			note.setUserId(userId);
			return noteRepository.save(note);
		});
	}

	@Override
	public Mono<String> deleteNote(String noteid){
		return noteRepository.findById(noteid).flatMap(note->{
			return noteRepository.delete(note).map(n->{
				return "Note with this id deleted successfully";
			}).then(Mono.just("Note deleted successfully"));
		}).switchIfEmpty(Mono.just("note id not found"));
	}
	
	@Override
	public Flux<Note> getNotesOfUser(String token){
		String userid=VerifyJwtToken.getId(token);
		return noteRepository.findByUserId(userid);
	}

	@Override
	public Mono<Note> updateNote(Mono<Note> note,String token) {
		String userId=VerifyJwtToken.getId(token);
		return note.flatMap(n->{
			return noteRepository.findById(n.getId())
					.flatMap(existingnote -> {
//						if(userId.matches(n.getUserId())) {	
						existingnote.setTitle(n.getTitle());
						existingnote.setDescription(n.getDescription());
						existingnote.setColor(n.getColor());
						existingnote.setCreatedAt(n.getCreatedAt());
						existingnote.setImage(n.getImage());
						existingnote.setUpdatedAt(n.getUpdatedAt());
						existingnote.setArchive(n.isArchive());
						existingnote.setPin(n.isPin());
						existingnote.setTrash(n.isTrash());
						
//						return noteRepository.save(existingnote);
//						}
//						else {
//							existingnote.setTitle(n.getTitle());
//							existingnote.setDescription(n.getDescription());
							return noteRepository.save(existingnote);
//					}
					}).switchIfEmpty(Mono.error(new Exception("Note not found")));
		});
		
	}


}
