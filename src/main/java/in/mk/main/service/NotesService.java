package in.mk.main.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.mk.main.dto.FavouriteNoteDto;
import in.mk.main.dto.NotesDto;
import in.mk.main.dto.NotesResponse;
import in.mk.main.entity.FavouritNotes;
import in.mk.main.entity.FilesDetails;

public interface NotesService {

	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FilesDetails filesDtls) throws Exception;

	public FilesDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);

	
	public NotesResponse getAllNotesByUserSearch(Integer pageNo, Integer pageSize,String keyword);

	
	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes();

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin();
	
	
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unFavouriteNotes(Integer noteId) throws Exception;
	
	public List<FavouriteNoteDto> getUserFavouritNotes() throws Exception;

	public Boolean copyNotes(Integer id) throws Exception;
	
	
	
}
