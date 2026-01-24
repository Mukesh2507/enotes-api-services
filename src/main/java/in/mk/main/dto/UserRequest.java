package in.mk.main.dto;

import java.util.List;

import in.mk.main.entity.Role;
import in.mk.main.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
public class UserRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobNo;
    private String password;

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
}
