package in.mk.main.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.mk.main.dto.NotesDto;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
}
