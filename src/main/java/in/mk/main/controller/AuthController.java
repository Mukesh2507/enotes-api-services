package in.mk.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.UserDto;
import in.mk.main.service.UserService;
import in.mk.main.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
   
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception
	{
		Boolean register = userService.register(userDto);
		if (register) {
			return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED );
			
		}else {
			return CommonUtil.createErrorResponseMessage("Register failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	

}
