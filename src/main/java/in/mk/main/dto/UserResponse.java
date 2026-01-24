package in.mk.main.dto;

import java.util.List;

import in.mk.main.dto.TodoDto.StatusDto;
import in.mk.main.dto.UserRequest.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {
	
	
	 private Integer id;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String mobNo;
	    
	    private StatusDto status;

	    private List<RoleDto> roles;

	    @Getter
	    @Setter
	    @AllArgsConstructor
	    @NoArgsConstructor
	    @Builder
	    public static class RoleDto {
	        private Integer id;
	        private String name;
	    }
	
	    public static class StatusDto{
	    	private Integer id;
	    	private Boolean isActive;
	    }
	
	

}
