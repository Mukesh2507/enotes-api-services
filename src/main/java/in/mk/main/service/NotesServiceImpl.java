package in.mk.main.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.mk.main.dto.NotesDto;
import in.mk.main.dto.NotesDto.CategoryDto;
import in.mk.main.entity.FilesDetails;
import in.mk.main.entity.Notes;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.respository.CategoryRepository;
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
	
	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception {
		//logic writing changes for save notes 
		
		 ObjectMapper ob =new ObjectMapper();
		NotesDto notesDto =ob.readValue(notes, NotesDto.class);
		

		//category validation logic here 
		 checkCategoryExist(notesDto.getCategory());
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FilesDetails fileDtls=saveFileDetails(file); //saving file in database
		
	    if(!ObjectUtils.isEmpty(ob)) {
	    	
	    	notesMap.setFilesDetails(fileDtls);
	    }
	    else {
	    	notesMap.setFilesDetails(null);
	    }
		
		  Notes saveNotes = notesRepo.save(notesMap);
		    if (!ObjectUtils.isEmpty(saveNotes)) {
			  return true;
			}
		return false;
	}

	private FilesDetails saveFileDetails(MultipartFile file) throws IOException {
      
		 if (!file.isEmpty()) {
			
			 FilesDetails fileDtls = new FilesDetails();
			 String originalFileNameString =file.getOriginalFilename();
			 fileDtls.setOriginalfileName(originalFileNameString);
			 fileDtls.setDisplayFileName(getDisplayName(originalFileNameString));
			 
			 String rndString =UUID.randomUUID().toString();
			 String extension =FilenameUtils.getExtension(originalFileNameString);
			String uploadFileName= rndString+"."+extension;
			
			fileDtls.setUploadFileName(uploadFileName);
			fileDtls.setFileSize(file.getSize());
			
			
			File saveFile =new File(uploadPath);
			
			if (!saveFile.exists()) {
				saveFile.mkdir();
				
			}
			// path :enotesapiservice/notes/java.pdf
			
			String storePath = uploadPath.concat(uploadFileName);
			fileDtls.setPath(storePath);
			
			
			//upload file logic
		Long upload=Files.copy(file.getInputStream(), Paths.get(storePath));
		
		if (upload !=0) {
			
     FilesDetails  saveFilesDetails=fileRepo.save(fileDtls);
     
			return saveFilesDetails;
		}
			
		}
		
		
		return null;
	}

	private String getDisplayName(String originalFileNameString) {
		//java programming language.pdf
		//used library apache common for file
		
	
		String extension =FilenameUtils.getExtension(originalFileNameString);
		String fileName=FilenameUtils.removeExtension(originalFileNameString);
		
		if (fileName.length()>8) {
			fileName =fileName.substring(0,7);
			
		}
		fileName= fileName+"."+extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		// TODO Auto-generated method stub
		
		
		categoryRepo.findById(category.getId())
		.orElseThrow (()->new ResourceNotFoundException("category id is invalid"));
		
		
		
		
		
		
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream()
				.map(note->mapper.map(note,NotesDto.class)).toList();
		
		
	}

}
