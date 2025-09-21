package in.mk.main.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.mk.main.dto.CategoryDto;
import in.mk.main.dto.CategoryResponse;
import in.mk.main.entity.Category;
import in.mk.main.respository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private  CategoryRepository categoryRepository;
	
    @Autowired
	private ModelMapper mapper;
	
	
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		
	Category category=mapper.map(categoryDto,Category.class);
		
		
		 category.setIsDeleted(false);;
         category.setCreatedBy(1);
         category.setCreatedOn(new Date());
         
    Category  saveCategory	=categoryRepository.save(category);
              
    if (ObjectUtils.isEmpty(saveCategory)) {
    	return false;
		
	}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category>category =categoryRepository.findAll();
		
	List<CategoryDto> categoryDtosList =	  category.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtosList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse>categoryList=categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryList;
	}
	
	

}
