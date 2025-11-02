package in.mk.main.service;

import java.util.List;

import in.mk.main.dto.NotesDto;

public interface NotesService {

	public Boolean saveNotes(NotesDto notesDto);
	
	public List<NotesDto> getAllNotes();
	
}
