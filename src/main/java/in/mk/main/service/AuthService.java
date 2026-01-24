package in.mk.main.service;

import in.mk.main.dto.LoginRequest;
import in.mk.main.dto.LoginResponse;
import in.mk.main.dto.UserRequest;

public interface AuthService {
	
	
	public Boolean register(UserRequest userDto) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
