package in.mk.main.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.aot.PublicMethodReflectiveProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.TodoDto;
import in.mk.main.dto.UserDto;
import in.mk.main.enums.TodoStatus;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.exception.ValidationException;
import in.mk.main.respository.FavouriteNotesRepository;
import in.mk.main.respository.RoleRepository;

@Component
public class Validation {

    private final FavouriteNotesRepository favouriteNotesRepository;

@Autowired
private RoleRepository roleRepo;


    Validation(FavouriteNotesRepository favouriteNotesRepository) {
        this.favouriteNotesRepository = favouriteNotesRepository;
    }
	

	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			
			throw new IllegalArgumentException("category object/json shoukld not be null or empty");
		}
		else {
			   // validation name field
			    if(ObjectUtils.isEmpty(categoryDto.getName())) {
			    	
			    	error.put("name","name filed is empty null");
			    }else {
			    	if(categoryDto.getName().length()<3) {
			    		error.put("name", "name length is min 3");
			    	}
			    	if(categoryDto.getName().length()>100) {
			    		error.put("name", "name length is max 100");
			    	}
			    }
			    
			    //validation description
			    
                   if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
			    	
			    	error.put("Description","Description filed is empty null");
			    }
                   
                   //validation isactive
                   

                   if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
			    	
			    	error.put("isActive","isActive filed is empty or null");
			    }else {
			    	
	 		    	if(categoryDto.getIsActive() !=Boolean.TRUE.booleanValue()  && categoryDto.getIsActive() !=Boolean.FALSE.booleanValue()) {
			    		
			    		error.put("isActive", "invalid value isActive field");
			    	}
			    }
		}
	       if (!error.isEmpty()) {
	    	   {
	    		   throw new ValidationException(error);
	    	   }
			
		}	
	}
	
	public void todoValidation(TodoDto todoDto) throws Exception {
		
		 TodoDto.StatusDto reqStatus=todoDto.getStatus();
		TodoStatus[] todoStatus=TodoStatus.values();
		
		Boolean statusFound =false;
		for(TodoStatus st:TodoStatus.values()) {
			
			if (st.getId().equals(reqStatus.getId())) {
				
				statusFound = true;
				
			}if(!statusFound) {
				throw new ResourceNotFoundException("invalid status");
			}
			
		}
		
	}
		public void userValidation(UserDto userDto) throws Exception {
			
			
			if (!StringUtils.hasText(userDto.getFirstName())) {
				
				throw new IllegalArgumentException("first name is invalid");
				
				}
			
			if(!StringUtils.hasText(userDto.getLastName())) {
				
				
				throw new IllegalArgumentException("last name is invalid");
			
			}
			
			if (!StringUtils.hasText(userDto.getEmail()) || 
			!userDto.getEmail().matches(Constants.Email_Regex)) {
				
			    throw new IllegalArgumentException("email is invalid");
				}
			
			if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.mOBILE_REGEX)) {
				
			    throw new IllegalArgumentException("mob no is invalid");
				}


			if (CollectionUtils.isEmpty(userDto.getRoles())) {
				throw new IllegalAccessException("role is invalid");
				
			}else {
				List<Integer> roleIds =roleRepo.findAll().stream().map(r->r.getId()).toList();		
                    
				
				List<Integer> invalidReqRoleids=userDto.getRoles().stream()
				.map(r->r.getId())
				.filter(roleId->roleIds.contains(roleIds)).toList();
				
				if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
					
					throw new IllegalArgumentException("role is invalid" +invalidReqRoleids);
					
				}
				
				
				
				
			}
		
		
		
	}
	
	
}
