package in.mk.main.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import in.mk.main.dto.handler.GenericResponse;
import jakarta.persistence.criteria.CriteriaBuilder.Case;
import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				              .responseStatus(status)
				              .status("Success")
				              .message("Success")
				              .data(data)
				             .build();
		return response.create();
		
	}
	
public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				              .responseStatus(status)
				              .status("Success")
				              .message(message)
				             .build();
		return response.create();
		
	}

public static ResponseEntity<?> createErrorBuildResponse(Object data,HttpStatus status){
	
	GenericResponse response = GenericResponse.builder()
			              .responseStatus(status)
			              .status("Succes")
			              .message("failed")
			              .data(data)
			             .build();
	return response.create();
	
}

public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
	
	GenericResponse response = GenericResponse.builder()
			              .responseStatus(status)
			              .status("failed")
			              .message(message)
			             .build();
	return response.create();
	
}
public static String getContentType(String originalfileName) {
	
	String extension = FilenameUtils.getExtension(originalfileName);
	
	switch (extension) {
	case "pdf": 
		
		return "application/pdf";
     
	case "xlsx":
		return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheett";
	case "txt":
		return "text/plan";
		
	case "png":
		return "image/png";
		
	case "jpeg":
		return "image/jpeg";
				
	default:
		return "application/octet-stream";
			
	}
	
	

}

public static String getUrl(HttpServletRequest request) {
	String apiUrl = request.getRequestURI().toString();
	return apiUrl;
}
}
