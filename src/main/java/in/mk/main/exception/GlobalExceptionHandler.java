package in.mk.main.exception;

import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.nio.file.AccessDeniedException;
import java.security.PublicKey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.mk.main.util.CommonUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?>handleExeception(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("GlobalExceptionHandler :: handleException() :{}",e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?>handleAccessDeniedExeception(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("GlobalExceptionHandler :: handleAccessDeniedException() :{}",e.getMessage());

		return CommonUtil.createErrorBuildResponse(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?>handleSuccessException(SuccessException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("GlobalExceptionHandler :: handleSuccessException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
	}

	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
		
		//log.error("GlobalExceptionHandler ::handleException::",e.getMessage());
		log.error("GlobalExceptionHandler : handleIllegalArgumentException() :{}",e.getMessage());

		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	{
		
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?>handleNullPointerExeception(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("GlobalExceptionHandler : handleNullPointerException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>handleResourceNotFoundException(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		log.error("GlobalExceptionHandler : handleResourceNotFoundException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?>handleValidationException(ValidationException e)
	{
		//return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		log.error("GlobalExceptionHandler : handleValidationException() :{}",e.getMessage());

		return CommonUtil.createErrorBuildResponse(e.getErrors(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?>handleExistDataException(ExistDataException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		log.error("GlobalExceptionHandler : handleExistDataException() :{}",e.getMessage());

		return CommonUtil.createErrorBuildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?>handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		log.error("GlobalExceptionHandler : handleHttpMessageNotReadableException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?>handleFileNotFoundException(FileNotFoundException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		log.error("GlobalExceptionHandler : handleFileNotFoundException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

	}
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?>handleBadCreadentialException(BadCredentialsException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		log.error("GlobalExceptionHandler : handleBadCreadentialException() :{}",e.getMessage());

		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

}
