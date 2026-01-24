package in.mk.main.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.main.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findByCreatedBy(Integer userId);

}
