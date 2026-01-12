package in.mk.main.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.UserResponse;
import in.mk.main.entity.User;
import in.mk.main.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
	private ModelMapper mapper;
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		
	User loggedInUser=CommonUtil.getLoggedInUSer();
	UserResponse userResponse=mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
}
