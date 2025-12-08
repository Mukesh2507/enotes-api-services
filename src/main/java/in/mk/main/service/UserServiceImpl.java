package in.mk.main.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.mk.main.dto.UserDto;
import in.mk.main.entity.Role;
import in.mk.main.entity.User;
import in.mk.main.respository.RoleRepository;
import in.mk.main.respository.UserRepository;
import in.mk.main.util.Validation;

@Service
public class UserServiceImpl implements UserService{

	
	
	@Autowired
	private UserRepository userRepos;
	
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private Validation validation;
	@Override
	public Boolean register(UserDto userDto) throws Exception {
		
		validation.userValidation(userDto);
		
		User  user=mapper.map(userDto, User.class );
		
		setRole(userDto,user);
	    User	saveUser=userRepos.save(user);
	    if (!ObjectUtils.isEmpty(saveUser)) {
	    	
	    	return true;
	    	
			
		}
		
		return false;
	}
	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles=roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
