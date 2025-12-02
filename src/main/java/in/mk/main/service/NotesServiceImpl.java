package in.mk.main.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.mk.main.dto.FavouriteNoteDto;
import in.mk.main.dto.NotesDto;
import in.mk.main.dto.NotesDto.CategoryDto;
import in.mk.main.dto.NotesDto.FilesDto;
import in.mk.main.dto.NotesResponse;
import in.mk.main.entity.FavouritNotes;
import in.mk.main.entity.FilesDetails;
import in.mk.main.entity.Notes;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.respository.CategoryRepository;
import in.mk.main.respository.FavouriteNotesRepository;
import in.mk.main.respository.FileRepository;
import in.mk.main.respository.NotesRepository;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileRepository fileRepo;
    
    @Autowired
    private FavouriteNotesRepository favouriteNotesRepo;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    @Transactional
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
        // parse incoming JSON dto
        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        // validate category
        checkCategoryExist(notesDto.getCategory());

        if (!ObjectUtils.isEmpty(notesDto.getId())) {
            // UPDATE flow
            return updateNotes(notesDto, file);
        }

        // CREATE flow
        Notes notesMap = mapper.map(notesDto, Notes.class);
           
        FilesDetails fileDtls = saveFileDetails(file);
        if (!ObjectUtils.isEmpty(fileDtls)) {
            notesMap.setFilesDetails(fileDtls);
        } else {
            notesMap.setFilesDetails(null);
        }

        Notes saved = notesRepo.save(notesMap);
        return !ObjectUtils.isEmpty(saved) && saved.getId() != null;
    }

    @Transactional
    protected Boolean updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
        Notes existNotes = notesRepo.findById(notesDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invalid notes id"));

        // Map incoming DTO to a temp Notes object
        Notes mapped = mapper.map(notesDto, Notes.class);

        // Preserve identity & audit fields
        mapped.setId(existNotes.getId());
        mapped.setCreatedBy(existNotes.getCreatedBy());
        mapped.setCreatedOn(existNotes.getCreatedOn());

        // Handle file: if a new file is provided, save it and set it; otherwise preserve existing
        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
            FilesDetails newFile = saveFileDetails(file);
            mapped.setFilesDetails(newFile);
        } else {
            // if DTO contains filesDetails (for example client explicitly cleared it) respect that
            if (!ObjectUtils.isEmpty(notesDto.getFilesDetails())) {
                mapped.setFilesDetails(mapper.map(notesDto.getFilesDetails(), FilesDetails.class));
            } else {
                mapped.setFilesDetails(existNotes.getFilesDetails());
            }
        }

        // Ensure category exists (already validated at entry), but set the category relation correctly if needed
        // (Assumes ModelMapper produced correct Category entity from DTO)

        notesRepo.save(mapped);
        return true;
    }

    private FilesDetails saveFileDetails(MultipartFile file) throws IOException {
        if (ObjectUtils.isEmpty(file) || file.isEmpty()) {
            return null;
        }

        FilesDetails fileDtls = new FilesDetails();
        String originalFileNameString = file.getOriginalFilename();
        fileDtls.setOriginalfileName(originalFileNameString);
        fileDtls.setDisplayFileName(getDisplayName(originalFileNameString));

        String rndString = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(originalFileNameString);
        String uploadFileName = rndString + (ObjectUtils.isEmpty(extension) ? "" : "." + extension);

        fileDtls.setUploadFileName(uploadFileName);
        fileDtls.setFileSize(file.getSize());

        // ensure upload path ends properly and exists
        Path uploadDir = Paths.get(uploadPath == null ? "" : uploadPath);
        Files.createDirectories(uploadDir);

        Path storePath = uploadDir.resolve(uploadFileName);
        fileDtls.setPath(storePath.toString());

        // copy file
        long copied = Files.copy(file.getInputStream(), storePath);

        if (copied > 0) {
            return fileRepo.save(fileDtls);
        }
        return null;
    }

    private String getDisplayName(String originalFileNameString) {
        if (ObjectUtils.isEmpty(originalFileNameString)) return "";
        String extension = FilenameUtils.getExtension(originalFileNameString);
        String fileName = FilenameUtils.removeExtension(originalFileNameString);

        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        return fileName + (ObjectUtils.isEmpty(extension) ? "" : "." + extension);
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        if (ObjectUtils.isEmpty(category) || ObjectUtils.isEmpty(category.getId())) {
            throw new ResourceNotFoundException("category id is invalid");
        }

        categoryRepo.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("category id is invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
    }

    @Override
    public byte[] downloadFile(FilesDetails fileDetails) throws Exception {
        if (fileDetails == null || ObjectUtils.isEmpty(fileDetails.getPath())) {
            throw new ResourceNotFoundException("file not found");
        }

        try (InputStream io = new FileInputStream(fileDetails.getPath())) {
            return StreamUtils.copyToByteArray(io);
        }
    }

    @Override
    public FilesDetails getFileDetails(Integer id) throws Exception {
        return fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("file is not available"));
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
        PageRequest pageableObj = PageRequest.of(pageNo, pageSize);

        Page<Notes> pagenotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageableObj);


        List<NotesDto> notesDtos = pagenotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();
        NotesResponse notesResponse = NotesResponse.builder().notesDtos(notesDtos).pageNo(pagenotes.getNumber())
                .pageSize(pagenotes.getSize()).totalElement((int) pagenotes.getTotalElements())
                .totalPages(pagenotes.getTotalPages()).isFirst(pagenotes.isFirst()).isLast(pagenotes.isLast()).build();
        return notesResponse;
    }

    @Override
    public void softDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes id invalid !Not found"));

        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepo.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) throws Exception {
        Notes notes = notesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes id invalid !Not found"));

        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepo.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
        List<Notes> recyclesNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
        List<NotesDto> notesDtoList = recyclesNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
        return notesDtoList;
    }

	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		Notes notes=notesRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
	    if (notes.getIsDeleted()) {
			notesRepo.delete(notes);
		}else {
			throw new IllegalArgumentException("sorry you can't hard delete");
		}
	}

	@Override
	public void emptyRecycleBin(int userId) {
		
        List<Notes> recyclesNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
        if (!CollectionUtils.isEmpty(recyclesNotes)) {
			notesRepo.deleteAll(recyclesNotes);
		}
        
		
	}

	@Override
	public void favouriteNotes(Integer noteId) throws Exception {
	  Integer userId=1;
		
		Notes notes	=notesRepo.findById(noteId)
			.orElseThrow(()->new ResourceNotFoundException("notes not found and invalid"));
		
		FavouritNotes favouritNotes =FavouritNotes.builder()
				.notes(notes)
				.userId(userId)
				.build();
		favouriteNotesRepo.save(favouritNotes);
		
		
	}

	@Override
	public void unFavouriteNotes(Integer favNoteId) throws Exception {
		FavouritNotes favnotes	=favouriteNotesRepo.findById(favNoteId)
				.orElseThrow(()->new ResourceNotFoundException("fav notes not found and invalid"));
		favouriteNotesRepo.delete(favnotes);
	}

	@Override
	public List<FavouriteNoteDto> getUserFavouritNotes() throws Exception {
		int userId =1;
		
	List<FavouritNotes>	favouritNotes=favouriteNotesRepo.findByUserId(userId);
  return  favouritNotes.stream().map(fn->mapper.map(fn, FavouriteNoteDto.class)).toList();
		
		
		
	}

	@Override
	public Boolean copyNotes(Integer id) throws Exception {
		 Notes notes = notesRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Notes id invalid !Not found"));

        Notes copyNotes =Notes.builder()
        		        .title(notes.getTitle())
        		        .description(notes.getDescription())
        		        .category(notes.getCategory())
        		        .isDeleted(false)
        		        .filesDetails(null)
                        .build();  
        
       Notes saveCopyNotes =notesRepo.save(copyNotes);
       
       
       if(!ObjectUtils.isEmpty(saveCopyNotes)) {
    	   return true;
       }
       return false;
	}
 
}
