package in.mk.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.mk.main.entity.AccountStatus;
import in.mk.main.entity.User;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.exception.SuccessException;
import in.mk.main.respository.UserRepository;

@Component

public class homeServiceImpl implements HomeService{

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public Boolean VerifyAccount(Integer userId, String verificationCode) throws Exception {
		
	User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("invalid user"));
	 
	if(user.getStatus().getVerificationCode()==null) {
		throw new SuccessException("Account already verified");
	}
	
	if (user.getStatus().getVerificationCode().equals(verificationCode)) {
		AccountStatus status =user.getStatus();
		status.setIsActive(true);
		status.setVerificationCode(null);
		
		userRepo.save(user);
		
		return true;
		
		
	}	
	
	
		return false;
	}
	
	
	

}
