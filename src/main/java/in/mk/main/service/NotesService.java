package in.mk.main.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.mk.main.dto.NotesDto;
import in.mk.main.dto.NotesResponse;
import in.mk.main.entity.FilesDetails;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FilesDetails filesDtls) throws Exception;

	public FilesDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);
	
}
