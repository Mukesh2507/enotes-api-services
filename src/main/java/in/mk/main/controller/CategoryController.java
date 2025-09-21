package in.mk.main.controller;

import java.security.PublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.CategoryResponse;
import in.mk.main.entity.Category;
import in.mk.main.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	//Is used to create new data in datbase
	//requestbody ensure data will be return in json or xml formate
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory) {
			 return new ResponseEntity<>("saved",HttpStatus.CREATED);
		}
		else {
			 return new ResponseEntity<>("saved",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		
		List<CategoryDto> allcategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allcategory)){
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allcategory,HttpStatus.OK);
		}
		
		
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponse> allcategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allcategory)){
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allcategory,HttpStatus.OK);
		}
		
		
	}
		
		
		
	}
	


