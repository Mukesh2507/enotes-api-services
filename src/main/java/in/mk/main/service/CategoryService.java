package in.mk.main.service;

import java.util.List;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.CategoryResponse;
import in.mk.main.entity.Category;

public interface CategoryService {
	
	
public Boolean  saveCategory(CategoryDto categoryDto);

public List<CategoryDto> getAllCategory();

public List<CategoryResponse> getActiveCategory();


	
	
}
