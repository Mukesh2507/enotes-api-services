package in.mk.main.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static in.mk.main.util.Constants.ROLE_ADMIN;
import static in.mk.main.util.Constants.ROLE_ADMIN_USER;
import in.mk.main.dto.CategoryDto;
import in.mk.main.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name="Category",description="All the category operations APIs")

@RequestMapping("/api/v1/category")
public interface CategoryControllerEndpoint {
	
	
	
	
	
	@Operation(summary="Save category Endpoint",tags= {"Category"})

	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(CategoryDto categoryDto);
	
	@Operation(summary="Get all category Endpoint",tags= {"Category"})

	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary="Get active category Endpoint",tags= {"Category"})

	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary="Get category by id Endpoint",tags= {"Category"})

	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(Integer id) throws Exception;
	
	@Operation(summary="delete category by id Endpoint",tags= {"Category"})

	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryById(Integer id);

}
