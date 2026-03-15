package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static in.mk.main.util.Constants.ROLE_USER;
import static in.mk.main.util.Constants.ROLE_ADMIN;
import static in.mk.main.util.Constants.ROLE_ADMIN_USER;
import static in.mk.main.util.Constants.DEFAULT_PAGE_NO;

import static in.mk.main.util.Constants.DEFAULT_PAGE_SIZE;;




@Tag(name="Notes",description="All the Notes operations APIs")

@RequestMapping("/api/v1/notes")
public interface NotesControllerEndpoint {
	
	@Operation(summary="user saveNotes",tags= {"Notes","User"},description="user save notes")

	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception;
	
	
	@Operation(summary="download file",tags= {"Notes","User"},description="download files")

	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@Operation(summary="get all notes",tags= {"Notes"},description="all notes")

	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	@Operation(summary="Get all notes by user",tags= {"Notes","User"},description="user getting all notes")

	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser( @RequestParam(name ="pageNo", defaultValue =DEFAULT_PAGE_NO ) Integer pageNo,
                                                @RequestParam(name ="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	@Operation(summary="searchNotes",tags= {"Notes","User"},description="user searching for notes")

	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>searchNotes(@RequestParam(name="key",defaultValue = "") String key,
			                            @RequestParam(name ="pageNo", defaultValue =DEFAULT_PAGE_NO ) Integer pageNo,
			                            @RequestParam(name ="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@Operation(summary="deleteNotes",tags= {"Notes","User"},description="user notes delete")

	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>deleteNotes(@PathVariable Integer id ) throws Exception;
	
	
	@Operation(summary="restoreNotes",tags= {"Notes","USer"},description="user backup files")

	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>restoreNotes(@PathVariable Integer id ) throws Exception;
	
	@Operation(summary="user recycle bin notes",tags= {"Notes","User"},description="soft removal by user")

	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>getUserRecycleBinNotes() throws Exception;
	
	@Operation(summary="hard deletes notes",tags= {"Notes","User"},description="complete delete  by user")

	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>hardDeleteNotes(@PathVariable   Integer id ) throws Exception;
	
	@Operation(summary="empty user recycle bin",tags= {"Notes","User"},description="removing data by user from recycle bin")

	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyUserRecycleBin()throws Exception;

	@Operation(summary="favourite notes ",tags= {"Notes","User"},description="user add fav notes into list")

	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>favouriteNote(@PathVariable    Integer noteId ) throws Exception;
	
	@Operation(summary="list of unfavourite notes",tags= {"Notes","User"},description="user un fav notes")

	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>unFavouriteNote(@PathVariable    Integer favNoteId ) throws Exception;
	
	@Operation(summary="user favourite notes",tags= {"Notes","User"},description="fav notes of user")

	@GetMapping("/fav-note")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>getUserFavouriteNote() throws Exception;
	
	@Operation(summary="copy note",tags= {"Notes","User"},description="copy the notes")

	@GetMapping("/copy/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>copyNote(@PathVariable   Integer id ) throws Exception; 
}
