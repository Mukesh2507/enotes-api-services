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
import in.mk.main.endpoint.AuthControllerEndpoint;
import in.mk.main.service.AuthService;
import in.mk.main.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController

public class AuthController implements AuthControllerEndpoint {
   
	
	@Autowired
	private AuthService authService;
	
	
	@Override
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest request) throws Exception
	{
		log.info("AuthController : registerUsser() :excecution start");


		String url=CommonUtil.getUrl(request);
		Boolean register = authService.register(userDto);
		if (!register) {
			log.info("Error ;{)","register failed");

			return CommonUtil.createErrorResponseMessage("Register failed",HttpStatus.INTERNAL_SERVER_ERROR);

		}	
		
		log.info("AuthController : registerUsser() :excecution end");
        return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED );
		
	}
		@Override
		public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception
		{
			LoginResponse loginResponse=authService.login(loginRequest);
			
			if(ObjectUtils.isEmpty(loginResponse)) {
				
				return CommonUtil.createErrorResponseMessage("invalid creadential",HttpStatus.BAD_REQUEST);
			}
		    return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
						
	}
	
	
	
	
	

}
