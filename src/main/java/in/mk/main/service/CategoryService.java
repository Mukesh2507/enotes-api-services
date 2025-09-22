package in.mk.main.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.CategoryResponse;
import in.mk.main.entity.Category;
import in.mk.main.respository.CategoryRepository;

public interface CategoryService {
	
	
public Boolean  saveCategory(CategoryDto categoryDto);

public List<CategoryDto> getAllCategory();

public List<CategoryResponse> getActiveCategory();

public CategoryDto getCategoryById(Integer id);

public Boolean deleteCategory(Integer id);

	
}
