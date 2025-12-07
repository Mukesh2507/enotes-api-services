package in.mk.main.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.TodoDto;
import in.mk.main.enums.TodoStatus;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.exception.ValidationException;

@Component
public class Validation {

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
	
	
}
