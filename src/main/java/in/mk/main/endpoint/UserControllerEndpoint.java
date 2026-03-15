package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import in.mk.main.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="User",description="All the user operations APIs")

@RequestMapping("/api/v1/user")
public interface UserControllerEndpoint {
	
	@Operation(summary="get profile",tags= {"User"},description="get user profile")

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@Operation(summary="change password",tags= {"User"},description="change password for user")

	@PostMapping("/chng-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest);
	
	

}
