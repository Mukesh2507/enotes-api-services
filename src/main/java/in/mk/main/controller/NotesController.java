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
import in.mk.main.entity.FilesDetails;
import in.mk.main.respository.FileRepository;
import in.mk.main.service.NotesService;
import in.mk.main.util.CommonUtil;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    private final FileRepository fileRepository;
	
	@Autowired
	private NotesService notesService;


    NotesController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
	

	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception{
		
		Boolean saveNotes=notesService.saveNotes(notes,file);
		if (saveNotes) {
			 return	CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
			} 
			
		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
	
		FilesDetails filesDetails=notesService.getFileDetails(id);
		byte[] downloadFile =notesService.downloadFile(filesDetails);

		
	HttpHeaders headers = new HttpHeaders();
	String contentTypeString =CommonUtil.getContentType(filesDetails.getOriginalfileName());
	headers.setContentType(MediaType.parseMediaType(contentTypeString));
	headers.setContentDispositionFormData("attachment", filesDetails.getOriginalfileName());
	
	return ResponseEntity.ok().headers(headers).body(downloadFile);
	}

	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes(){
		List<NotesDto> notes  =notesService.getAllNotes();
		       if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
			}
		 			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
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
	
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER')")
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
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>deleteNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Deletye success", HttpStatus.OK);
	}

	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>restoreNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Restore success", HttpStatus.OK);
	}

	@GetMapping("/recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>getUserRecycleBinNotes() throws Exception{
    List<NotesDto> notes	=notesService.getUserRecycleBinNotes();
	if (CollectionUtils.isEmpty(notes)) {
		return CommonUtil.createBuildResponseMessage("No data available in recycle bin", HttpStatus.OK);

	}
	return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>hardDeleteNotes(@PathVariable Integer id ) throws Exception{
		
		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Deletye success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> emptyUserRecycleBin()throws Exception{
         notesService.emptyRecycleBin();
         return CommonUtil.createBuildResponseMessage("Delete success", HttpStatus.OK);
	}
	
	
	@GetMapping("/fav/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>favouriteNote(@PathVariable Integer noteId ) throws Exception{
		
		notesService.favouriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Notes added fav", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>unFavouriteNote(@PathVariable Integer favNoteId ) throws Exception{
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtil.createBuildResponseMessage("remove fav success", HttpStatus.OK);
	}
	
	@GetMapping("/fav-note")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>getUserFavouriteNote() throws Exception{
	
		 List<FavouriteNoteDto> userFavouriteNoteDtos =notesService.getUserFavouritNotes();
		 if (CollectionUtils.isEmpty(userFavouriteNoteDtos)) {
			return ResponseEntity.noContent().build();
		}
		 
		return CommonUtil.createBuildResponse(userFavouriteNoteDtos, HttpStatus.OK);
	}
	
	@GetMapping("/copy/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>copyNote(@PathVariable Integer id ) throws Exception{
		
		boolean copyNotes=notesService.copyNotes(id);
		
		if (copyNotes) {
			return CommonUtil.createBuildResponseMessage("copied success", HttpStatus.CREATED);

		}
		return CommonUtil.createErrorResponseMessage("copy failed! Try again", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	}

