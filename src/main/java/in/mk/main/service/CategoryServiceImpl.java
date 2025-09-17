package in.mk.main.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.mk.main.entity.Category;
import in.mk.main.respository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private  CategoryRepository categoryRepository;

	@Override
	public Boolean saveCategory(Category category) {
		
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
	public List<Category> getAllCategory() {
		List<Category>category =categoryRepository.findAll();
		
		return category;
	}
	
	

}
