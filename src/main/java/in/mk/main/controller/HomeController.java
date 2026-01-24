package in.mk.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.PswdResetRequest;
import in.mk.main.service.HomeService;
import in.mk.main.service.UserService;
import in.mk.main.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private  HomeService homeService;
	
	@Autowired
	private UserService userService;

//	private HttpServletRequest request;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code ) throws Exception{
		Boolean verifyAccount = homeService.VerifyAccount(uid, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponse("Account verified", HttpStatus.OK);
		}
		return  CommonUtil.createErrorResponseMessage("invalid verification link", HttpStatus.BAD_REQUEST);
		
	}
	
	   @GetMapping("/send-email-reset")
	   public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception{
		   
		   userService.sendEmailPasswordReset(email,request);
		   return CommonUtil.createBuildResponse("email sent success :: check email reset password", HttpStatus.OK);
	   }
	   
	   @GetMapping("/verify-pswd-link")
	   public ResponseEntity<?> verifyPasswordResetToken(@RequestParam Integer uid ,@RequestParam String code) throws Exception{
		   
		  userService.verifypswdResetLink(uid,code);
		  return CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.OK);
	   }
	   
	   @PostMapping("/reset-pswd")
	   public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception{
		   
		   userService.resetPassword(pswdResetRequest);
		   
		   return CommonUtil.createBuildResponseMessage("password reset succesfully", HttpStatus.OK);
	   }
	

}
