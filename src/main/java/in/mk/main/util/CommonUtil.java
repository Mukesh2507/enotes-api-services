package in.mk.main.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import in.mk.main.dto.handler.GenericResponse;

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
}
