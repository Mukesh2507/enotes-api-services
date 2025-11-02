package in.mk.main.service;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.mk.main.dto.NotesDto;
import in.mk.main.entity.Notes;
import in.mk.main.respository.NotesRepository;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveNotes(NotesDto notesDto) {
		//validation logic here 
		
		Notes notes = mapper.map(notesDto, Notes.class);
		
		  Notes saveNotes = notesRepo.save(notes);
		  
		  if (!ObjectUtils.isEmpty(saveNotes)) {
			  return true;
			
		}
		return false;
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream()
				.map(note->mapper.map(note,NotesDto.class)).toList();
		
		
	}

}
