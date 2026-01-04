package in.mk.main.service;

import in.mk.main.dto.LoginRequest;
import in.mk.main.dto.LoginResponse;
import in.mk.main.dto.UserDto;

public interface UserService {
	
	
	public Boolean register(UserDto userDto) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
