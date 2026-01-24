package in.mk.main.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {

}
