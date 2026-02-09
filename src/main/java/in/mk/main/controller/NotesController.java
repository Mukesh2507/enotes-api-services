package in.mk.main.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.mk.main.dto.FavouriteNoteDto;
import in.mk.main.dto.NotesDto;
import in.mk.main.dto.NotesResponse;
import in.mk.main.endpoint.NotesControllerEndpoint;
import in.mk.main.entity.FilesDetails;
import in.mk.main.respository.FileRepository;
import in.mk.main.service.NotesService;
import in.mk.main.util.CommonUtil;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController

public class NotesController implements NotesControllerEndpoint {

    private final FileRepository fileRepository;
	
	@Autowired
	private NotesService notesService;


    NotesController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
	

	@Override
	public ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception{
		
		Boolean saveNotes=notesService.saveNotes(notes,file);
		if (saveNotes) {
			 return	CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
			} 
			
		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@Override
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
	
		FilesDetails filesDetails=notesService.getFileDetails(id);
		byte[] downloadFile =notesService.downloadFile(filesDetails);

		
	HttpHeaders headers = new HttpHeaders();
	String contentTypeString =CommonUtil.getContentType(filesDetails.getOriginalfileName());
	headers.setContentType(MediaType.parseMediaType(contentTypeString));
	headers.setContentDispositionFormData("attachment", filesDetails.getOriginalfileName());
	
	return ResponseEntity.ok().headers(headers).body(downloadFile);
	}

	
	@Override
	public ResponseEntity<?> getAllNotes(){
		List<NotesDto> notes  =notesService.getAllNotes();
		       if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
			}
		 			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(
		@RequestParam(name="pageNo",required = true,defaultValue = "0" ) Integer pageNo,
		@RequestParam(name="pageSize",defaultValue = "10")Integer pageSize
		){
		

		NotesResponse notes  =notesService.getAllNotesByUser(pageNo,pageSize);
//		       if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//			}
		 			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
	
	
	@Override
	public ResponseEntity<?>searchNotes(
			@RequestParam(name="key",defaultValue = "")String key,
		@RequestParam(name="pageNo",required = true,defaultValue = "0" ) Integer pageNo,
		@RequestParam(name="pageSize",defaultValue = "10")Integer pageSize
		){
		

		NotesResponse notes  =notesService.getAllNotesByUserSearch(pageNo,pageSize,key);
//		       if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//			}
		 			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
	
	@Override
	public ResponseEntity<?>deleteNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Deletye success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?>restoreNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Restore success", HttpStatus.OK);
	}

    @Override
	public ResponseEntity<?>getUserRecycleBinNotes() throws Exception{
    List<NotesDto> notes	=notesService.getUserRecycleBinNotes();
	if (CollectionUtils.isEmpty(notes)) {
		return CommonUtil.createBuildResponseMessage("No data available in recycle bin", HttpStatus.OK);

	}
	return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?>hardDeleteNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Deletye success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> emptyUserRecycleBin()throws Exception{
         notesService.emptyRecycleBin();
         return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?>favouriteNote(@PathVariable Integer noteId ) throws Exception{
		
		notesService.favouriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Notes added fav", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?>unFavouriteNote(@PathVariable Integer favNoteId ) throws Exception{
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtil.createBuildResponseMessage("remove fav success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?>getUserFavouriteNote() throws Exception{
	
		 List<FavouriteNoteDto> userFavouriteNoteDtos =notesService.getUserFavouritNotes();
		 if (CollectionUtils.isEmpty(userFavouriteNoteDtos)) {
			return ResponseEntity.noContent().build();
		}
		 
		return CommonUtil.createBuildResponse(userFavouriteNoteDtos, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?>copyNote(@PathVariable Integer id ) throws Exception{
		
		boolean copyNotes=notesService.copyNotes(id);
		
		if (copyNotes) {
			return CommonUtil.createBuildResponseMessage("copied success", HttpStatus.CREATED);

		}
		return CommonUtil.createErrorResponseMessage("copy failed! Try again", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	}

