package in.mk.main.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.PasswordChangeRequest;
import in.mk.main.dto.UserResponse;
import in.mk.main.endpoint.UserControllerEndpoint;
import in.mk.main.entity.User;
import in.mk.main.service.UserService;
import in.mk.main.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class UserController implements UserControllerEndpoint {
    @Autowired
	private ModelMapper mapper;
    
    @Autowired
    private UserService userService;
    
    @Override
	public ResponseEntity<?> getProfile(){
		
	User loggedInUser=CommonUtil.getLoggedInUSer();
	UserResponse userResponse=mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest){
		
	    userService.changePassword(passwordRequest);
		return CommonUtil.createBuildResponseMessage("password change success", HttpStatus.OK);
	}
	
	
	
}
