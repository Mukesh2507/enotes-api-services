package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import in.mk.main.dto.PasswordChangeRequest;

@RequestMapping("/api/v1/user")
public interface UserControllerEndpoint {
	
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@PostMapping("/chng-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest);
	
	

}
