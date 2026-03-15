package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static in.mk.main.util.Constants.ROLE_USER;

import in.mk.main.dto.TodoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name="Todo",description="All the Todo operations APIs")

@RequestMapping("/api/v1/todo")
public interface TodoControllerEndpoint {
	
	
	@Operation(summary="save todo",tags= {"Todo"},description="save todo notes")

	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	@Operation(summary="get todo by id",tags= {"Todo"},description="Get todo by id")

	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

	@Operation(summary="get todo by user",tags= {"Todo"},description="Get todo by user")

	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser(@PathVariable Integer id) throws Exception;
}
