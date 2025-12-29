package in.mk.main.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Boolean existsByEmail(String email);

	User findByEmail(String username);

}
