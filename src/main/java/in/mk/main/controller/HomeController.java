package in.mk.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.service.HomeService;
import in.mk.main.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private  HomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code ) throws Exception{
		Boolean verifyAccount = homeService.VerifyAccount(uid, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponse("Account verified", HttpStatus.OK);
		}
		return  CommonUtil.createErrorResponseMessage("invalid verification link", HttpStatus.BAD_REQUEST);
		
	}
	

}
