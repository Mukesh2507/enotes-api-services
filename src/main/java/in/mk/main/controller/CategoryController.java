package in.mk.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.CategoryResponse;
import in.mk.main.entity.Category;
import in.mk.main.exception.ResourceNotFoundException;
import in.mk.main.service.CategoryService;
import in.mk.main.util.CommonUtil;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	//Is used to create new data in datbase
	//requestbody ensure data will be return in json or xml formate
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory( @RequestBody CategoryDto categoryDto){
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory) {
			
		return CommonUtil.createBuildResponse("Saved successfully", HttpStatus.CREATED); //implementation of generic response handler 
			// return new ResponseEntity<>("saved",HttpStatus.CREATED);
		}
		else {
			return   CommonUtil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			//return new ResponseEntity<>("saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDto> allcategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allcategory)){
			return ResponseEntity.noContent().build();
		}
		else {
			return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);
			//return new ResponseEntity<>(allcategory,HttpStatus.OK);
		}
		
		
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponse> allcategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allcategory)){
			return ResponseEntity.noContent().build();
		}
		else {
			
			return CommonUtil.createBuildResponse(allcategory, HttpStatus.OK);

			//return new ResponseEntity<>(allcategory,HttpStatus.OK);
		}
		
		
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		
		
		
		
		CategoryDto categoryDto = categoryService.getCategoryById(id);
	
		if (ObjectUtils.isEmpty(categoryDto)) {
			return CommonUtil.createErrorResponseMessage("Internal server error", HttpStatus.NOT_FOUND);

			//return new ResponseEntity<>("internal server error",HttpStatus.NOT_FOUND);
		}
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);

			//return new ResponseEntity<>(categoryDto,HttpStatus.OK);
		} 
	
	

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
	Boolean deleted = categoryService.deleteCategory(id);

	if (deleted) {
		return CommonUtil.createBuildResponse("category deleted successfully", HttpStatus.OK);

		//return new ResponseEntity<>("category deleted",HttpStatus.OK);
	}
	return CommonUtil.createErrorResponseMessage("category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);

		//return new ResponseEntity<>("category not delted",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
	


