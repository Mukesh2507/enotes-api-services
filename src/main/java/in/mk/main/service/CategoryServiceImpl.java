package in.mk.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		
	if(ObjectUtils.isEmpty(category.getId())) {
		 category.setIsDeleted(false);;
         category.setCreatedBy(1);
         category.setCreatedOn(new Date());
	}
	else {
		updateCategory(category);
	}
	
		
         
    Category  saveCategory	=categoryRepository.save(category);
              
    if (ObjectUtils.isEmpty(saveCategory)) {
    	return false;
		
	}
		return true;
	}

	private void updateCategory(Category category) {
		
		Optional<Category> findByIdOptional =categoryRepository.findById(category.getId());
		
		if (findByIdOptional.isPresent()) {
			Category existCategory = findByIdOptional.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
			
			
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category>category =categoryRepository.findByIsDeletedFalse();
		
	List<CategoryDto> categoryDtosList =	  category.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtosList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse>categoryList=categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional< Category> findByCategory = categoryRepository.findByIdAndIsDeletedFalse(id);
		if (findByCategory.isPresent()) {
			
		Category category=findByCategory.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		
		
		Optional< Category> findByCategory = categoryRepository.findById(id);
		if (findByCategory.isPresent()) {
			
		Category category=findByCategory.get();
		category.setIsDeleted(true);
		categoryRepository.save(category);
			return true;
		}
		return false;
		
	}
	
	

}
