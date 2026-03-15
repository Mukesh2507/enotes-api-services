package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.mk.main.dto.PswdResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Home",description="All the Home operations APIs")

@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {
	
	
	@Operation(summary="verification user account",tags= {"Home"},description="user verification after verification")

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code ) throws Exception;

	@Operation(summary="email resend request",tags= {"Home"},description="sneding email for password reset")

	 @GetMapping("/send-email-reset")
	   public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

	@Operation(summary="verify password",tags= {"Home"},description="user verification password link")

	   @GetMapping("/verify-pswd-link")
	   public ResponseEntity<?> verifyPasswordResetToken(@RequestParam Integer uid ,@RequestParam String code) throws Exception;
	   
	@Operation(summary="for reset password",tags= {"Home"},description="passowrd reset user can change passwords")

	   @PostMapping("/reset-pswd")
	   public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
