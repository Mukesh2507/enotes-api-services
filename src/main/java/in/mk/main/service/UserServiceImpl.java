package in.mk.main.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import in.mk.main.dto.Emailrequest;
import in.mk.main.dto.PasswordChangeRequest;
import in.mk.main.dto.PswdResetRequest;
import in.mk.main.entity.User;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.respository.UserRepository;
import in.mk.main.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;  
	
	@Override
	public void changePassword(PasswordChangeRequest passwordRequest) {
		
	User loggedInUser=CommonUtil.getLoggedInUSer();
	
	
	if(!passwordEncoder.matches( passwordRequest.getOldPassword(),loggedInUser.getPassword())) {
		throw new IllegalArgumentException("old password is incorrect !! ");
		}
     String	encodePassword=passwordEncoder.encode(passwordRequest.getNewPassowrd());
	 loggedInUser.setPassword(encodePassword);
		userRepository.save(loggedInUser);
		
	}

	@Override
	public void sendEmailPasswordReset(String email,HttpServletRequest request) throws Exception {
		User user=userRepository.findByEmail(email); 
		
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("invalid email");
			
		}
		//Generate password reset token
		String passwordResetToken=UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
	    User updateUser=userRepository.save(user);
		
		
	String	url=CommonUtil.getUrl(request);
     sendEmailRequest(updateUser,url);
		
		
		
		
		
	}

	private void sendEmailRequest(User user, String url) throws Exception {
		
		String message ="Hi, <b>[[username]]</b><br>"
		        + "Your have requested to reset your  password.<br>"
		        + "<br>Click the below link to chnage your password:<br>"
		        + "<p><a href='[[url]]'>Change my password</a></p>"
		        +"<p>Ignore this email if you do remember your password,"
		        +"or you have not made the request.</p><br><b>"
		        +"Thanks,<br>Enotes.com";

		      

message=message.replace("[[username]]", user.getFirstName());	
message=message.replace("[[url]]",url + "/api/v1/home/verify-pswd-link?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());
		Emailrequest emailrequest=Emailrequest.builder()
				.to(user.getEmail())
				.title("Password reset")
				.subject("password reset link")
				.message(message)
				.build();
		
		//send password reset email to user
		emailService.sendEmail(emailrequest);

	}

	@Override
	public void verifypswdResetLink(Integer uid, String code) throws Exception {
	User	user=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("invalid user"));
		
	verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),code);	
	
	}

	private void verifyPasswordResetToken(String existToken, String reToken) {
		//request token not null
		if(StringUtils.hasText(existToken)) {
			
			//password already reset 
			if(!StringUtils.hasText(existToken)) {
				
				throw new IllegalArgumentException("Already password reset");
			}
			
			//user req token changes
			if(!existToken.equals(reToken)) {
				throw new IllegalArgumentException("invalid url");
			}
			
		}else {
			throw new IllegalArgumentException("invalid token");
		}
		
		
		
	}

	@Override
	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception  {
		User  user=userRepository.findById(pswdResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("invalid user"));
         String enocdePassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
		 user.setPassword(enocdePassword);
		 user.getStatus().setPasswordResetToken(enocdePassword);
	    	userRepository.save(user);
	}

	

}
