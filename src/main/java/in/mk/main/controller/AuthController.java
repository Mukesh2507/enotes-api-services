package in.mk.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.LoginRequest;
import in.mk.main.dto.LoginResponse;
import in.mk.main.dto.UserRequest;
import in.mk.main.service.UserService;
import in.mk.main.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
   
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest request) throws Exception
	{
		String url=CommonUtil.getUrl(request);
		Boolean register = userService.register(userDto);
		if (register) {
			return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED );
			
		}else {
			return CommonUtil.createErrorResponseMessage("Register failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		@PostMapping("/login")
		public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception
		{
			LoginResponse loginResponse=userService.login(loginRequest);
			
			if(ObjectUtils.isEmpty(loginResponse)) {
				
				return CommonUtil.createErrorResponseMessage("invalid creadential",HttpStatus.BAD_REQUEST);
			}
		    return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
						
	}
	
	
	
	
	

}
