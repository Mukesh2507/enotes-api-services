package in.mk.main.exception;

import java.io.FileNotFoundException;
import java.lang.System.Logger;
import java.security.PublicKey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.mk.main.util.CommonUtil;
import lombok.extern.java.Log;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?>handleExeception(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?>handleSuccessException(SuccessException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
	}

	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentexception(IllegalArgumentException e){
		
		//log.error("GlobalExceptionHandler ::handleException::",e.getMessage());
		
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	{
		
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?>handleNullPointerExeception(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>handleResourceNotFoundException(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?>handleValidationException(ValidationException e)
	{
		//return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorBuildResponse(e.getErrors(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?>handleExistDataException(ExistDataException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorBuildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?>handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?>handleFileNotFoundException(FileNotFoundException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

	}
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?>handleBadCreadentialException(BadCredentialsException e)
	{
		//return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

}
